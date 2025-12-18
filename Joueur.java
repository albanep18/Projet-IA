
package awale;
public class Joueur {
	private String nom;
	private boolean estJoueur1;
	private Strategie strategie;
	private int score;
	public Joueur(String nom, boolean estJoueur1, Strategie strategie) {
		this.nom = nom;
		this.estJoueur1 = estJoueur1;
		this.strategie = strategie;
		this.score = 0;
	}
	public String getNom() {
		return nom;
	}
	public boolean estJoueur1() {
		return estJoueur1;
	}
	public Strategie getStrategie() {
		return strategie;
	}
	public int getScore() {
		return score;
	}
	public void ajouterScore(int points) {
		score += points;
	}
}
