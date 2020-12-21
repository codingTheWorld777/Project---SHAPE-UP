package Modele;

import java.util.ArrayList;

import Modele.Carte.couleurPossible;
import Modele.Carte.formePossible;
import Modele.Carte.naturePossible;

public class PiocheCartes {
	/**
	 * @author Huu Khai NGUYEN (Alec), Pierre-Louis DAMBRAINE
	 */
//	public static void main(String args[]) {
//		new PiocheCartes();
//		for (int i = 0; i < cartesOrg.length; i++) {
//			System.out.println(i + 1 + ") "
//					+ piocheCartes.get(i).getForme() + " "
//					+ piocheCartes.get(i).getNature() + " "
//					+ piocheCartes.get(i).getCouleur() + " " + piocheCartes.get(i).getCarteID());
//		}
//	}
	
	private static ArrayList<Carte> piocheCartes = new ArrayList<Carte>();
	private static Carte[] cartesOrg = new Carte[18];
	
	public PiocheCartes() {
		this.creerCartes();
	}
	
	/*
	 * Create an array that contains all of the card
	 */
	public void creerCartes() {
		
		naturePossible[] natures = {naturePossible.plein, naturePossible.vide};
		formePossible[] formes = {formePossible.cercle, formePossible.carre, formePossible.triangle};
		couleurPossible[] couleurs = {couleurPossible.bleu, couleurPossible.vert, couleurPossible.rouge};
		
		int compteur = 0;
		int ind;
		
		/*
		 * Create and shuffle card
		 */
		for (int i = 0; i < formes.length; i++) {
			for (int j = 0; j < natures.length; j++) {
				for (int k = 0; k < couleurs.length; k++) {
					Carte.setNombreDeCarte(compteur + 1);
					Carte carte = new Carte(formes[i], natures[j], couleurs[k]);  //need to add BufferedImage carteImage
					carte.setCarteID(compteur + 1);
					
					//Shuffle cards
					while (compteur < cartesOrg.length) {
						ind = (int) (Math.random() * cartesOrg.length);
						
						if (cartesOrg[ind] == null) {
							cartesOrg[ind] = carte;
							compteur++;
							break;
						} else continue;
					}
				
				}
			}
		}
		
		for (int i = 0; i < cartesOrg.length; i++) {
			piocheCartes.add(cartesOrg[i]);
		}
		
	}
	
	/*
	 * Get the set of cards that we have just created
	 */
	public static ArrayList<Carte> getPiocheCartes() {
		return PiocheCartes.piocheCartes;
	}
}
