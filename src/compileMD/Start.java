package compileMD;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;
import gestionFichier.WorkFolder;



/**
 * Classe de lancement du projet
 * @author nicolas
 */
public class Start {

	/**
	 * M�thode de d�marage de l'application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner keyb = new Scanner(System.in);
		System.out.println("Chemin du r�pertoire ?");
		
		String chemin = keyb.nextLine();
		keyb.close();
		
		
		System.out.println("Traitement du r�pertoire : '" + chemin + "'");
		
		try {
			
			WorkFolder.initialise(chemin);
			System.out.println("Nom du r�pertoire : '" + WorkFolder.getFolder().getNomDuProjetDefaut() + "'");
			
			WorkFolder repertoireDeTravail = WorkFolder.getFolder();
			
			// D�claration du dossier old
			Path oldRepertoire = repertoireDeTravail.getFolder().getRepertoireOld();
			int nbFichiersOld = 0;
			int newNbFichiersOld = 0;			
			
			// Compte le nombre de fichiers dans le dossier old au d�marrage de l'application			
			if (oldRepertoire != null) {
				File[] f = oldRepertoire.toFile().listFiles();
				for (int i = 0; i < f.length; i++) {
					if (f[i].isFile()) {
						nbFichiersOld++;
					}
				}
			}			
			// Affiche le nombre de fichiers dans le dossier old au d�marrage de l'application			
			System.out.println("Le dossier \"Old\" contient d�j� " + nbFichiersOld + " fichier(s)");
			
			repertoireDeTravail.getFichiers().forEach(x -> x.action());
			
			// Compte � nouveau le nombre de fichiers dans le r�pertoire old
			if (oldRepertoire != null) {
				File[] f = oldRepertoire.toFile().listFiles();
				for (int i = 0; i < f.length; i++) {
					if (f[i].isFile()) {
						newNbFichiersOld++;
					}
				}
			}
			// Affiche le nombre de fichiers d�plac�s dans le dossier old
			int nbFichiersDeplace = newNbFichiersOld - nbFichiersOld;
			System.out.println(nbFichiersDeplace + " fichier(s) ont �t� d�plac�s dans le dossier \"Old\".");
			if (nbFichiersDeplace > 0) {
				System.out.println("Le dossier \"Old\" contient maintenant " + newNbFichiersOld + " fichier(s)");
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
