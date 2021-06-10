package gestionFichier;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Interface devant �tre impl�ment�e par tous les fichiers d�placables.
 * @author nicolas
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
		
		String repParent = this.getChemin().getName(this.getChemin().getNameCount() - 2).getFileName().toString();
		
		if(repParent.equals("old")) return false;	//Ce fichier est dans le r�pertoire "old".
		
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
