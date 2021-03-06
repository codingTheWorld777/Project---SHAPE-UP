package Controleur;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import Modele.Carte;
import Modele.Compteur;
import Modele.Coordonnees;
import Modele.InstallerJeu;
import Modele.InstallerTour;
import Modele.Joueur;
import Modele.JoueurVir;
import Modele.Partie;
import Modele.PiocheCartes;
import Modele.Plateau;
import Vue.ButtonCard;
import Vue.FenetreTableDuJeu;
import Vue.VueText;

/**
 * @author Huu Khai NGUYEN (Alec), Pierre-Louis DAMBRAINE
 * <br>
 * Description: This class allows to control the game (includes GUI and datas)
 */

public class ControleurTableDuJeu {

	private static FenetreTableDuJeu tableDuJeu;
	
	protected static InstallerJeu installerJeu;
	protected static InstallerTour installerTour;
	
	private static ButtonCard[][] cartesBtn;
	private int x, y;
	
	protected static boolean pouvoirPiocher = true;
	protected static boolean permettreDeDeplacer;
	
	private static Color joueurBackg = new Color(107, 142, 35);
	private LineBorder lineBorder = new LineBorder(SystemColor.activeCaptionText, 1);
	
	/**
	 * Constructor
	 * @param joueur Player
	 * @param btnCarte Card button
	 */
	public void controleurTableDuJeu(Joueur joueur, ButtonCard btnCarte) {
		btnCarte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Coordonnees coord;
				
				if (!Plateau.isInPossibilites(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y))
					ControleurTableDuJeu.setBorderColorToOrg1();
				
				/*
				 * Click the button in 'piocheCarte' zone to draw a card
				 */
				try {
					if (FenetreTableDuJeu.carteJouee.getCarteTiree() && pouvoirPiocher && !joueur.getNom().equals("Joueur Virtuel")) {
						if (Partie.tour == 0 && joueur.getEnTour() == true) {
							coord = btnCarte.getCoordonnees();
							
							if (InstallerJeu.getVarianteDuTapis().equals("P")) {
								if (coord.x == 0 || coord.x == 6) {
									joueur.setCoordAPlacer(coord.x, coord.y);
									
									Image imgRecto = ControleurTableDuJeu.getCartePiochee().getCarteImageRecto();
									imgRecto = imgRecto.getScaledInstance(btnCarte.getWidth(), btnCarte.getHeight(), Image.SCALE_DEFAULT);
									btnCarte.setIcon(new ImageIcon(imgRecto));
								} else {
									for (int y = 0; y < 7; y++) {
										cartesBtn[y][0].setBorder(BorderFactory.createLineBorder(Color.green));
										cartesBtn[y][6].setBorder(BorderFactory.createLineBorder(Color.green));
									}
								}
								
							} else {
								joueur.setCoordAPlacer(coord.x, coord.y);
								
								Image imgRecto = ControleurTableDuJeu.getCartePiochee().getCarteImageRecto();
								imgRecto = imgRecto.getScaledInstance(btnCarte.getWidth(), btnCarte.getHeight(), Image.SCALE_DEFAULT);
								btnCarte.setIcon(new ImageIcon(imgRecto));							
							}
							
							FenetreTableDuJeu.carteJouee.setCarteTiree(false);
							ControleurTableDuJeu.getCartePiochee().estSurTableDuJeu = true;
							pouvoirPiocher = false;
							
						} else if (joueur.getEnTour() == true) {
							coord = btnCarte.getCoordonnees();

							if (Plateau.isInPossibilites(coord.x, coord.y)) {
								joueur.setCoordAPlacer(coord.x, coord.y);
								
								Image imgRecto = ControleurTableDuJeu.getCartePiochee().getCarteImageRecto();
								imgRecto = imgRecto.getScaledInstance(btnCarte.getWidth(), btnCarte.getHeight(), Image.SCALE_DEFAULT);
								btnCarte.setIcon(new ImageIcon(imgRecto));
								
								FenetreTableDuJeu.carteJouee.setCarteTiree(false);
								ControleurTableDuJeu.getCartePiochee().estSurTableDuJeu = true;
								pouvoirPiocher = false;
								
							} else System.out.println("réessayez");						
						}

					}
					
				} catch (Exception err) {
					System.out.println(err.toString());
				}
				
				
				/*
				 * Click a card on game's table, we have 2 situations here: 
				 * 1) 
				 	* If the chosen card is moveable: Change border's color of all ButtonCard on game's table that the chosen card 
				 	can move to these ButtonCard to green
				 	* Else: Do nothing
				 */
				try {
					if (Plateau.isInCartesJouees(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y) 
							&& (Plateau.nePasPouvoirDeplacer() == false) && !joueur.getNom().equals("Joueur Virtuel")) {
						ControleurTableDuJeu.setBorderColorToOrg();

						if (Plateau.estDeplacable(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y)) {
							joueur.setCoordChoisieADeplacer(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y);
							
							for (int i = 0; i < Plateau.getPositionDeDeplacer().size(); i++) {
								x = Plateau.getPositionDeDeplacer().get(i).x;
								y = Plateau.getPositionDeDeplacer().get(i).y;
								cartesBtn[y][x].setBorder(BorderFactory.createLineBorder(Color.green));
							}
						}
				
						return;
					} 
						 

					if (!Plateau.isInPositionDeDeplacer(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y)) {
						if (Plateau.isInCartesJouees(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y) 
								&& !Plateau.estDeplacable(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y)) {
							ControleurTableDuJeu.setBorderColorToOrg();
						} else if (!Plateau.isInCartesJouees(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y))
							ControleurTableDuJeu.setBorderColorToOrg();
					}
					
				} catch (Exception err) {
					System.out.println(err.toString());
				}
					
				
				/**
				 * 2) After choosing a card on game's table that is moveable:
				 		* Click 'ButtonCard' on game's table to move this card to this position 
				 */
				try {
					if (!joueur.getNom().equals("Joueur Virtuel") && joueur.getCoordChoisieADeplacer() != null 
						&& Plateau.isInPositionDeDeplacer(btnCarte.getCoordonnees().x, btnCarte.getCoordonnees().y)) {
						
						int x1 = btnCarte.getCoordonnees().x;
						int y1 = btnCarte.getCoordonnees().y;
						
						if (Plateau.isInPositionDeDeplacer(x1, y1)) {
							joueur.setCoordADeplacer(x1, y1);
							
							int x = joueur.getCoordChoisieADeplacer().x;
							int y = joueur.getCoordChoisieADeplacer().y;
							
							for (int i = 0; i < Plateau.getListeDeCartesJouees().size(); i++) {
								if (permettreDeDeplacer && Plateau.getListeDeCartesJouees().get(i).getCoordonnees().x == x 
									&& Plateau.getListeDeCartesJouees().get(i).getCoordonnees().y == y) {
									
									Image imgRecto = ImageIO.read(this.getClass().getResource("/images/" + Plateau.getListeDeCartesJouees().get(i).getCarteID() + ".png"));
									imgRecto = imgRecto.getScaledInstance(btnCarte.getWidth(), btnCarte.getHeight(), Image.SCALE_DEFAULT);
									
									cartesBtn[y1][x1].setIcon(new ImageIcon(imgRecto));
									cartesBtn[y1][x1].setBorder(lineBorder);
									cartesBtn[y][x].setIcon(null);
									cartesBtn[y][x].setBorder(lineBorder);
									
									permettreDeDeplacer = false;
									
									ControleurTableDuJeu.setBorderColorToOrg();
									
									/** 
									 * If we play by click on GUI: After updating card's image, we update datas 
									 * in all necessary lists by using method "deplacerCarte" of player
									*/
									joueur.deplacerCarte();
									if (!joueur.aPiocheUneCarte) Plateau.determinerFormeDuTapis(Plateau.getListeDeCartesJouees());
									
									Thread.sleep(180);
									return;
								}

							}
						}	
					}
				} catch (Exception err){
					System.out.println(err.toString());
				}
					
			}
			
		});
	}
	
	/**
	 * Finish a round of player <br>
	 * Activate the next player
	 * @param finirMonTour : Button 'Finish my turn'
	 */
	public static void finirMonTour(JButton finirMonTourBtn, Joueur joueur, int id) {
		
		finirMonTourBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (joueur.getId() == Partie.tourDeJoueur) {
					if (joueur.aPiocheUneCarte == true) {
						joueur.pouvoirFinirMonTour = true;
						permettreDeDeplacer = true;
					} 

					/* 1) Change color of player in turn to green and the others to pink
					 * 2) Count scores of each player after 1 turn and show its in screen
					 */
					Compteur compteurPoint = new Compteur();
					compteurPoint.compter(Partie.getTableDuJeu());
					
					for (int i = 0; i < Partie.joueursEnJeu.length; i++) {
						//1)
						if (Partie.joueursEnJeu[i].getEnTour()) FenetreTableDuJeu.getJoueurPanel(i + 1).setBackground(joueurBackg);
						else FenetreTableDuJeu.getJoueurPanel(i + 1).setBackground(UIManager.getColor("Button.select"));
						
						//2)
						if (i == 0) 
							FenetreTableDuJeu.point1.setText("Point: " + compteurPoint.getPointsJoueurs(Partie.joueursEnJeu[i].getId()));
						
						if (i == 1) 
							FenetreTableDuJeu.point2.setText("Point: " + compteurPoint.getPointsJoueurs(Partie.joueursEnJeu[i].getId()));
					
						if (Partie.joueursEnJeu.length == 3 && i == 2) 
							FenetreTableDuJeu.point3.setText("Point: " + compteurPoint.getPointsJoueurs(Partie.joueursEnJeu[i].getId()));

					}
					
					FenetreTableDuJeu.carteRestantLabel.setText("Carte restant : " + ControleurTableDuJeu.getNombreDeCartesRes());

					pouvoirPiocher = true;		
					joueur.coordChoisieADeplacer = null;
					joueur.coordADeplacer = null;
					
					ControleurTableDuJeu.setBorderColorToOrg();
				}
			}
		});

		
	}
	
	/**
	 * Finish a round of a virtual player
	 * Activate the next player
	 * @param joueur
	 * @param id
	 */
	public static void finirVirtualTour(JoueurVir joueur, int id) {
		if (joueur.getId() == Partie.tourDeJoueur) {
			joueur.pouvoirFinirMonTour = true;
			permettreDeDeplacer = true;
				
			/* *
			 * 1) Change color of player in turn to green and the others to pink
			 * 2) Count scores of each player after 1 turn and show its in screen
			 */
			Compteur compteurPoint = new Compteur();
			compteurPoint.compter(Partie.getTableDuJeu());
			
			for (int i = 0; i < Partie.joueursEnJeu.length; i++) {
				//1)
				if (Partie.joueursEnJeu[i].getEnTour()) FenetreTableDuJeu.getJoueurPanel(i + 1).setBackground(joueurBackg);
				else FenetreTableDuJeu.getJoueurPanel(i + 1).setBackground(UIManager.getColor("Button.select"));
				
				//2)
				if (i == 0) 
					FenetreTableDuJeu.point1.setText("Point: " + compteurPoint.getPointsJoueurs(Partie.joueursEnJeu[i].getId()));
				
				if (i == 1) 
					FenetreTableDuJeu.point2.setText("Point: " + compteurPoint.getPointsJoueurs(Partie.joueursEnJeu[i].getId()));
			
				if (Partie.joueursEnJeu.length == 3 && i == 2) 
					FenetreTableDuJeu.point3.setText("Point: " + compteurPoint.getPointsJoueurs(Partie.joueursEnJeu[i].getId()));

			}

			FenetreTableDuJeu.carteRestantLabel.setText("Carte restant : " + ControleurTableDuJeu.getNombreDeCartesRes());
			
			pouvoirPiocher = true;		
			joueur.coordChoisieADeplacer = null;
			joueur.coordADeplacer = null;
				
			ControleurTableDuJeu.setBorderColorToOrg();
		}
	}
	
	/**
	 * Pass to next round after finishing a round
	 * @param tourSuivantBtn
	 */
	public void tourSuivant(JButton tourSuivantBtn) {
		tourSuivantBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Partie.nombreDeCartesJouables == 0 && FenetreTableDuJeu.round < 8) {
					FenetreTableDuJeu.round++;
					
					System.out.println("ROUND " + FenetreTableDuJeu.round);
					/** Pass to next round: (Release memory + InstallerTour + new Partie() + Repaint GUI */
					
					/** Thread for game in CMD. Thread's name: Thread-index */
					Thread threadCMD = new Thread() {
						public void run() {
							System.out.println("Start " + Thread.currentThread().getName());
							new Partie();
						}
					};
					threadCMD.start();
					
					
					/**
					 * Thread for GUI
					 * Run this thread of "FenetreTableDuJeu" after 2 seconds to wait for "InstallerTour" is finish
					 * Thread's name: AWT-EventQueue-0
					 */
					while (Partie.joueur2 == null) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					
					try {
						Thread.sleep(1400);
					} catch (InterruptedException er) {
						System.out.println(er.toString());
					}
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							while (Partie.joueursEnJeu[0] == null) {
								try {
									Thread.sleep(1800);
								} catch (Exception err) {
									System.out.println(err.toString());
								}
							}
							
							try {			
								ControleurTableDuJeu.tableDuJeu.dispose();
								FenetreTableDuJeu tableDuJeu = new FenetreTableDuJeu();
								ControleurTableDuJeu.setFenetreTableDuJeu(tableDuJeu);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					
					/** Thread of VueText */
					if (InstallerJeu.getConsoleOption()) {
						try {
							Thread.sleep(700);
							new VueText();
						} catch (NullPointerException | InterruptedException err) {
							System.out.println(err.toString());
						}
					}
				}
			}
			
		});
	}
	
	/**
	 * Check if a coordinate of a card is in list of possiblities
	 * @param tabBtnCarte
	 * @return
	 */
	public boolean checkPossibilites(ButtonCard[][] tabBtnCarte) {
		boolean isInPossibilites = true;
		for (int i = 0; i < tabBtnCarte.length; i++) {
			for (int j = 0; j < tabBtnCarte[i].length; j++) {
				if (!Plateau.isInPossibilites(tabBtnCarte[i][j].getCoordonnees().x, tabBtnCarte[i][j].getCoordonnees().y)) {
					isInPossibilites = false;
				}
			}
		}
			
		return isInPossibilites;
	}
	
	
	/**
	 * Change border's color of card to origin for card in list 'positionDeDeplacer'
	 */
	public static void setBorderColorToOrg() {
		if (!Plateau.getPositionDeDeplacer().isEmpty()) {
			LineBorder lineBorder = new LineBorder(SystemColor.activeCaptionText, 1);
			for (int i = 0; i < Plateau.getPositionDeDeplacer().size(); i++) {
				int x0 =  Plateau.getPositionDeDeplacer().get(i).x;
				int y0 =  Plateau.getPositionDeDeplacer().get(i).y;
				cartesBtn[y0][x0].setBorder(lineBorder);
			}
			Plateau.getPositionDeDeplacer().clear();
		}
		
		if (!pouvoirPiocher) ControleurTableDuJeu.setBorderColorToOrg1();
	}
	
	/**
	 * Change border's color of card to origin for card in list 'positionDeDeplacer'
	 */
	public static void setBorderColorToOrg1() {
		LineBorder lineBorder = new LineBorder(SystemColor.activeCaptionText, 1);
		Plateau.determinerFormeDuTapis(Plateau.getListeDeCartesJouees());
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 7; x++)
				cartesBtn[y][x].setBorder(lineBorder);
		}
	}

	/**
	 * Set and get game's table for controler
	 */
	public static void setFenetreTableDuJeu(FenetreTableDuJeu tableDuJeu) {
		ControleurTableDuJeu.tableDuJeu = tableDuJeu;
	}
	
	public static FenetreTableDuJeu getFenetreTableDuJeu() {
		return tableDuJeu;
	}

	/**
	 * Check if the game includes a three player and paint it 
	 * @param installerJeu
	 * @param tableDuJeu
	 * @return
	 */
	public static boolean paintJoueur3(InstallerJeu installerJeu, FenetreTableDuJeu tableDuJeu) {
		if (InstallerJeu.getNombreDeJoueurs() == 3) return true;
		
		return false;
	}
	
	/** Get player */
	public static Joueur getJoueur(int index) {
		return Partie.joueursEnJeu[index];
	}
	
	public void setCartesBtn(ButtonCard[][] cartesBtn) {
		ControleurTableDuJeu.cartesBtn = cartesBtn;
	}
	
	/**
	 * Get hidden card from "Partie"
	 */
	public Carte getCarteCachee() {
		return Partie.carteCachee;
	}
	
	/**
	 * Get the drawn card from "PiocheCartes"
	 */
	public static Carte getCartePiochee() {
		return PiocheCartes.getPiocheCartes().get(PiocheCartes.getPiocheCartes().size() - 1);
	}
	
	/**
	 * Get the number of playable cards
	 */
	public int getNombreCarteJouable() {
		return PiocheCartes.getPiocheCartes().size();
	}
	
	/**
	 * Set and get installerJeu variable from class Partie in package.Modele when it is created
	 */
	public static void setInstallerJeu(InstallerJeu installerJeu) {
		ControleurTableDuJeu.installerJeu = installerJeu;
	}
	
	public static InstallerJeu getInstallerJeu() {
		return ControleurTableDuJeu.installerJeu;
	}
	
	/**
	 * Set and get installerTour variable from class Partie in package,Modele when it is created
	 */
	public static void setInstallerTour(InstallerTour installerTour) {
		ControleurTableDuJeu.installerTour = installerTour;
	}
	
	public static InstallerTour getInstallerTour() {
		return ControleurTableDuJeu.installerTour;
	}
	
	/** Get and set permettreDeDeplacer */
	public static boolean getPermettreDeDeplacer() {
		return ControleurTableDuJeu.permettreDeDeplacer;
	}
	
	public static void setPermettreDeDeplacer(boolean bool) {
		ControleurTableDuJeu.permettreDeDeplacer = bool;
	}
	
	/**
	 * Set pouvoirPiocher
	 * @param bool : boolean
	 */
	public static void setPouvoirPiocher(boolean bool) {
		ControleurTableDuJeu.pouvoirPiocher = bool;
	}
	
	/**
	 * Get the color joueur Backg
	 * @return Color of player's background
	 */
	public static Color getJoueurBackg() {
		return ControleurTableDuJeu.joueurBackg;
	}
	
	/**
	 * Check if a card is moveable
	 * @param x : int
	 * @param y : int
	 * @return boolean
	 */
	public static boolean estDeplacable(int x, int y) {
		return Plateau.estDeplacable(x, y);
	}
	
	/**
	 * Check if a new position chosen by player to move a card to is moveable
	 * @param x : int
	 * @param y : int
	 * @return boolean
	 */
	public static boolean isInPositionDeDeplacer(int x, int y) {
		return Plateau.isInPositionDeDeplacer(x, y);
	}
	
	public static int getNombreDeCartesRes() {
		return PiocheCartes.getPiocheCartes().size();
	}
}