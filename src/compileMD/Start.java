package compileMD;

import java.util.Scanner;

import compilation.Projet;
import gestionFichier.WorkFolder;



/**
 * Classe de lancement du projet
 * @author Ludwig
 */
public class Start {

	/**
	 * M�thode de d�marage de l'application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		// M�thode Scanner pour r�cup�rer une saisie utilisateur
		Scanner keyb = new Scanner(System.in);
		System.out.println("Chemin du r�pertoire ?");
		
		// Saisi de l'utilisateur du r�pertoire de travail
		String chemin = keyb.nextLine();
		keyb.close(); // Ferme le Scanner
		
		
		System.out.println("Traitement du r�pertoire : '" + chemin + "'");
		
		try {
			
			WorkFolder.initialise(chemin); // Initialise le r�pertoire de travail
			System.out.println("Nom du r�pertoire : '" + WorkFolder.getFolder().getNomDuProjetDefaut() + "'");
			
			WorkFolder repertoireDeTravail = WorkFolder.getFolder(); // R�cup�re le Singleton du r�pertoire de travail
			
			// Etape Triage fichiers
			repertoireDeTravail.getFichiers().forEach(x -> x.action()); // Appel de la m�thode action pour chaque fichier pr�sent dans le r�pertoire de travail			
			// Affiche le nombre de fichier d�plac� dans le r�pertoire old
			System.out.println("--> " + WorkFolder.getFolder().getNbFichierDeplace() + " fichier(s) ont �t� d�plac�.");
			
			// Etape Compilation
			repertoireDeTravail.getFichiers().forEach(x -> Projet.ajouterACompilation(x)); // Ajoute � la compilation tout fichiers compilable pr�sent dans le r�pertoire de travail			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
