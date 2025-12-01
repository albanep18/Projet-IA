




public class MCTS {

    private static Random rand = new Random();

    // Simule une partie aléatoire
    private static double simulateRandomGame(AwaleState state) {
        AwaleState sim = new AwaleState(state.getBoard(), state.getScoreNorth(), state.getScoreSouth(), state.isNorthToPlay());

        while (!sim.isTerminal()) {
            List<Integer> moves = sim.getValidMoves();
            if (moves.isEmpty()) break;
            int move = moves.get(rand.nextInt(moves.size()));
            sim = sim.playMove(move);
        }

        if (sim.getScoreSouth() > sim.getScoreNorth()) return 1.0;
        return 0.0;
    }

    // Exécute MCTS à partir d'une position donnée
    public static int bestMove(AwaleState rootState, int iterations) {
        MCTSNode root = new MCTSNode(rootState, null, -1);

        for (int i = 0; i < iterations; i++) {
            // 1. Sélection
            MCTSNode node = root;
            while (!node.isTerminal() && node.isFullyExpanded()) {
                node = node.selectChild();
            }

            // 2. Expansion
            if (!node.isTerminal()) {
                node = node.expand();
            }

            // 3. Simulation
            double result = simulateRandomGame(node.getState());

            // 4. Rétropropagation
            node.backpropagate(result);
        }

        // Choisir le meilleur coup : celui avec le plus de visites
        MCTSNode best = null;
        int maxVisits = -1;
        for (MCTSNode child : root.getChildren()) {
            if (child.getVisits() > maxVisits) {
                maxVisits = child.getVisits();
                best = child;
            }
        }

        return best != null ? best.getMoveFromParent() : -1;
    }
}

