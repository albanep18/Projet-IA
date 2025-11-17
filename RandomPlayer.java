package awale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlayer implements Strategie {
	private static final Random rand = new Random();

	public int choisirCoup(boolean joueur1, int[] plateau) {
		List<Integer> coupsValides = new ArrayList<>();
		int debut = joueur1 ? 0 : 6;
		int fin = joueur1 ? 5 : 11;

		for (int i = debut; i <= fin; i++) {
			if (Awale.coupvalide(i, joueur1))
				coupsValides.add(i);
		}

		if (coupsValides.isEmpty())
			return debut; // au cas où aucun coup n’est valide
		return coupsValides.get(rand.nextInt(coupsValides.size()));
	}
}
