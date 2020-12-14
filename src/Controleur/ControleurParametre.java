package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import Modele.InstallerJeu;
import Modele.Partie;

public class ControleurParametre {
	private static InstallerJeu installerJeu;
	
	private static JFrame fenetreParametreFrame;
	
	/**
	 * Render variation/numberOfPlayer option
	 * @param installerJeu
	 * @param comboBox
	 */
	public void controleurParametre(InstallerJeu installerJeu, JComboBox comboBox) {
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = (String) comboBox.getSelectedItem();
				
				try {
					InstallerJeu.setNombreDeJoueurs(Integer.parseInt(str));
					
					
				} catch (NumberFormatException err) {
					if (str.equals("classique")) InstallerJeu.setVarianteDuTapis("R");
					else if (str.equals("caree")) InstallerJeu.setVarianteDuTapis("C");
					else if (str.equals("pyramide")) InstallerJeu.setVarianteDuTapis("P");
				}
			}
		});
	}
	
	/**
	 * overloading 
	 * Render activerJoueurVir / Niveau option
	 * @param installerJeu
	 * @param radioBtn
	 */
	public void controleurParametre(InstallerJeu installerJeu, JRadioButton radioBtn) {
		radioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton selectedValue = (AbstractButton) e.getSource();
				
				try {
					//In case activerJoueurVirRadioBtn is selected
					if (selectedValue.getText().equals("Oui")) {
						InstallerJeu.setActiverJoueurVir(true);
						InstallerJeu.activerJoueurVirValidation = 1;

					} else if ((selectedValue.getText().equals("Non"))) {
						InstallerJeu.setActiverJoueurVir(false);
						InstallerJeu.activerJoueurVirValidation = 1;
					}
						
					//In case niveauRaidoBtn is selected
					if (selectedValue.getText().equals("Facile")) {
						InstallerJeu.setNiveau("F");
						InstallerJeu.niveauValidation = 1;
					}
					else if (selectedValue.getText().equals("Difficile")) {
						InstallerJeu.setNiveau("D");
						InstallerJeu.niveauValidation = 1;
					}
	
				} catch (Exception err) {
					System.out.println("Some errors happened...");
				}
			}
		});
	}
	
	/**
	 * overloading
	 * Validate options from players -> Close parameted window -> Open SHAPE UP's GUI
	 * @param installerJeu
	 * @param button
	 */
	public void controleurParametre(InstallerJeu installerJeu, JButton button) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControleurParametre.fenetreParametreFrame.dispose();
				new Partie();
			}
		});
	}
	
	
	/*
	 * Set and get installerJeu variable from Class InstallerJeu in package.Modele when it is created
	 */
	public static void setInstallerJeu(InstallerJeu installerJeu) {
		ControleurParametre.installerJeu = installerJeu;
	}
	
	public static InstallerJeu getInstallerJeu() {
		return ControleurParametre.installerJeu;
	}
	
	
	/*
	 * Set and get fenetreParametre's frame for handling event of JButton "valider"
	 */
	public static void setFenetreParametre(JFrame fenetreParametreFrame) {
		ControleurParametre.fenetreParametreFrame = fenetreParametreFrame;
	}
	
	public static JFrame getFenetreParametre() {
		return ControleurParametre.fenetreParametreFrame;
	}
}