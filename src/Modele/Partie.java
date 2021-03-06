package Modele;

import Controleur.ControleurParametre;
import Controleur.ControleurTableDuJeu;

/**
 * @author Huu Khai NGUYEN (Alec), Pierre-Louis DAMBRAINE
 * <br>
 * Description: A place where the game occurs
 */

public class Partie extends Observable {
		
	/**
	 * Player of game (from 2-3 players)
	 */
	public static Joueur joueur1;
	public static Joueur joueur2;
	public static Joueur joueur3;
	
	/** Sets of players */ 
	public static Joueur[] joueursEnJeu;
	
	/** Hidden card */
	public static Carte carteCachee;
	
	/**
	 * Number of playable cards 
	 */
	public static int nombreDeCartesJouables;
	
	/** Turn */
	public static int tour = 0;
	public static int tourDeJoueur = 1;
	
	/**
	 * Board of game
	 */
	private static Carte[][] tableDuJeu = new Carte[5][7];
	
	/** Constructor */
	public Partie() {
		
		/**
		 * Step 1:
		 * Install game: choose number of players, activer virtual player(Yes or No) and choose its level
		 */
		InstallerJeu installerJeu = new InstallerJeu();
		ControleurParametre.setInstallerJeu(installerJeu);
		ControleurTableDuJeu.setInstallerJeu(installerJeu);
		
		
		/**
		 * Step 2:
		 * Install a round:
		 * 	+ Eliminate a card
		 * 	+ Draw victory card to each player
		 */
		InstallerTour installerTour = new InstallerTour(InstallerJeu.getNombreDeJoueurs(), InstallerJeu.getActiverJoueurVir());
		ControleurTableDuJeu.setInstallerTour(installerTour);
		
		
		/**
		 * Step 3:
		 * All player in turn of game and play their turn:
		 * + Move a card	[OK]
		 * + Draw and put that card in a possible position 	[OK]
		 */
		joueursEnJeu[0].setEnTour(true);  //Choosing Player 1 for the first turn
		while (Partie.nombreDeCartesJouables > 0) {    
			for (int i = 0; i < InstallerJeu.getNombreDeJoueurs() && Partie.nombreDeCartesJouables > 0; i++) {
				for (int k = 0; k < InstallerJeu.getNombreDeJoueurs(); k++) {
					if (Partie.joueursEnJeu[k].getEnTour() == true) {
						Partie.tourDeJoueur = Partie.joueursEnJeu[k].getId();
						break;
					}
				}
				
				System.out.println(joueursEnJeu[i].getNom());
				if (Partie.tour == 0) {
					Partie.jouerSonTour(joueursEnJeu[i], joueursEnJeu[i].estEnTour, Partie.tour);		//draw and play a card
					Partie.tour++;
					
					while (joueursEnJeu[i].pouvoirFinirMonTour == false) {
						try {
							Thread.sleep(1400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 
					
					joueursEnJeu[i].aPiocheUneCarte = false;
					joueursEnJeu[i].pouvoirFinirMonTour = false;
					
				} else if (Partie.tour >= 1) {
					Partie.jouerSonTour(joueursEnJeu[i], joueursEnJeu[i].estEnTour, Partie.tour);		//draw and play a card
					Partie.tour++;
					
					while (joueursEnJeu[i].pouvoirFinirMonTour == false) {
						try {
							Thread.sleep(1400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 
					
					joueursEnJeu[i].aPiocheUneCarte = false;
					joueursEnJeu[i].pouvoirFinirMonTour = false;
					
				}
				Plateau.determinerFormeDuTapis(Plateau.cartesJouees);
				System.out.println("Carte restant: " + Partie.nombreDeCartesJouables + "\n");
			}
		}
		
		
		/** 
		 * Step 4: The game is now finish: 
		 	* Compter points of each player and print it on the screen
		 */
		System.out.println("\n \n **************************** RESULTAT FINAL ****************************");
		this.imprimerResult();
		
		Compteur compteurPoint = new Compteur();
		compteurPoint.compter(tableDuJeu);
		
		for (int i = 0; i < Partie.joueursEnJeu.length; i++) {
			System.out.print(Partie.joueursEnJeu[i].nom 
					+ ": " + compteurPoint.getPointsJoueurs()[i] + " points." + "\t");
		}
		System.out.println();
		
		
		/**
		 * Step 5: Release memory for next round
		 */
		PiocheCartes.getPiocheCartes().clear();
		Partie.tour = 0;
		Partie.tableDuJeu = new Carte[5][7];
		Plateau.getListeDeCartesJouees().clear();
		Plateau.getPossibilites().clear();
	}
	
	/**
	 * Player play their turn:
	 * <ul>
	 * 		<li>Move a card</li>
	 * 		<li>Draw ad put that card in a possible position</li>
	 * </ul>
	 * @param joueur Joueur
	 * @param estEnTour boolean
	 * @param tour int
	 */
	public static void jouerSonTour(Joueur joueur, boolean estEnTour, int tour) {
		if (estEnTour) {
			if (InstallerJeu.getNombreDeJoueurs() == 2) {
				if (joueur.id == 1) {
					joueur.piocherCarte(Partie.tableDuJeu, tour);
					
					joueur.setEnTour(false);
					joueursEnJeu[joueur.id].setEnTour(true);
				} else if (joueur.id == 2) {
					if (joueur.getNom().equals("Joueur Virtuel") && tour >= 3) {
						joueur.deplacerCarte();
					}
					joueur.piocherCarte(Partie.tableDuJeu, tour);
					
					joueur.setEnTour(false);
					joueursEnJeu[0].setEnTour(true);
					
					if (joueur.getNom().equals("Joueur Virtuel")) {
						ControleurTableDuJeu.finirVirtualTour((JoueurVir) joueur, joueur.getId());;
					}
				}

			} else if (InstallerJeu.getNombreDeJoueurs() == 3) {
				if (joueur.id == 1) {
					joueur.piocherCarte(Partie.tableDuJeu, tour);
					
					joueur.setEnTour(false);
					joueursEnJeu[1].setEnTour(true);
					
				} else if (joueur.id == 2) {
					joueur.piocherCarte(Partie.tableDuJeu, tour);
					
					joueur.setEnTour(false);
					joueursEnJeu[2].setEnTour(true);
					
				} else if (joueur.id == 3) {
					if (joueur.getNom().equals("Joueur Virtuel") && tour >= 3) {
						joueur.deplacerCarte();
					}
					joueur.piocherCarte(Partie.tableDuJeu, tour);
					
					joueur.setEnTour(false);
					joueursEnJeu[0].setEnTour(true);
					
					if (joueur.getNom().equals("Joueur Virtuel")) {
						ControleurTableDuJeu.finirVirtualTour((JoueurVir) joueur, joueur.getId());;
					}
				}
			}
		}
	}
	
	/**
	 * Get table of game "tableDuJeu"
	 * @return tableDuJeu : Carte[][]
	 */
	public static Carte[][] getTableDuJeu() {
		return Partie.tableDuJeu;
	}
	
	/**
	 * Show the table of game with all features of cards
	 */
	public void imprimerResult() {
		for (int i = 0; i < Partie.joueursEnJeu.length; i++) {
			System.out.println("Carte victoire de " + joueursEnJeu[i].nom + " est: " 
					+ " " + joueursEnJeu[i].getCarteVictoire().getForme()
					+ " " + joueursEnJeu[i].getCarteVictoire().getCouleur()
					+ " " + joueursEnJeu[i].getCarteVictoire().getNature());
		}
		System.out.println();
		
		String result;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if (Partie.getTableDuJeu()[i][j] != null) {
					result = "|" + Partie.getTableDuJeu()[i][j].getForme() + " "
							+ Partie.getTableDuJeu()[i][j].getCouleur() + " "
							+ Partie.getTableDuJeu()[i][j].getNature();
			
					while (result.length() < 22) {
						if (result.length() == 21) result += "|";
						else result += " ";;
					}
					
					System.out.print(result);
				} else if (Partie.getTableDuJeu()[i][j] == null) {
					result = "| ";
					
					while (result.length() < 22) {
						if (result.length() == 21) result += "|";
						else result += " ";;
					}
					
					System.out.print(result);
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
