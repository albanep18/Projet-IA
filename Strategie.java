package awale;

/**
 * Adaptateur entre l'algorithme MCTS et l'interface Strategie utilisée dans Main.
 */
public class MCTSStrategie implements Strategie {

    private final int iterations;

    public MCTSStrategie(int iterations) {
        this.iterations = iterations;
    }

    /**
     * Appelée par Main à chaque tour.
     * Transforme l'état réel du jeu en AwaleState pour le MCTS.
     */
    @Override
    public int choisirCoup(boolean tourJ1, int[] plateau, int scoreJ1, int scoreJ2) {

        // Création de l'état racine du MCTS
        AwaleState root = new AwaleState(
                plateau,
                scoreJ1,
                scoreJ2,
                tourJ1
        );

        // Lancement de MCTS
        return MCTS.bestMove(root, iterations);
    }

    /**
     * Version par défaut (non utilisée ici, mais requise par l'interface)
     */
    @Override
    public int choisirCoup(boolean tourJ1, int[] plateau) {
        return choisirCoup(tourJ1, plateau, 0, 0);
    }
}
