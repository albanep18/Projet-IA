Awalé – Intelligence Artificielle (Java)

Ce projet a été réalisé dans le cadre d’un projet universitaire de licence.
Il consiste à implémenter le jeu d’Awalé en Java ainsi que plusieurs intelligences artificielles permettant de jouer automatiquement.
Le projet compare différentes approches :
un joueur aléatoire,
des fonctions heuristiques,
une intelligence artificielle basée sur Monte Carlo Tree Search (MCTS).
L’objectif est d’étudier les avantages et les limites de ces différentes stratégies.

Règles du jeu :
L’Awalé est un jeu de stratégie à deux joueurs composé de 12 trous.
Chaque joueur contrôle 6 trous. Les joueurs sèment les graines et peuvent en capturer selon des règles précises.
La partie se termine lorsqu’un joueur dépasse 24 graines ou lorsqu’aucun coup valide n’est possible.
Les règles sont implémentées dans la classe Awale.
Intelligences artificielles
RandomPlayer : choisit un coup valide aléatoirement.
Heuristiques : deux fonctions heuristiques évaluant les coups à partir de critères comme les gains et les pertes potentielles.
MCTS : implémentation de l’algorithme Monte Carlo Tree Search permettant d’évaluer les coups à partir de simulations de parties complètes.

Structure du projet :
awale/
├── Main.java
├── Awale.java
├── Joueur.java
├── Strategie.java
├── RandomPlayer.java
├── GreedyBestFirst.java
├── AwaleState.java
├── MCTS.java
├── MCTSNode.java
├── MCTSStrategie.java

Éxecution:

javac awale/*.java
java awale.Main

Limites et améliorations:

MCTS beaucoup trop couteux en temps de calcul ce qui le rend peu utilisable.
Les heuristiques ont une vision à court terme et ne prennent pas en compte les coups de l’adversaire.
Les simulations MCTS sont aléatoires et peuvent nécessiter un grand nombre d’itérations.
Des améliorations possibles incluent l’intégration d’heuristiques dans MCTS et l’adaptation dynamique du nombre d’itérations.

Ducruet Pierre-Léo     -	Guibert Maëlle       -	Lama Adodo Joyce    -	Perrier Albane

