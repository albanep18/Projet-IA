
package awale;
public class Main {
   public static void main(String[] args) {
       Awale jeu = new Awale();
       jeu.initialiserPlateau();
       Joueur j1 = new Joueur("Joueur 1", true, new RandomPlayer());
       Joueur j2 = new Joueur("Joueur 2", false, new MCTSStrategie());
       boolean tourJ1 = true;
       while (!jeu.finpartie(j1, j2, tourJ1)) {
           Joueur joueur = tourJ1 ? j1 : j2;
           System.out.println("\nTour de " + joueur.getNom());
           int coup = joueur.getStrategie().choisirCoup(tourJ1, jeu.plateau);
           if (!Awale.coupvalide(coup, tourJ1, jeu.plateau)) {
               System.out.println("Aucun coup valide !");
               break;
           }
           int captures = jeu.jouercoup(coup, tourJ1);
           joueur.ajouterScore(captures);
           System.out.println(joueur.getNom() +
                   " joue le trou " + coup +
                   " et capture " + captures + " graines");
           tourJ1 = !tourJ1;
       }
       System.out.println("\n🎯 Partie terminée !");
       System.out.println(j1.getNom() + " : " + j1.getScore());
       System.out.println(j2.getNom() + " : " + j2.getScore());
   }
}
