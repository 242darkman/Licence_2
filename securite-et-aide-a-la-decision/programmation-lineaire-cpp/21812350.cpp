#include <lpsolve/lp_lib.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <math.h>
#include <stdlib.h>
#include <cstdlib>
						// 21812350 VOUVOU Brandon
using namespace std;


/*
	Pour compiler : g++ 21812350.cpp -llpsolve55 -lcolamd -ldl -o coloration.exe
	Pour lancer   : ./coloration.exe graph.dimacs
*/

/******************************************** COLORATION DE GRAPHE **********************************************************/
			// 1. Trouvons la modélisation du problème
	/* a) Les variables représentent la coloration des noeuds et les couleurs potentielles (k couleurs & n noeuds)
	 *
	 * b) Les contraintes du problème sont :
	 *		----> contrainte sur chaque noeuds dans l'intervalle [0,1]
	 *		----> contrainte sur les couleurs dans l'intervalle [0,1]
	 *		----> contrainte sur les liens entre les noeuds
	 * 		----> contrainte pour chaque noeuds
	 *
	 * c) La fonction objectif à optimiser est celle qui minimise le nombre de couleurs nécessaires 
	 *    pour colorier le graphe. Elle correspond à la somme des couleurs utilisées.
	 *
	 ***/
	 
	 
	 
			// 2. En utilisant la modélisation précédente, écrivons un programme en C/C++ permettant de trouver le
			//    nombre le plus petit de couleurs à utiliser pour colorier un graphe donné en entrée.
void vider_row(REAL row[], const int& taille){
    for(int i = 0; i < taille; i++){
        row[i] = 0.0;
    }
}


int main(int argc, char* argv[]){
	lprec * lp;
	int nombreDeNoeuds = 0;
	int nombreArcs     = 0;
	
	
	std :: ifstream monFichier ("graph.dimacs");
	
	if (!monFichier.is_open()){
		std :: cout << "Impossible d'ouvrir le fichier du graphe" << std :: endl;
		exit(EXIT_FAILURE);
	}
	
	std :: string ligne;
	
	
	// Lecture des problèmes
	while(getline(monFichier,ligne)){
		std::string m;
		std::istringstream iss(ligne);
		iss >> m;
		
		if(m == "p"){
			iss >> m;
			iss >> m;
			nombreDeNoeuds = stoi(m);
			iss >> m;
			nombreArcs = stoi(m);
			break;
		}
	}
	
	
	// création du lp_solve et vecteur row
	lp = make_lp(0,nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds);
	REAL row[nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds + 1];
	vider_row(row, nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds + 1);
	
	for(int i = 1; i <= (nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds); i++){
		row[i] = 1.0;
		set_int(lp,i,true);
		add_constraint(lp,row,GE,0);
		add_constraint(lp,row,LE,1);
		row[i] = 0.0;
	}
	
	for(int i = 0; i < nombreDeNoeuds; i++){
		for(int j = 0; j < nombreDeNoeuds; j++){
			row[i * nombreDeNoeuds + j + 1] = 1.0;
		}
		add_constraint(lp,row,EQ,1);
		vider_row(row,nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds + 1);
	}
	
	
	// Lecture d'une contrainte
	while(getline(monFichier,ligne)){
		std :: string m;
		std :: istringstream iss(ligne);
		iss >> m;
		
		if(m == "e"){
			iss >> m;
			int pN = stoi(m);
			iss >> m;
			int sN = stoi(m);
			
			for(int i = 1; i <= nombreDeNoeuds; i++){
				row[pN * nombreDeNoeuds - nombreDeNoeuds + i] = 1.0;
				row[sN * nombreDeNoeuds - nombreDeNoeuds + i] = 1.0;
				row[(nombreDeNoeuds * nombreDeNoeuds + 1)+i]    = -1.0;
				add_constraint(lp,row,LE,0);
				vider_row(row,nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds + 1);
			}
		}
	}
	
	
	
	
	// Modélisation de la fonction objectif
	for(int i = 0; i < nombreDeNoeuds; i++){
		row[nombreDeNoeuds * nombreDeNoeuds + i + 1] = 1.0;
	}
	set_obj_fn(lp,row);
	vider_row(row, nombreDeNoeuds * nombreDeNoeuds + nombreDeNoeuds + 1);
	
	if(solve(lp)!=0){
		fprintf(stderr, "INFAISABLE\n");
		return 1;
	}
	else {
		get_variables(lp,row);
		int compteur;
		
		for(int i = 0; i < nombreDeNoeuds; i++){
			for(int j = 1; j < nombreDeNoeuds; j++){
				compteur++;
				
				if(row[i * nombreDeNoeuds + j] == 1) break;
			}
			std :: cout << "Noeud " << (i+1) << " -----> couleur :" << compteur << std :: endl;
			compteur = 0;
		}
		print_objective(lp);
		
		std::cout << "Nombre Arretes : " << nombreArcs << std::endl;
		
		std::cout << "Nombre total de Noeuds : " << nombreDeNoeuds << std::endl;
		
		std::cout << "Nombre de couleur nécessaire : " << get_objective(lp) << std::endl;
	}
	return 0;
}



/************************************** SOLUTION OPTIMALE MODÉLISATION ******************************************************
 * 
 *	Nombre Arrete : 20
 *
 *	Model size:      495 constraints,     132 variables
 *
 *	Noeud 1 -----> couleur :1
 *	Noeud 2 -----> couleur :6
 *	Noeud 3 -----> couleur :1
 *	Noeud 4 -----> couleur :6
 *	Noeud 5 -----> couleur :10
 *	Noeud 6 -----> couleur :1
 *	Noeud 7 -----> couleur :6
 *	Noeud 8 -----> couleur :1
 *	Noeud 9 -----> couleur :6
 *	Noeud 10 -----> couleur :10
 *	Noeud 11 -----> couleur :7
 *	
 *	Value of objective function: 4.00000000
 * 
 *	Nombre total de Noeuds : 11
 * 
 * 	Nombre de couleur nécessaire : 4
 *
 * 
 ***************************************************************************************************************************/