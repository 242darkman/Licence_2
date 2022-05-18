     TRAVAUX PRATIQUES I.A EN SECURITE ET AIDE A LA DECISION : JEU D'INFECTION
------------------------------------------------------------------------------------

II - COMPILATION
----------------------------------------------------------
1 - Se placer dans le repertoire jeu-d-infection 
2 - Saisissez la commande :  javac -d "build" infection/*/*.java

III - EXECUTION
---------------------------------------------------------
1 -> java -cp build/ main.Main arg1 arg2 arg3 arg4 arg5 arg6 

	- arg1 : le nombre de lignes de la grille de jeu
	- arg2 : le nombre de colones de la grille de jeu
	- arg3 : le nombre de coup d'avance pour le joueur blanc
	- arg4 : la profondeur de raisonnement du joueur blanc
	- arg5 : la profondeur de raisonnement du joueur noir
	- arg6 : le type d'algorithme (true : MinMax / false : AlphaBeta)


---------------------------------------------------------
Exemple:
	- java -cp build/ main.Main 4 4 2 4 5 true
	- java -cp build/ main.Main 4 4 2 5 2 false
	- java -cp build/ main.Main 6 6 2 4 3 false
	- java -cp build/ main.Main 6 6 2 4 7 true
	- java -cp build/ main.Main 5 5 3 5 4 true
	- java -cp build/ main.Main 5 5 3 4 5 false


Règle: 
    Lorsqu'un joueur mange tous les pions de son adversaire, il gagne. Si aucun des deux joueurs ne peut jouer un coup valide c'est à dire si la grille est remplie ; le gagnant est celui qui a le plus de pions dans la grille. Dans le cas contraire on a un match nul c'est a dire le nombre de pions du joueur 1 est égale au nombre de pions du joueur 2
