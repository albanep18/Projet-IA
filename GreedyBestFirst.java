package awale;

import awale.Awale;

public class GreedyBestFirst implements Strategie {
	
	public int choisirCoup(boolean joueur1, int[] plateau) {
		int debut = joueur1 ? 0 : 6;
		int fin = joueur1 ? 5 : 11;
		int meilleurTrou = -1;
		int maxCaptures = -1;

		for (int i = debut; i <= fin; i++) {
			if (!Awale.coupvalide(i, joueur1))
				continue;
			int captures = simulerCoup(plateau, i, joueur1);
			if (captures > maxCaptures) {
				maxCaptures = captures;
				meilleurTrou = i;
			}
		}

		// Si aucun coup ne rapporte de points, choisir le premier valide
		if (meilleurTrou == -1) {
			for (int i = debut; i <= fin; i++) {
				if (Awale.coupvalide(i, joueur1)) {
					meilleurTrou = i;
					break;
				}
			}
		}

		return meilleurTrou;
	}
	
	private int simulerCoup(int[] plateau, int trou, boolean joueur1) {
		int graines = plateau[trou];
		plateau[trou] = 0;
		int position = trou;

		while (graines > 0) {
			position = (position + 1) % Awale.nb_trou;
			if (position == trou)
				continue;
			plateau[position]++;
			graines--;
		}

		int captures = 0;
		while (true) {
			if (joueur1 && position >= 6 && position < 12 && (plateau[position] == 2 || plateau[position] == 3)) {
				captures += plateau[position];
				plateau[position] = 0;
			} else if (!joueur1 && position >= 0 && position < 6 && (plateau[position] == 2 || plateau[position] == 3)) {
				captures += plateau[position];
				plateau[position] = 0;
			} else
				break;
			position = (position - 1 + Awale.nb_trou) % Awale.nb_trou;
		}
		return captures;
	}

}

/*
	public static int meilleurCoup(boolean joueur1) {
		if (joueur1) {
			// jouer le coup qui rapporte le plus au joueur 1
			int meilleurTrou = 0;
			for (int i = 0; i < 5; i++) {
				if (jouercoup(i, joueur1) < jouercoup(i + 1, joueur1))
					meilleurTrou = i + 1;
			}
			if (jouercoup(meilleurTrou, joueur1) != 0)
				return meilleurTrou;

		} else {
			// jouer le coup qui rapporte le plus au joueur 2
			int meilleurTrou = 0;
			for (int i = 6; i < 11; i++) {
				if (jouercoup(i, joueur1) < jouercoup(i + 1, joueur1))
					meilleurTrou = i + 1;
			}
			if (jouercoup(meilleurTrou, joueur1) != 0)
				return meilleurTrou;

			// ensuite faut trouver une strategie de coup a jouer
			// lorsqu'aucun coup ne peut rapporter de points
		}			
		return 1;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}*/
