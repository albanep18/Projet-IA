package awale;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un nœud de l'arbre MCTS.
 * Chaque nœud correspond à un état du jeu Awalé après un coup.
 */
public class MCTSNode {

    // État du jeu associé à ce nœud
    private final AwaleState state;

    // Parent dans l'arbre (null pour la racine)
    private final MCTSNode parent;

    // Enfants générés depuis ce nœud
    private final List<MCTSNode> children;

    // Nombre de simulations passées par ce nœud
    private int visits;

    // Nombre de victoires (du point de vue du joueur racine)
    private double wins;

    // Coup joué depuis le parent pour arriver à ce nœud
    private final int moveFromParent;

    public MCTSNode(AwaleState state, MCTSNode parent, int moveFromParent) {
        this.state = state;
        this.parent = parent;
        this.moveFromParent = moveFromParent;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.wins = 0.0;
    }

    /**
     * Un nœud est complètement développé si tous les coups possibles
     * ont déjà été explorés.
     */
    public boolean isFullyExpanded() {
        return children.size() == state.getValidMoves().size();
    }

    /** Un nœud est terminal si la partie est finie dans cet état */
    public boolean isTerminal() {
        return state.isTerminal();
    }

    /**
     * 1) SÉLECTION
     * Choisit l'enfant avec la meilleure valeur UCT
     * (équilibre exploration / exploitation).
     */
    public MCTSNode selectChild() {
        MCTSNode best = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        for (MCTSNode child : children) {

            // Exploitation : taux de victoire
            double exploitation = child.wins / (child.visits + 1e-9);

            // Exploration : favorise les nœuds peu visités
            double exploration =
                    Math.sqrt(2.0 * Math.log(this.visits + 1.0) / (child.visits + 1e-9));

            double uct = exploitation + exploration;

            if (uct > bestValue) {
                bestValue = uct;
                best = child;
            }
        }
        return best;
    }

    /**
     * 2) EXPANSION
     * Crée un nouvel enfant correspondant à un coup encore inexploré.
     */
    public MCTSNode expand() {
        List<Integer> possibleMoves = state.getValidMoves();

        for (int move : possibleMoves) {

            // Vérifie si ce coup a déjà été exploré
            boolean alreadyExpanded = false;
            for (MCTSNode c : children) {
                if (c.moveFromParent == move) {
                    alreadyExpanded = true;
                    break;
                }
            }

            // Si le coup est nouveau, on crée un nouvel enfant
            if (!alreadyExpanded) {
                AwaleState nextState = state.playMove(move);
                MCTSNode childNode = new MCTSNode(nextState, this, move);
                children.add(childNode);
                return childNode;
            }
        }
        return null;
    }

    /**
     * 4) RÉTROPROPAGATION
     * Met à jour les statistiques du nœud et de tous ses ancêtres.
     */
    public void backpropagate(double result) {
        visits++;
        wins += result;
        if (parent != null) parent.backpropagate(result);
    }

    /* -------- Getters -------- */

    public AwaleState getState() { return state; }
    public int getMoveFromParent() { return moveFromParent; }
    public List<MCTSNode> getChildren() { return children; }
    public int getVisits() { return visits; }
}


    public MCTSNode selectChild() {
        MCTSNode best = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        for (MCTSNode child : children) {
            // UCT classique
            double exploitation = child.wins / (child.visits + 1e-9);
            double exploration = Math.sqrt(2.0 * Math.log(this.visits + 1.0) / (child.visits + 1e-9));
            double uct = exploitation + exploration;

            if (uct > bestValue) {
                bestValue = uct;
                best = child;
            }
        }
        return best;
    }

    public MCTSNode expand() {
        List<Integer> possibleMoves = state.getValidMoves();

        for (int move : possibleMoves) {
            boolean alreadyExpanded = false;
            for (MCTSNode c : children) {
                if (c.moveFromParent == move) {
                    alreadyExpanded = true;
                    break;
                }
            }

            if (!alreadyExpanded) {
                AwaleState next = state.playMove(move);
                MCTSNode childNode = new MCTSNode(next, this, move);
                children.add(childNode);
                return childNode;
            }
        }
        return null;
    }

    public void backpropagate(double result) {
        visits++;
        wins += result;
        if (parent != null) parent.backpropagate(result);
    }

    public AwaleState getState() { return state; }
    public int getMoveFromParent() { return moveFromParent; }
    public List<MCTSNode> getChildren() { return children; }
    public int getVisits() { return visits; }
}




