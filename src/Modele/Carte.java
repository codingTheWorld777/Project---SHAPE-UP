package Modele;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Carte {
	/**
	 * @author Huu Khai NGUYEN (Alec)
	 */
	
	/**
	 * There are 3 possible shapes for a card: circle, triangle, square.
	 */
	protected enum formePossible {cercle, carre, triangle};
	private formePossible forme;
	
	/**
	 * There are only 2 main states of card: hollow and solid
	 */
	protected enum naturePossible {plein, vide};
	private naturePossible nature;
	
	/**
	 * There are 3 possible colors for a card: red, blue or green
	 */
	protected enum couleurPossible {bleu, vert, rouge};
	private couleurPossible couleur;
	
	/**
	 * Face of card: UP or DOWN
	 */
	private boolean recto;
	
	/**
	 * This is card's image in the game.
	 * Each card has a different image
	 * Each card can be up or down (recto vs verso)
	 * Each card has a index in order to get image
	 * Each card has an ID which is matched with its image's name
	 */
	private BufferedImage carteImageRecto;
	private BufferedImage carteImageVerso;
	private static int nombreDeCarte;
	private int ID; 
	
	/**
	 * coordinates of card (of card played) 
	 */
	private Coordonnees coordonnesDeCarte;
	
	/**
	 * Function of variable "estSurTableDuJeu"
	 * 	if 'estSurTableDuJeu == true": Card is now on game's table (rectangle 5x7)
	 * 	else: Card is not on game's table (rectangle 5x7)
	 */
	public boolean estSurTableDuJeu;

	/**
	 * Constructor
	 * @param forme
	 * @param nature
	 * @param couleur
	 */
	public Carte(formePossible forme, naturePossible nature, couleurPossible couleur) { //+ BufferedImage carteImage
		this.forme = forme;
		this.nature = nature;
		this.couleur = couleur;

		this.toVerso();

		try {
			//Load the image for the current file to render "face up" of card
			URL url = getClass().getResource("../images/" + Carte.nombreDeCarte + ".png");
			this.carteImageRecto = ImageIO.read(url);
			
			//Load the backup of card
			url = getClass().getResource("../images/dos.png");
			this.carteImageVerso = ImageIO.read(url);
			
//			this.setBounds(0, 0, 100, 50);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get card's form
	 */
	public formePossible getForme() {
		return this.forme;
	}
	
	/**
	 * Set card's form
	 */
	public void setForme(formePossible forme) {
		this.forme = forme;
	}
	
	/**
	 * get card's color
	 */
	public couleurPossible getCouleur() {
		return this.couleur;
	}
	
	/**
	 * Set card's color
	 */
	public void setCouleur(couleurPossible couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * get card's nature
	 */
	public naturePossible getNature() {
		return this.nature;
	}
	
	/**
	 * Set card's nature
	 */
	public void setNature(naturePossible nature) {
		this.nature = nature;
	}
	
	/**
	 * Change the face of card
	 */
	public void toRecto() {
		this.recto = true;
	}
	
	public void toVerso() {
		this.recto = false;
	}
	
	public boolean getFace() {
		return this.recto;
	}
	
	/**
	 * Set image to card: Face up OR Face down
	 */
	public void setCarteImage(boolean recto) {}
	
	/*
	 * Get card's image (faceUp-recto or faceDown-verso)
	 */
	public Image getCarteImageRecto() {
		return this.carteImageRecto;
	}
	
	public Image getCarteImageVerso() {
		return this.carteImageVerso;
	}
	
	/**
	 * Set number for card
	 */
	public static void setNombreDeCarte(int nombreDeCarte) {
		Carte.nombreDeCarte = nombreDeCarte;
	}
	
	/**
	 * Get number of card to render image 
	 */
	public static int getNombreDeCarte() {
		return Carte.nombreDeCarte;
	}
	
	/**
	 * Set and Get ID of card
	 */
	public int getCarteID() {
		return this.ID;
	}
	
	public void setCarteID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Set coordinates for card
	 */
	public void setCoordonnees(int x, int y) {
		this.coordonnesDeCarte = new Coordonnees(x, y);
	}
	
	/**
	 * Get coordinates for card
	 */
	public Coordonnees getCoordonnees() {
		return this.coordonnesDeCarte;
	}
}
