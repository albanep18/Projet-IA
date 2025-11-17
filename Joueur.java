package awale;

import awaleTest.Strategies;

public class Joueur {
	private String nom;
	private boolean estJoueur1;
	private Strategies strategies;
	private int score;

	public Joueur(String nom, boolean estJoueur1, Strategies strategies) {
		this.nom = nom;
		this.estJoueur1 = estJoueur1;
		this.strategies = strategies;
		this.score = 0;
	}

	public String getNom() {
		return nom;
	}

	public boolean estJoueur1() {
		return estJoueur1;
	}

	public Strategies getStrategie() {
		return strategies;
	}

	public int getScore() {
		return score;
	}

	public void ajouterScore(int points) {
		score += points;
	}
}
