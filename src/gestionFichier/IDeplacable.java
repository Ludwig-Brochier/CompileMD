package gestionFichier;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Interface devant �tre impl�ment�e par tous les fichiers d�placables.
 * @author Ludwig
 *
 */
public interface IDeplacable {

	/**
	 * Chemin physique du fichier sur le disque dur.
	 * @return Chemin
	 */
	public Path getChemin();
	
	/**
	 * Permets de savoir si le fichier doit �tre d�plac�.
	 * @return Vrai si le fichier doit �tre d�plac�.
	 */
	public default boolean getIsDeplacable() {
		
		// R�cup�re le nom du r�pertoire parent du fichier appelant
		String repParent = this.getChemin().getParent().getFileName().toString();
		
		if(repParent.equals("old")) return false; // Test si r�pertoire �gal � "old"
		
		try {
			
			File fichierDansOld = new File(WorkFolder.getFolder().getRepertoireOld().toFile(), this.getChemin().getFileName().toString());
			if(Files.exists(fichierDansOld.toPath())) return false;	//Ce fichier existe d�j� dans "old".
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Lance le d�placement du fichier.
	 */
	public default void deplacer() {
		
		Path source = getChemin();
		Path cible;
		try {
			
			cible = (new File(WorkFolder.getFolder().getRepertoireOld().toFile(), this.getChemin().getFileName().toString())).toPath();
			Files.move(source, cible);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
