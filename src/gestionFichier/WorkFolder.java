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
 * @author nicolas
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
	 * Constructeur de la classe qui initialise la liste des fichiers.
	 * @param cheminDuRepertoire
	 */
	private WorkFolder(String cheminDuRepertoire) {
		
		fichiers = new ArrayList<BaseFile>();
		
    	try {
    		
			Files.walk(Paths.get(cheminDuRepertoire))
				.filter(Files::isRegularFile)
				.forEach(x -> fichiers.add(BaseFile.Fabrik(x)));
			Collections.sort(fichiers);
			
			this.repertoire = Paths.get(cheminDuRepertoire);
			nomDuProjetDefaut = this.getRepertoire().getName(this.getRepertoire().getNameCount() - 1).toString();
			
			File oldRep = new File(this.getRepertoire().toFile(), "old");
			this.repertoireOld = oldRep.toPath();
			if(!Files.exists(this.getRepertoireOld())) {
				Files.createDirectory(this.getRepertoireOld());
			}
			
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
