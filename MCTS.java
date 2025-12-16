package awale;

import java.util.List;
import java.util.Random;

/**
 * Implémentation complète de l'algorithme MCTS :
 * Sélection → Expansion → Simulation → Rétropropagation
 */
public class MCTS {

    private static final Random rand = new Random();

    /**
     * 3) SIMULATION
     * Joue une partie aléatoire jusqu'à la fin depuis un état donné.
     * Retourne 1 si le joueur racine gagne, 0 sinon.
     */
    private static double simulateRandomGame(AwaleState state, boolean rootIsJ1) {

        // Copie de l'état pour ne jamais modifier l'arbre
        AwaleState sim = new AwaleState(
                state.getBoard(),
                state.getScoreSouth(),
                state.getScoreNorth(),
                state.isJ1ToPlay()
        );

        // Partie jouée aléatoirement
        while (!sim.isTerminal()) {
            List<Integer> moves = sim.getValidMoves();
            if (moves.isEmpty()) break;
            int move = moves.get(rand.nextInt(moves.size()));
            sim = sim.playMove(move);
        }

        // Résultat du point de vue du joueur racine
        boolean j1Wins = sim.getScoreSouth() > sim.getScoreNorth();
        return (j1Wins == rootIsJ1) ? 1.0 : 0.0;
    }

    /**
     * Lance MCTS depuis l'état racine et retourne le meilleur coup.
     */
    public static int bestMove(AwaleState rootState, int iterations) {

        // Racine de l'arbre
        MCTSNode root = new MCTSNode(rootState, null, -1);

        // Joueur que l'on cherche à faire gagner
        boolean rootIsJ1 = rootState.isJ1ToPlay();

        for (int i = 0; i < iterations; i++) {

            // 1) SÉLECTION
            MCTSNode node = root;
            while (!node.isTerminal() && node.isFullyExpanded()) {
                node = node.selectChild();
            }

            // 2) EXPANSION
            if (!node.isTerminal()) {
                MCTSNode expanded = node.expand();
                if (expanded != null) node = expanded;
            }

            // 3) SIMULATION
            double result = simulateRandomGame(node.getState(), rootIsJ1);

            // 4) RÉTROPROPAGATION
            node.backpropagate(result);
        }

        // Choix final : enfant le plus visité
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

