
public class AwaleAI {

    public static int alphaBeta(AwaleState state, int depth, int alpha, int beta, boolean maximizingPlayer) {

        if (depth == 0 || state.isTerminal()) {
            return state.evaluate();
        }

        if (maximizingPlayer) {     // Joueur IA (Sud)
            int maxEval = Integer.MIN_VALUE;

            for (int move : state.getValidMoves()) {
                AwaleState next = state.playMove(move);
                int eval = alphaBeta(next, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) break; // Élagage
            }
            return maxEval;

        } else {                    // Adversaire (Nord)
            int minEval = Integer.MAX_VALUE;

            for (int move : state.getValidMoves()) {
                AwaleState next = state.playMove(move);
                int eval = alphaBeta(next, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) break; // Élagage
            }
            return minEval;
        }
    }

    // Trouve le meilleur coup pour IA (Sud)
    public static int bestMove(AwaleState state, int depth) {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int move : state.getValidMoves()) {
            AwaleState next = state.playMove(move);
            int value = alphaBeta(next, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }
}
