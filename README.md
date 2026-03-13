# 🎮 Awalé – Intelligence Artificielle en Java

Projet universitaire réalisé dans le cadre de l’UE **Initiation à l'Intelligence Artificielle** (Licence Informatique).  
L’objectif est d’implémenter le **jeu d’Awalé** en Java ainsi que plusieurs **stratégies d’intelligence artificielle** permettant de jouer automatiquement.

Le projet explore et compare différentes approches de prise de décision :
* Un joueur **aléatoire**.
* Des **fonctions heuristiques**.
* Une IA basée sur **Monte Carlo Tree Search (MCTS)**.

Le but est d’étudier les **performances, avantages et limites** de ces différentes stratégies dans un jeu de stratégie.

---

## 📂 Contenu du dépôt

Ce dépôt contient l'ensemble des éléments du projet :
* **Sujet** : Les consignes et règles de l'UE.
* **Rapport** : L'analyse détaillée des algorithmes et des résultats.
* **Code** : L'implémentation complète en Java.

---

## 🎲 Le jeu : Awalé

L’Awalé est un **jeu de stratégie traditionnel africain** appartenant à la famille des jeux Mancala.

### Principe
* Le plateau contient **12 trous** (6 par joueur).
* Les joueurs **sèment des graines** dans les trous du plateau.
* Selon certaines conditions, un joueur peut **capturer les graines de l’adversaire**.

### Fin de partie
La partie se termine lorsque :
* Un joueur capture **plus de 24 graines**.
* Ou **aucun coup valide n’est possible**.

Les règles du jeu sont implémentées dans la classe `Awale.java`.

---

## 🧠 Intelligences Artificielles implémentées

### RandomPlayer
* Choisit un **coup valide aléatoirement**.
* Sert de **référence de base** pour comparer les autres stratégies.

### Heuristiques
Deux fonctions heuristiques évaluent les coups possibles en prenant en compte :
* Les **graines capturées**.
* Les **pertes potentielles**.
* L’état du plateau.

Ces stratégies permettent une **prise de décision rapide**, mais restent **limitées à une vision à court terme**.

### Monte Carlo Tree Search (MCTS)
Implémentation de l’algorithme **Monte Carlo Tree Search**, qui consiste à :
1. Explorer l’arbre des coups possibles.
2. Simuler de nombreuses parties complètes.
3. Estimer la qualité d’un coup à partir des résultats des simulations.

Cette approche permet d’obtenir des décisions plus robustes mais **nécessite un temps de calcul important**.

---

## 🛠 Structure du projet

| Fichier | Rôle |
| :--- | :--- |
| **Main.java** | Point d’entrée du programme. |
| **Awale.java** | Implémentation des règles du jeu. |
| **Joueur.java** | Représentation d’un joueur. |
| **Strategie.java** | Interface des stratégies. |
| **RandomPlayer.java** | Joueur aléatoire. |
| **GreedyBestFirst.java** | Stratégie heuristique. |
| **AwaleState.java** | Représentation d’un état du jeu. |
| **MCTS.java** | Algorithme Monte Carlo Tree Search. |
| **MCTSNode.java** | Nœuds de l’arbre MCTS. |
| **MCTSStrategie.java** | Stratégie utilisant MCTS. |

---

## 🚀 Exécution

### Compilation
```bash
javac awale/*.java
```

## 📉 Limites et améliorations

### Limites

* **MCTS** est très coûteux en temps de calcul, ce qui limite son utilisation intensive.
* Les **heuristiques** ont une vision locale et ne prennent pas toujours en compte les réponses de l’adversaire.
* Les **simulations MCTS** étant aléatoires, un grand nombre d’itérations est nécessaire pour la fiabilité.

### Améliorations possibles

* Intégrer des **heuristiques dans les simulations MCTS**.
* Adapter **dynamiquement le nombre d’itérations**.
* Optimiser la **représentation des états du jeu**.
* Ajouter une **interface graphique**.
* Implémenter d’autres algorithmes comme **Minimax avec élagage alpha-bêta**.

---

## 👥 Auteurs

Projet réalisé par :

* Pierre-Léo Ducruet  
* Maëlle Guibert  
* Joyce Lama Adodo  
* Albane Perrier
