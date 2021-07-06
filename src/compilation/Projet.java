/**
 * Classe m�tier permettant de compiler un fichier compilable
 */
package compilation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import gestionFichier.BaseFile;
import gestionFichier.TextFile;
import gestionFichier.WorkFolder;
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
	public static ArrayList<Projet> getProjets(){
		return projets;
	}
		
	
	/**
	 * Nom du projet
	 */
	private String nom;
	public String getNom() {
		return nom;
	}
	
	/**
	 * Liste des fichiers composant le projet
	 */
	private ArrayList<ICompilableFile> fichiers;
	
	
	/**
	 * Constructeur de la classe Projet
	 * @param nomProjet
	 */
	private Projet(String nomProjet)  {
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
	
	public static void compiler() {
		DBCompilation bdd = new DBCompilation();
		
		// Variable correspondant au retour chariot pour la mise en page du fichier texte compil�
		String retourCharriot = System.getProperty("line.separator");
		
		// V�rifie si la liste des projets �xiste
		if (projets != null) {
			
			// Pour chaque projet
			for (Projet projet : projets) {
				
				String titreDuLivre = projet.getNom().replace('_', ' '); // Titre du livre
				int numeroChapitre = 0; // Instanciation du num�ro de chapitre
				int numFichier = 0; // Repr�sente le num�ro du fichier trait�
				int nbTotalMot = 0;
				int nbMotsLastCompile = 0;
				int nbMotsCompile = 0;
				int idLivre = bdd.getIdLivre(titreDuLivre);				
				
				if (idLivre != 0) {
					nbTotalMot = bdd.getNbTotalMots(idLivre);
					nbMotsLastCompile = bdd.getNbMots(idLivre);
				}
				
				try {					
					
					// Chemin du projet fictif en sp�cifiant le suffixe du fichier
					Path newProjet = (new File(WorkFolder.getFolder().getRepertoire().toFile(), projet.getNom() + "_compile.txt")).toPath(); 
					
					// Si le fichier du projet n'�xiste pas
					if(!Files.exists(newProjet)) {
						Files.createFile(newProjet); // Cr�� le fichier correspondant au projet						
					}
					
					// Ecriture du titre du livre dans le fichier texte compil�
					Files.writeString(newProjet, titreDuLivre + retourCharriot + retourCharriot, StandardOpenOption.APPEND);
					
					// Pour chaque fichiers du projet
					for (ICompilableFile fichier : projet.fichiers) {						
						
						// Si fichier repr�sente un nouveau chapitre
						if (fichier.getChemin().toString().endsWith("-.md")) {	
							// Ecriture du num�ro de chapitre
							numeroChapitre++;
							Files.writeString(newProjet,retourCharriot + "Chapitre " + numeroChapitre + retourCharriot + retourCharriot, StandardOpenOption.APPEND);							
						}
						
						else {
							if (numFichier > 0) {
								// Ecriture du s�parateur entre fichiers intra chapitre
								Files.writeString(newProjet, retourCharriot + "*" + retourCharriot + retourCharriot, StandardOpenOption.APPEND);
							}
						}							
						
						List<String> lignes = Files.readAllLines(fichier.getChemin()); // Toutes les lignes d'un des fichier
						
						// Pour chaque lignes du fichier
						for (String ligne : lignes) {
							
							// Ecrit dans le fichier la ligne en cours
							Files.writeString(newProjet, ligne + retourCharriot, StandardOpenOption.APPEND); 	
						}
						
						numFichier++; // Incr�mente le num�ro du fichier trait�
					}
					
					// Instancie le nouveau fichier compil� en fichier Texte via la fabrique de BaseFile
					TextFile newLivre = (TextFile)BaseFile.Fabrik(newProjet);
					int nbMots = newLivre.getNbrMots(); // Calcule le nombre de mots dans le fichier compil�
					
					System.out.println(titreDuLivre + " contient " + nbMots + " mots."); // Nombre de mots
					
					if (idLivre == 0) {
						bdd.insertLivre(titreDuLivre, nbMots);
						System.out.println("Premi�re compilation du livre intitul� : " + titreDuLivre);
					} else {
						nbMotsCompile = nbMots - nbTotalMot;
						bdd.updateLivre(idLivre, nbMots, nbMotsCompile);
						System.out.println("La compilation en cour comprend : " + nbMotsCompile + ", tandis que la derni�re compilation comprenait : " + nbMotsLastCompile);
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
		} else {
			System.out.println("Impossible de compiler, aucun projet trouv�."); // Erreur
		}
		
		bdd.finalize();
	}
}



