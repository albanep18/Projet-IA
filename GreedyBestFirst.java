package awale;

import java.util.Arrays;

public class GreedyBestFirst implements Strategie {
	public int choisirCoup(boolean joueur1, int[] plateau) {
		int debut = joueur1 ? 0 : 6;
		int fin = joueur1 ? 5 : 11;
		int meilleurTrou = -1;
		int maxCaptures = -1;
		for (int i = debut; i <= fin; i++) {
			if (!Awale.coupvalide(i, joueur1, plateau))
				continue;
			int captures = Awale.simulerCoup(plateau, i, joueur1);
			if (captures > maxCaptures) {
				maxCaptures = captures;
				meilleurTrou = i;
			}
		}
		// Si aucun coup ne rapporte de points, choisir le premier valide
		if (meilleurTrou == -1) {
			for (int i = debut; i <= fin; i++) {
				if (Awale.coupvalide(i, joueur1, plateau)) {
					meilleurTrou = i;
					break;
				}
			}
		}
		return meilleurTrou;
	}


}
