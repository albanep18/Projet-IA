package awale;

import java.util.Scanner;

import awale.GreedyBestFirst;
import awale.Joueur;
import awale.RandomPlayer;

public class Awale {
	static int nb_trou = 12;
	int nb_graines_initial = 48;
	static int[] plateau = new int[nb_trou];
	// de 1 à 6 : joueur 1
	// de 7 à 12 : joueur 2
	static int scoreJ1 = 0;
	static int scoreJ2 = 0;

	public static void initialiserPlateau() {
		for (int i = 0; i < nb_trou; i++)
			plateau[i] = 4;
	}

	public static boolean coupvalide(int trou, boolean joueur1) {
		int cpt = 0; // compte le nombre de graine dans le camp adverse
		if (joueur1) {
			if (trou < 0 || trou > 5 || plateau[trou] == 0)
				return false;
			// si le joueur 2 a pas de graines de son cote il faut jouer un coup qui peut
			// lui en donner
			for (int i = 6; i < nb_trou; i++) {
				if (plateau[i] == 0)
					cpt++;
			}
			if (cpt == 0) {
				// si le joueur 2 a a pas de graine
				// il faut que notre coup donne au minimum une graine dans la case 6
				// il doit y avoir plus de 6-trou graines dans la case de trou
				if (plateau[trou] < 6 - trou)
					return false;
			}
			return true;
		} else {
			if (trou < 6 || trou > 11)
				return false;
			// si le joueur 1 a pas de graines de son cote il faut jouer un coup qui peut
			// lui en donner
			for (int i = 0; i < 6; i++) {
				if (plateau[i] == 0)
					cpt++;
			}
		}
		if (cpt == 0) {
			// si le joueur 1 a a pas de graine
			// il faut que notre coup donne au minimum une graine dans la case 0
			// il doit y avoir plus de 12-trou graines dans la case de trou
			if (plateau[trou] < 12 - trou)
				return false;
		}
		return true;
	}

	public static int jouercoup(int trou, boolean joueur1) {
		// deposage de graines
		int graines = plateau[trou];
		plateau[trou] = 0;
		int position = trou;
		while (graines > 0) {
			// avancer et faire le tour du plateau (wrap)
			position = (position + 1) % nb_trou;
			// on ne replante pas dans la case d'origine
			if (position == trou)
				continue;
			plateau[position]++;
			graines--;
		}
		// capture des graines
		int captures = 0;
		while (true) {
			if (joueur1 && position >= 6 && position < 12 && (plateau[position] == 2 || plateau[position] == 3)) {
				captures += plateau[position];
				plateau[position] = 0;
			} else if (!joueur1 && position >= 0 && position < 6
					&& (plateau[position] == 2 || plateau[position] == 3)) {
				captures += plateau[position];
				plateau[position] = 0;
			} else
				break;
			position = (position - 1 + nb_trou) % nb_trou;
		}
		// NOTE: le score est mis à jour par le caller (main) pour éviter double
		// comptage
		return captures;
	}

	public static boolean finpartie(boolean joueur1) {
		if (scoreJ1 > 24 || scoreJ2 > 24)
			return true;

		if (joueur1) {
			// voir si le joueur 2 a des graines de son coté
			for (int i = 6; i < nb_trou; i++)
				if (plateau[i] != 0)
					return false;
			// sinon si joueur 2 n'a pas de graines de son coté
			// on regarde si le joueur 1 a au moins un coup valide à jouer
			for (int i = 0; i < 6; i++)
				if (coupvalide(i, joueur1) == true)
					return false;
			// si le joueur 1 n'a pas de coup valide c'est la fin de la partie
			// et il récupère les graines de son coté
			for (int i = 0; i < 6; i++)
				scoreJ1 += plateau[i];
			return true;
		}
		// pareil lorsque c'est le tour du joueur 2 de jouer
		else {
			// voir si le joueur 1 a des graines de son coté
			for (int i = 0; i < 6; i++)
				if (plateau[i] != 0)
					return false;
			// sinon si joueur 1 n'a pas de graines de son coté
			// on regarde si le joueur 2 a au moins un coup valide a jouer
			for (int i = 6; i < nb_trou; i++)
				if (coupvalide(i, (!joueur1)) == true)
					return false;
			// si le joueur 2 n'a pas de coup valide c'est la fin de la partie
			// et il recupere les graines de son coté
			for (int i = 6; i < nb_trou; i++)
				scoreJ2 += plateau[i];
			return true;
		}

	}

	public static void afficherPlateau() {
		System.out.println("\nPlateau :");
		System.out.println("   12 11  10  9  8  7");
		System.out.println("   ");
		System.out.print("   ");
		for (int i = 11; i >= 6; i--)
			System.out.printf("%2d ", plateau[i]);
		System.out.println();
		System.out.print("   ");
		for (int i = 0; i <= 5; i++)
			System.out.printf("%2d ", plateau[i]);
		System.out.println("   ");
		System.out.println("\n    1  2  3  4  5  6");
		System.out.printf("Score J1: %d | Score J2: %d%n", scoreJ1, scoreJ2);
	}

	public static void main(String[] args) {
		// Configuration des joueurs
		Joueur j1 = new Joueur("Joueur 1", true, new GreedyBestFirst());
		Joueur j2 = new Joueur("Joueur 2", false, new RandomPlayer());

		initialiserPlateau();
		boolean tourJ1 = true;

		while (!finpartie(tourJ1)) {
			afficherPlateau();
			Joueur courant = tourJ1 ? j1 : j2;

			System.out.println("\nTour de " + courant.getNom());
			int coup = courant.getStrategie().choisirCoup(courant.estJoueur1(), plateau);
			int captures = jouercoup(coup, courant.estJoueur1());
			courant.ajouterScore(captures);

			System.out.println(courant.getNom() + " a capturé " + captures + " graines !");
			tourJ1 = !tourJ1;
		}

		afficherPlateau();
		System.out.printf("\n🎯 Partie terminée !\n%s : %d | %s : %d\n", j1.getNom(), j1.getScore(), j2.getNom(),
				j2.getScore());
		if (j1.getScore() > j2.getScore())
			System.out.println("🏆 " + j1.getNom() + " gagne !");
		else if (j2.getScore() > j1.getScore())
			System.out.println("🏆 " + j2.getNom() + " gagne !");
		else
			System.out.println("🤝 Égalité !");
	}

}
/*
 * Scanner sc = new Scanner(System.in); initialiserPlateau(); boolean joueur1 =
 * true; while (!finpartie(joueur1)) { afficherPlateau();
 * System.out.println("\nTour du " + (joueur1 ? "Joueur 1" : "Joueur 2"));
 * System.out.println("Score J1 = " + scoreJ1 + " | Score J2 = " + scoreJ2); int
 * trou; while (true) { System.out.print("Choisis un trou ("); if (joueur1)
 * System.out.print("1 à 6"); else System.out.print("7 à 12");
 * System.out.print(") : "); trou = sc.nextInt() - 1; if (coupvalide(trou,
 * joueur1)) break; System.out.
 * println("❌ Coup invalide, choisis un trou de ton camp avec au moins 1 graine !"
 * ); } int captures = jouercoup(trou, joueur1); if (joueur1) scoreJ1 +=
 * captures; else scoreJ2 += captures; joueur1 = !joueur1; // changement de
 * joueur
 * 
 * } afficherPlateau(); System.out.println("\n🎯 Partie terminée !");
 * System.out.println("Score final : Joueur 1 = " + scoreJ1 + " | Joueur 2 = " +
 * scoreJ2); if (scoreJ1 > scoreJ2) System.out.println("🏆 Joueur 1 gagne !");
 * else if (scoreJ2 > scoreJ1) System.out.println("🏆 Joueur 2 gagne !"); else
 * System.out.println("🤝 Égalité !"); } }
 */
