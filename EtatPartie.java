import java.util.ArrayList;
import java.util.List;

/**
 * État autonome du jeu Awalé, adapté à la dernière version de Awale.java fournie.
 * - Reproduit fidèlement la logique : coupvalide, couppossible, jouercoup, finpartie.
 * - Ne modifie jamais d'état extérieur (Awale.plateau ou Joueur).
 * - Immutable : playMove(...) retourne un nouvel AwaleState.
 */
public class AwaleState {

    private static final int NB_TROU = 12;

    private final int[] board;   // 12 cases : 0..11 (0..5 = joueur1, 6..11 = joueur2)
    private final int scoreJ1;   // score joueur 1 (sud)
    private final int scoreJ2;   // score joueur 2 (nord)
    private final boolean j1ToPlay;

    public AwaleState(int[] board, int scoreJ1, int scoreJ2, boolean j1ToPlay) {
        if (board == null || board.length != NB_TROU)
            throw new IllegalArgumentException("board must be length " + NB_TROU);
        this.board = board.clone();
        this.scoreJ1 = scoreJ1;
        this.scoreJ2 = scoreJ2;
        this.j1ToPlay = j1ToPlay;
    }

    public static AwaleState initialState() {
        int[] init = new int[NB_TROU];
        for (int i = 0; i < NB_TROU; i++) init[i] = 4;
        return new AwaleState(init, 0, 0, true);
    }

    /* ---------------- internal rules (mirror de la dernière version de Awale.java) ---------------- */

    /**
     * Logique de vérification d'un coup identique à coupvalide(...).
     * NOTE: on reproduit exactement les calculs (y compris la formule utilisée
     * pour vérifier la capacité à "nourrir" l'adversaire).
     */
    private boolean coupValide(int trou, boolean joueur1) {
        int debutJ = joueur1 ? 0 : 6;
        int finJ   = joueur1 ? 5 : 11;
        int debutA = joueur1 ? 6 : 0;
        int finA   = joueur1 ? 11 : 5;

        if (trou < debutJ || trou > finJ || board[trou] == 0)
            return false;

        // somme des graines du camp adverse
        int cpt = 0;
        for (int i = debutA; i <= finA; i++) {
            cpt += board[i];
        }

        if (cpt == 0) {
            // reproduit la même condition que dans ton Awale.java
            if (board[trou] < 6 - trou)
                return false;
        }
        return true;
    }

    /**
     * Génère les coups possibles (comme couppossible(...)), mais retourne une List<Integer>.
     * (MCTS attend une structure itérable facile à utiliser.)
     */
    public List<Integer> getValidMoves() {
        List<Integer> moves = new ArrayList<>();
        int debutJ = j1ToPlay ? 0 : 6;
        int finJ   = j1ToPlay ? 5 : 11;
        for (int i = debutJ; i <= finJ; i++) {
            if (coupValide(i, j1ToPlay)) moves.add(i);
        }
        return moves;
    }

    /**
     * Applique le coup (semis + captures) en reproduisant jouercoup(...) et renvoie
     * un nouvel AwaleState (ne modifie pas this).
     * Retourne IllegalArgumentException si move hors bornes.
     */
    public AwaleState playMove(int move) {
        if (move < 0 || move >= NB_TROU) throw new IllegalArgumentException("move out of range");
        // (optionnel) on pourrait vérifier la validité ici : if (!coupValide(move, j1ToPlay)) throw ...
        return jouerCoup(move, j1ToPlay);
    }

    /** Implémentation du semis + captures correspondant à ton jouercoup(...) */
    private AwaleState jouerCoup(int trou, boolean joueur1) {
        int[] newBoard = board.clone();
        int newScore1 = scoreJ1;
        int newScore2 = scoreJ2;

        // Déposage (semis)
        int graines = newBoard[trou];
        newBoard[trou] = 0;
        int position = trou;
        while (graines > 0) {
            position = (position + 1) % NB_TROU;
            if (position == trou) continue; // ne replante pas dans la case d'origine
            newBoard[position]++;
            graines--;
        }

        // Captures : on détermine début/fin du camp adverse selon joueur
        int debutA = joueur1 ? 6 : 0;
        int finA   = joueur1 ? 11 : 5;

        int captures = 0;
        // La boucle suit la même logique : tant que la position est dans le camp adverse,
        // si la case vaut 2 ou 3 on capture, sinon on break.
        while (position >= debutA && position <= finA) {
            if (newBoard[position] == 2 || newBoard[position] == 3) {
                captures += newBoard[position];
                newBoard[position] = 0;
            } else {
                break;
            }
            position = (position - 1 + NB_TROU) % NB_TROU;
        }

        if (joueur1) newScore1 += captures;
        else newScore2 += captures;

        return new AwaleState(newBoard, newScore1, newScore2, !joueur1);
    }

    /**
     * Reproduit la logique de finpartie(J1,J2,joueur1) — sans toucher à des objets Joueur.
     * - Si un des scores internes dépasse 24 => terminal.
     * - Sinon : si le joueur courant n'a aucun coup valide => terminal (c.f. ton implémentation).
     *
     * NOTE : dans ton Awale.java, finpartie(...) récupère les graines restantes dans le camp du
     * joueur (via joueurT.ajouterScore(...)). Ici on **ne modifie pas** les objets extérieurs ;
     * pour reproduire ce comportement, utilise collectRemainingSeeds() sur un état terminal.
     */
    public boolean isTerminal() {
        // majorité atteinte
        if (scoreJ1 > 24 || scoreJ2 > 24) return true;

        int debutJ = j1ToPlay ? 0 : 6;
        int finJ   = j1ToPlay ? 5 : 11;

        // si le joueur courant a au moins un coup valide -> pas terminal
        for (int i = debutJ; i <= finJ; i++) {
            if (coupValide(i, j1ToPlay)) return false;
        }
        // sinon terminal (et selon Awale.java on devrait collecter les graines du camp)
        return true;
    }

    /* ---------------- getters et utilitaires ---------------- */

    /** Renvoie une copie du plateau pour sécurité immuable */
    public int[] getBoard() {
        return board.clone();
    }

    public int getScoreSouth() { return scoreJ1; }
    public int getScoreNorth() { return scoreJ2; }
    public boolean isJ1ToPlay() { return j1ToPlay; }
    public boolean isNorthToPlay() { return !j1ToPlay; }

    /**
     * Si tu veux reproduire l'effet de finpartie(J1,J2,...) qui ajoute les graines
     * restantes au score du joueur dont c'était le tour, appelle cette méthode sur un
     * état terminal. Elle retourne un nouvel état avec les graines du camp du joueur
     * ajouté à son score et les cases vidées.
     *
     * (C'est équivalent à ce que fait ton Awale.finpartie quand il appelle joueurT.ajouterScore(...))
     */
    public AwaleState collectRemainingSeeds() {
        if (!isTerminal()) return this;

        int[] newBoard = board.clone();
        int newScore1 = scoreJ1;
        int newScore2 = scoreJ2;

        int debutJ = j1ToPlay ? 0 : 6;
        int finJ   = j1ToPlay ? 5 : 11;

        for (int i = debutJ; i <= finJ; i++) {
            if (j1ToPlay) newScore1 += newBoard[i];
            else newScore2 += newBoard[i];
            newBoard[i] = 0;
        }

        return new AwaleState(newBoard, newScore1, newScore2, j1ToPlay);
    }
}

