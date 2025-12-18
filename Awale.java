package awale;

import java.util.Arrays;

public class Awale {
	public static final int nb_trou = 12;
	public int nb_graines_initial = 48;
	public int[] plateau = new int[nb_trou];

	public void initialiserPlateau() {
		for (int i = 0; i < nb_trou; i++)
			plateau[i] = nb_graines_initial / nb_trou;
	}

	public static boolean coupvalide(int trou, boolean joueur1, int[] plateau) {
		int debutJ = joueur1 ? 0 : 6;
		int finJ = joueur1 ? 5 : 11;
		int debutA = joueur1 ? 6 : 0;
		int finA = joueur1 ? 11 : 5;
		if (trou < debutJ || trou > finJ || plateau[trou] == 0)
			return false;
		int grainesAdverses = 0;
		for (int i = debutA; i <= finA; i++)
			grainesAdverses += plateau[i];
		if (grainesAdverses == 0) {
			int distance = (debutA - trou - 1 + 12) % 12 + 1;
			if (plateau[trou] < distance)
				return false;
		}
		return true;
	}

	public int jouercoup(int trou, boolean joueur1) {
		int graines = plateau[trou];
		plateau[trou] = 0;
		int position = trou;
		while (graines > 0) {
			position = (position + 1) % nb_trou;
			if (position != trou) {
				plateau[position]++;
				graines--;
			}
		}
		int captures = 0;
		int debutA = joueur1 ? 6 : 0;
		int finA = joueur1 ? 11 : 5;
		while (position >= debutA && position <= finA && (plateau[position] == 2 || plateau[position] == 3)) {
			captures += plateau[position];
			plateau[position] = 0;
			position = (position - 1 + nb_trou) % nb_trou;
		}
		return captures;
	}

	public static int simulerCoup(int[] plateau, int trou, boolean joueur1) {
		int[] plateauS = Arrays.copyOf(plateau, plateau.length);
		int graines = plateauS[trou];
		plateauS[trou] = 0;
		int position = trou;
		while (graines > 0) {
			position = (position + 1) % Awale.nb_trou;
			if (position == trou)
				continue;
			plateauS[position]++;
			graines--;
		}
		// capture des graines
		int captures = 0;
		int debutA = joueur1 ? 6 : 0; // premiere case de l'adversaire
		int finA = joueur1 ? 11 : 5; // derniere case de l'adversaire
		while (position >= debutA && position <= finA) {
			if (plateauS[position] == 2 || plateauS[position] == 3) {
				captures += plateauS[position];
				plateauS[position] = 0;
			} else
				// si on recupere pas de graine ou a finit notre tour
				break;
			position = (position - 1 + Awale.nb_trou) % Awale.nb_trou;
		}
		return captures;
	}

	public boolean finpartie(Joueur j1, Joueur j2, boolean joueur1) {
		if (j1.getScore() > 24 || j2.getScore() > 24)
			return true;
		int debutJ = joueur1 ? 0 : 6;
		int finJ = joueur1 ? 5 : 11;
		Joueur joueur = joueur1 ? j1 : j2;
		for (int i = debutJ; i <= finJ; i++)
			if (coupvalide(i, joueur1, plateau))
				return false;

		int recup = 0;
		for (int i = debutJ; i <= finJ; i++) {
			recup += plateau[i];
			plateau[i] = 0;
		}
		joueur.ajouterScore(recup);
		System.out.println("Fin de partie : " + joueur.getNom() + " récupère " + recup + " graines");
		return true;
	}
}
