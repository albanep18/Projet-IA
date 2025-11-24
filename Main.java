package awale;

public class Main extends Awale {
	public static void main(String[] args) {
		// Configuration des joueurs
		Joueur j1 = new Joueur("Joueur 1", true, new RandomPlayer());
		Joueur j2 = new Joueur("Joueur 2", false, new RandomPlayer());
		initialiserPlateau();
		boolean tourJ1 = true;
		while (!finpartie(j1, j2, tourJ1)) {
			// quijoue.estJoueur1()
			Joueur quijoue = tourJ1 ? j1 : j2;
			System.out.println("\nTour de " + quijoue.getNom());
			int coup = quijoue.getStrategie().choisirCoup(tourJ1, plateau);
			int captures = jouercoup(coup, tourJ1);
			quijoue.ajouterScore(captures);
			System.out.println(quijoue.getNom() + " a capturé " + captures + " graines !");
			tourJ1 = !tourJ1;
		}
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
