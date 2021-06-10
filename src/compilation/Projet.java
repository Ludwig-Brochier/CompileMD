/**
 * 
 */
package compilation;

import java.util.ArrayList;

import gestionFichier.BaseFile;
import gestionFichier.WorkFolder;
import texteFichier.ICompilableFile;

/**
 * @author Loud
 * Repr�sente un projet
 * Classe finale, non-h�ritable
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
		
		if (Projet.projets == null) {
			Projet.projets = new ArrayList<Projet>();
		}
		
		if (fichierX instanceof ICompilableFile) {
			
			ICompilableFile fichierAAjouter = (ICompilableFile)fichierX;
			
			Projet projetDuFichier = Projet.projets.stream()
					.filter(p -> p.nom.equals(fichierAAjouter.getNomDuProjet()))
					.findFirst()
					.orElse(null);
			
			if (projetDuFichier == null) {
				projetDuFichier = new Projet(fichierAAjouter.getNomDuProjet());
				Projet.projets.add(projetDuFichier);
				System.out.println("Cr�ation du projet : " + projetDuFichier.nom);
			}
			
			projetDuFichier.fichiers.add(fichierAAjouter);
			
			System.out.println(" Un fichier a �t� ajout� au projet : " + projetDuFichier.nom +
					" Nombre total de fichiers du projet : " + projetDuFichier.fichiers.size());
		}
	}
}



