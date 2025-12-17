package awale;

public class MCTSStrategie implements Strategie {
    private final int iterations;

    public MCTSStrategie(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public int choisirCoup(boolean tourJ1, int[] plateau, int scoreJ1, int scoreJ2) {
        AwaleState root = new AwaleState(plateau, scoreJ1, scoreJ2, tourJ1);
        return MCTS.bestMove(root, iterations);
    }

    @Override
    public int choisirCoup(boolean tourJ1, int[] plateau) {
        return choisirCoup(tourJ1, plateau, 0, 0);
    }
}

