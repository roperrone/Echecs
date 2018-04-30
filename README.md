# Echecs
Jeu d'échec (projet)

Ce jeu d'échec comprend quasiment toutes les règles du jeu d'echec. Il contient les coups petit roque, grand roque, 
manger en passant ainsi que les déplacement standards. Lorsque que l'on clique sur une pièce, les cases de déplacements possibles de cette pièce
s'affichent en vert.
Lorsque le Roi est en Echec ou en Echec et Mate la case sur laquelle il se trouve devient rouge. Si il est en echec le joueur peut seulement
parrer l'échec. Tout autre déplacement ne sera pas valide.
A droite du plateau figure le compte des pièces. Lorsqu'une pièce est "mangée" le compteurs de la pièce concernée s'incrémente.
En dessous se trouve deux boutons l'un pour rejouer et l'autre pour abandonner

# Comment jouer

Pour lancer le jeu il faut ouvrir le fichier compile.sh en tapant dans le terminal "./compile.sh"
Il faut ensuite choisir le nombre de joueurs.
Si l'on joue seul il faut:
 - entrer son nom
 - choisir le niveau de difficulté de l'IA, à l'aide du slider (attention plus le niveau est élevé plus le temps d'attente est important - 
mieux vaut donc ne pas être pressé)
 - cliquer sur Commencer
Si l'on joue à deux il faut:
 -entrer les noms des joueurs
 -cliquer sur Commencer

# Partie terminée

Lorsque la partie est finie la Fenetre contenant le plateau se ferme après 2 secondes
et une autre fenetre s'ouvre. Cette fenetre annonce la couleur gagnante.
et offre plusieurs possibilités.
Si l'on clique sur Recommencer, une nouvelle partie contre le même joueur commence
Si l'on clique sur Nouvelle Partie, une nouvelle fenetre de choix du nombre de joueurs s'ouvre
Si l'on clique sur Fermer le jeu se ferme 