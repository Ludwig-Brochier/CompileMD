package gestionFichier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe m�tier pour le r�pertoire de travail.
 * @author Ludwig
 */
public class WorkFolder {
	
	/**
	 * Singleton qui permet de toujours travailler sur le m�me r�pertoire de travail.
	 */
	private static WorkFolder folder;
	public static WorkFolder getFolder() throws Exception {
		
		if(WorkFolder.folder == null) {
			throw new Exception("Le r�pertoire de travail n'a pas �t� initialis�.");
		}
		
		return folder;
	}
	
	/**
	 * Liste des fichiers qui se trouvent dans le r�pertoire.
	 */
	private ArrayList<BaseFile> fichiers ;
	public ArrayList<BaseFile> getFichiers() {
		return fichiers;
	}
	
	/**
	 * Nom du projet par d�faut correspondant au r�pertoire.
	 */
	private String nomDuProjetDefaut;
	public String getNomDuProjetDefaut() {
		return nomDuProjetDefaut;
	}
	
	/**
	 * Path du r�pertoire de travail.
	 */
	private Path repertoire = null;
	public Path getRepertoire() {
		return repertoire;
	}
	
	/**
	 * Path du r�pertoire old.
	 */
	private Path repertoireOld;
	public Path getRepertoireOld() {
		return repertoireOld;
	}
	
	/**
	 * Nombre de fichier pr�sent dans le r�pertoire old
	 */
	private int nbFichierRepertoireOld;
	public int getNbFichierRepertoireOld() {
		return nbFichierRepertoireOld;
	}
	
	/**
	 * Nombre de fichier d�plac�
	 */
	private int nbFichierDeplace;
	public int getNbFichierDeplace() {
		return nbFichierDeplace;
	}
	public int incrementNbFichierDeplace() {
		return nbFichierDeplace++;
	}
	
	
	/**
	 * Constructeur de la classe qui initialise la liste des fichiers.
	 * @param cheminDuRepertoire
	 */
	private WorkFolder(String cheminDuRepertoire) {
		
		// Initialise la liste des fichiers BaseFile pr�sents dans le r�pertoire de travail
		fichiers = new ArrayList<BaseFile>();
		
    	try {
    		
			Files.walk(Paths.get(cheminDuRepertoire)) // Parcours r�cursivement les fichiers et dossiers du r�pertoire de travail
				.filter(Files::isRegularFile) // Filtre les fichiers des dossiers
				.forEach(x -> fichiers.add(BaseFile.Fabrik(x))); // Ajoute chaque fichier � la liste en utilisant la fabrique dans BaseFile
			Collections.sort(fichiers); // Tri le nom des fichiers de mani�re croissante
			
			this.repertoire = Paths.get(cheminDuRepertoire); // Initialise le chemin du r�pertoire de travail
			nomDuProjetDefaut = this.getRepertoire().getFileName().toString(); // Initialise le nom du projet par d�fault avec le nom du r�pertoire de travail
			
			
			File oldRep = new File(this.getRepertoire().toFile(), "old"); // Instancie un fichier old dans le r�pertoire de travail
			this.repertoireOld = oldRep.toPath(); // Initialise le chemin du r�pertoire old
			// Si le fichier old n'�xiste pas
			if(!Files.exists(this.getRepertoireOld())) {
				Files.createDirectory(this.getRepertoireOld()); // Cr�� le dossier old
			}
			
			Files.walk(repertoireOld)
				.filter(Files::isRegularFile)
				.forEach(x -> nbFichierRepertoireOld++);
			
			System.out.println("--> Le r�pertoire old contient au lancement " + nbFichierRepertoireOld + " fichier(s).");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	/**
	 * M�thode qui initialise le singleton.
	 * @param cheminDuRepertoire chemin physique du r�pertoire de travail.
	 * @throws Exception
	 */
	public static void initialise(String cheminDuRepertoire) throws Exception {
		
		if(WorkFolder.folder != null) {
			throw new Exception("Le r�pertoire de travail a d�j��t� initialis�.");
		}
		
		WorkFolder.folder = new WorkFolder(cheminDuRepertoire);
		
	}

	

	
	
}
