package awale;

import java.util.*;

public class MCTSStrategie implements Strategie {
	private int nbSimulations;
	private Random rand = new Random();

	public MCTSStrategie() {
		nbSimulations = 1000;
	}

	public int choisirCoup(boolean joueur1, int[] plateau) {
		int meilleurCoup = -1;
		double meilleurScore = -1.0; // double car prend un taux comme valeur après

		// On regarde quels trous on peut jouer
		int debut = joueur1 ? 0 : 6;
		int fin = joueur1 ? 5 : 11;

		for (int coup = debut; coup <= fin; coup++) {
			if (Awale.coupvalide(coup, joueur1, plateau)) {

				// pour ce coup, on fait plein de simulations
				int victoires = 0;
				for (int i = 0; i < nbSimulations; i++) {
					if (simulerPartieAleatoire(plateau, coup, joueur1)) {
						victoires++;
					}
				}

				// On garde le coup qui a le meilleur taux de réussite
				double tauxReussite = (double) victoires / nbSimulations;
				if (tauxReussite > meilleurScore) {
					meilleurScore = tauxReussite;
					meilleurCoup = coup;
				}
			}
		}
		return meilleurCoup;
	}

	// Joue une partie au hasard jusqu'à ce qu'il n'y ait plus de coups
	private boolean simulerPartieAleatoire(int[] plateauInitial, int premierCoup, boolean monTour) {
		int[] plateauS = Arrays.copyOf(plateauInitial, plateauInitial.length);
		int monScore = 0;
		int scoreAdv = 0;

		// Jouer le premier coup choisi
		monScore += executerCoup(plateauS, premierCoup, monTour);
		boolean tourActuel = !monTour;

		// Continuer la partie au hasard (max 40 tours pour aller vite)
		for (int tour = 0; tour < 40; tour++) {
			List<Integer> coupsPossibles = getCoups(plateauS, tourActuel);
			if (coupsPossibles.isEmpty())
				break;

			int coupHasard = coupsPossibles.get(rand.nextInt(coupsPossibles.size()));
			int gain = executerCoup(plateauS, coupHasard, tourActuel);

			if (tourActuel == monTour)
				monScore += gain;
			else
				scoreAdv += gain;

			tourActuel = !tourActuel;
		}
		return monScore > scoreAdv;
	}

	// Méthode pour trouver les coups valides
	private List<Integer> getCoups(int[] p, boolean j1) {
		List<Integer> liste = new ArrayList<>();
		int debutJ = j1 ? 0 : 6;
		for (int i = debutJ; i < debutJ + 6; i++) {
			if (Awale.coupvalide(i, j1, p))
				liste.add(i);
		}
		return liste;
	}

	private int executerCoup(int[] p, int trou, boolean j1) {
		int graines = p[trou];
		p[trou] = 0;
		int pos = trou;
		while (graines > 0) {
			pos = (pos + 1) % 12;
			if (pos != trou) {
				p[pos]++;
				graines--;
			}
		}
		int cap = 0;
		int debutA = j1 ? 6 : 0;
		int finA = j1 ? 11 : 5;
		while (pos >= debutA && pos <= finA && (p[pos] == 2 || p[pos] == 3)) {
			cap += p[pos];
			p[pos] = 0;
			pos = (pos - 1 + 12) % 12;
		}
		return cap;
	}

}
