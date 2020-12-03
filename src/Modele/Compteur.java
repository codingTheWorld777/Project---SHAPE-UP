package Modele;

public class Compteur {
	/**
	 * @author Pierre-Louis DAMBRAINE, Huu Khai NGUYEN (Alec)
	 */
	
	
	private int[] pointsJoueurs;
	
	//constructor
	public Compteur(Carte[][] tableDuJeu) {
		//initialization of pointsJoueurs 
		this.pointsJoueurs = new int [Partie.joueursEnJeu.length];

		for (int k = 0; k < Partie.joueursEnJeu.length; k++) {	//k parcours les joueurs en jeu
			this.pointsJoueurs[k] = 0;
			
			//1. On compte les points obtenus sur les lignes
			//2. On compte les points obtunus sur les colonnes
			
			//1.
			//on compte les points obtenus du joueur k sur les formes
			for (int i = Plateau.yMinDuTapis; i <= Plateau.yMaxDuTapis; i++) {	//i parcours les lignes
				int j = Plateau.xMinDuTapis;
				
				while (j <= Plateau.xMaxDuTapis) {	//j parcours les colonnes
					if (tableDuJeu[i][j] != null) {
						if (Partie.joueursEnJeu[k].getCarteVictoire().getForme() != tableDuJeu[i][j].getForme()) {
							j++;
							
						} else if (j + 1 <= Plateau.xMaxDuTapis && tableDuJeu[i][j + 1] != null && Partie.joueursEnJeu[k].getCarteVictoire().getForme() == tableDuJeu[i][j + 1].getForme()) {
							this.pointsJoueurs[k]++;
							j++;
							
						} else j++;	
						
					} else j++;
				}
				
			}
	
		/******************************************************************************************************************************************/
			
			//on compte les points obtenus du joueur k sur les natures
			for (int i = Plateau.yMinDuTapis; i <= Plateau.yMaxDuTapis; i++) {	//i parcours les lignes
				int j = Plateau.xMinDuTapis;
				int compteur = 1;
				
				while (j <= Plateau.xMaxDuTapis) {	//j parcours les colonnes
					if (tableDuJeu[i][j] != null) {
						if (Partie.joueursEnJeu[k].getCarteVictoire().getNature() != tableDuJeu[i][j].getNature()) {
							j++;
					
						} else if (j + 1 <= Plateau.xMaxDuTapis && tableDuJeu[i][j + 1] != null && Partie.joueursEnJeu[k].getCarteVictoire().getNature() == tableDuJeu[i][j + 1].getNature()) {
							compteur++;
							j++;
							
						} else j++;
						
					} else j++;
				}
					
				if (compteur > 2) {
					this.pointsJoueurs[k] += compteur;
				}
			}
			
		/******************************************************************************************************************************************/
			
			//on compte les points obtenus du joueur k sur les couleurs
			for (int i = Plateau.yMinDuTapis; i <= Plateau.yMaxDuTapis; i++) { //i parcours les lignes
				int j = Plateau.xMinDuTapis;
				int compteur = 1;
				
				while (j <= Plateau.xMaxDuTapis) {//j parcours les colonnes
					if (tableDuJeu[i][j] != null) {
						if (Partie.joueursEnJeu[k].getCarteVictoire().getCouleur() != tableDuJeu[i][j].getCouleur() ) {
							j++;
				
						} else if (j + 1 <= Plateau.xMaxDuTapis && tableDuJeu[i][j + 1] != null && Partie.joueursEnJeu[k].getCarteVictoire().getCouleur() == tableDuJeu[i][j + 1].getCouleur()) {
							compteur++;
							j++;
							
						} else j++;
						
					} else j++;
				}
				
				if (compteur > 2) {
					compteur++;
					this.pointsJoueurs[k] += compteur;
				}
			}
		
	
			
		/******************************************************************************************************************************************/
			
		/******************************************************************************************************************************************/
			
			//2.
			//on compte les points obtenus du joueur k sur les formes
			for (int j = Plateau.xMinDuTapis; j <= Plateau.xMaxDuTapis; j++) { //j parcours les colonnes
				int i = Plateau.yMinDuTapis;
				
				while (i <= Plateau.yMaxDuTapis) {//i parcours les lignes
					if (tableDuJeu[i][j] != null) {
						if (Partie.joueursEnJeu[k].getCarteVictoire().getForme() != tableDuJeu[i][j].getForme() ) {
							i++;
					
						} else if (i + 1 <= Plateau.yMaxDuTapis && tableDuJeu[i + 1][j] != null && Partie.joueursEnJeu[k].getCarteVictoire().getForme() == tableDuJeu[i+1][j].getForme()) {
							this.pointsJoueurs[k]++;
							i++;
							
						} else i++;
						
					} else i++;
				}
				
			}
		
		/******************************************************************************************************************************************/
	
			//on compte les points obtenus du joueur k sur les natures
			for (int j = Plateau.xMinDuTapis; j <= Plateau.xMaxDuTapis; j++) { //j parcours les colonnes
				int i = Plateau.yMinDuTapis;
				int compteur = 1;
				
				while (i <= Plateau.yMaxDuTapis) {//i parcours les lignes
					if (tableDuJeu[i][j] != null) {
						if (Partie.joueursEnJeu[k].getCarteVictoire().getNature() != tableDuJeu[i][j].getNature() ) {
							i++;
					
						} else if (i + 1 <= Plateau.yMaxDuTapis && tableDuJeu[i + 1][j] != null && Partie.joueursEnJeu[k].getCarteVictoire().getNature() == tableDuJeu[i+1][j].getNature()) {
							compteur++;
							i++;
							
						} else i++;
						
					} else i++;
				}
				
				if (compteur > 2) {
					this.pointsJoueurs[k] += compteur;
				}
			}
		
		/******************************************************************************************************************************************/
			
			//on compte les points obtenus du joueur k sur les couleurs
			for (int j = Plateau.xMinDuTapis; j <= Plateau.xMaxDuTapis; j++) { //j parcours les colonnes
				int i = Plateau.yMinDuTapis;
				int compteur = 1;
				
				while (i <= Plateau.yMaxDuTapis) {//i parcours les lignes
					if (tableDuJeu[i][j] != null) {
						if (Partie.joueursEnJeu[k].getCarteVictoire().getCouleur() != tableDuJeu[i][j].getCouleur() ) {
							i++;
					
						} else if (i + 1 <= Plateau.yMaxDuTapis && tableDuJeu[i + 1][j] != null && Partie.joueursEnJeu[k].getCarteVictoire().getCouleur() == tableDuJeu[i+1][j].getCouleur()) {
							compteur++;
							i++;
							
						} else i++;
						
					} else i++;
				}
				
				if (compteur > 2) {
					compteur++;
					this.pointsJoueurs[k] += compteur;
				}
			}
		}
	}//end of constructor
	
	
	/*
	 * Get scores of players
	 */
	public int[] getPointsJoueurs() {
		return this.pointsJoueurs;
	}
	
	/*
	 * Get score of a player
	 */
	public int getPointsJoueurs(int joueur_id) {
		return this.pointsJoueurs[joueur_id - 1];
	}
}
			

