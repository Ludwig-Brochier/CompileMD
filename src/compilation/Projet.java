/**
 * Classe m�tier permettant de compiler un fichier compilable
 */
package compilation;

import java.util.ArrayList;

import gestionFichier.BaseFile;
import texteFichier.ICompilableFile;

/**
 * Repr�sente un projet
 * Classe finale, non-h�ritable
 * @author Ludwig 
 */
public final class Projet {
	
	/**
	 * Liste statique des diff�rents projets
	 */
	private static ArrayList<Projet> projets;
		
	
	/**
	 * Nom du projet
	 */
	private String nom;
	
	/**
	 * Liste des fichiers composant le projet
	 */
	private ArrayList<ICompilableFile> fichiers;
	
	
	/**
	 * Constructeur de la classe Projet
	 * @param nomProjet
	 */
	private Projet(String nomProjet) {
		nom = nomProjet;
		fichiers = new ArrayList<ICompilableFile>();
	}
	
	/**
	 * M�thode qui permet d'ajouter un fichier � la compilation
	 */
	public static void ajouterACompilation(BaseFile fichierX) {
		
		// Initialise la liste des projets si n'�xiste pas encore
		if (Projet.projets == null) {
			Projet.projets = new ArrayList<Projet>();
		}
		
		// Test si le fichierX en argument de la m�thode impl�mente l'interface ICompilable
		if (fichierX instanceof ICompilableFile) {
			
			ICompilableFile fichierAAjouter = (ICompilableFile)fichierX; // fichierX est compilable
						
			Projet projetDuFichier = Projet.projets.stream() // Passe la liste des projets en Stream
					.filter(p -> p.nom.equals(fichierAAjouter.getNomDuProjet())) // Boucle le stream et cherche le nom du projet 
					.findFirst() // Retourne le premier projet correspondant
					.orElse(null); // Retourne null si ne trouve pas le projet dans la liste
			
			// Test si le projet �xiste
			if (projetDuFichier == null) {
				projetDuFichier = new Projet(fichierAAjouter.getNomDuProjet()); // Initialise un nouveau projet
				Projet.projets.add(projetDuFichier); // Ajoute le projet � la liste des projets
				System.out.println("--> Cr�ation du projet : " + projetDuFichier.nom);
			}
			
			// Ajoute le fichierX � la liste des fichiers du projet
			projetDuFichier.fichiers.add(fichierAAjouter);
			
			System.out.println("----> Un fichier a �t� ajout� au projet : " + projetDuFichier.nom +
					" Nombre total de fichiers du projet : " + projetDuFichier.fichiers.size());
		}
	}
}



