package gestionFichier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import texteFichier.IComptableFile;

/**
 * Classe g�rant les fichiers texte simples. Ces fichiers doivent être compt�s, mais pas compil� ni d�plac�.
 * @author Ludwig
 *
 */
public class TextFile extends BaseFile implements IComptableFile {

	/**
	 * Permets d'obtenir toutes les lignes qui sont pr�sentes dans le fichier.
	 */
	private List<String> lignes;
	public List<String> getlignes() {
		return lignes;
	}
	
	/**
	 * Constructeur explicite de la classe.
	 * @param cheminDuFichier
	 */
	protected TextFile(Path cheminDuFichier) {
		super(cheminDuFichier);
		
		try {
			lignes = Files.readAllLines(this.getChemin()); // Lis toutes les lignes du fichier
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Point d'entr�e principal de la classe qui lance les actions devant �tre effectu�es.
	 */
	@Override
	public void action() {
		
		System.out.println("TextFile : " + this.nomDuFichier +
				" NbrMots : " + this.getNbrMots() + 
				" NbrCaracteres : " + this.getNbrCaracteres());
		
	}
	
	/**
	 * Permets d'obtenir le nombre de mots contenus dans le fichier.
	 * @return Le nombre de mots contenus dans le fichier.
	 */
	@Override
	public int getNbrMots() {
		
		// Je r�cup�re toutes les lignes du fichier
		List<String> fichierLignes = getlignes();		
		int nbrMots = 0;		
		
		// Pour chaque ligne
		for (String string : fichierLignes) {
			
			if(string.isEmpty() == false) {
				
				// Je r�cup�re les lemmes de chaque ligne
				String[] lemmes = string.split(" ");
				
				// Pour chaque lemme
				for (int i = 0; i < lemmes.length; i++) {
					
					// Affichage de test des lemmes
					//System.out.println(lemmes[i] + " Ceci est un lemme");	
					
					// Je r�cup�re les caract�res du lemme
					char[] chLemmes = lemmes[i].toCharArray();	
						
					// Pour chaque charact�re
					for (int j = 0; j < chLemmes.length; j++) {
						
						// Test si le charact�re est une lettre ou un chiffre
						if (Character.isAlphabetic(chLemmes[j]) || Character.isDigit(chLemmes[j])) {
							
							// Affichage de test du lemme qui est un mot r�el
							//System.out.println(lemmes[i] + " Un lemme qui compte comme �tant un mot r�el.");
							nbrMots++;
							break; 
						}								
					}										
				}
			}
		}
		
		return nbrMots;
		
	}
	
	/**
	 * Permets d'obtenir le nombre de caract�res contenus dans le fichier.
	 * @return Le nombre de caract�res contenus dans le fichier.
	 */
	@Override
	public int getNbrCaracteres() {
		
		// Je r�cup�re toutes les lignes du fichier
		List<String> fichierLignes = getlignes();		
		int nbrCaracteres = 0;
		
		// Pour chaque ligne
		for (String string : fichierLignes) {
			
			// Longueur de la chaine string
			nbrCaracteres += string.length();
		}
		
		return nbrCaracteres;
		
	}
}
