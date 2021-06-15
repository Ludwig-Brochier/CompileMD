package texteFichier;

import java.nio.file.Path;

import gestionFichier.WorkFolder;

/**
 * Interface devant �tre impl�ment�e par tous les fichiers compilables.
 * @author Ludwig
 *
 */
public interface ICompilableFile {

	/**
	 * Chemin physique du fichier sur le disque dur.
	 * @return Chemin
	 */
	public Path getChemin();
	
	/**
	 * Permets d'obtenir le nom du projet qui est trouv� � partir du nom du fichier ou du nom du r�pertoire de travail.
	 * @return Le nom du projet
	 * @throws Exception 
	 */
	public default String getNomDuProjet() {
		
		String nomDuProjet;
		String nomDuFichier = getChemin().getFileName().toString();
		
		// R�cup�re l'index du dernier underscore si pr�sent dans le nom du fichier
		int indexUnder = nomDuFichier.lastIndexOf("_");
		
		// Si underscore non pr�sent dans le nom du fichier
		if(indexUnder == -1) {
			
			try {
				nomDuProjet = WorkFolder.getFolder().getNomDuProjetDefaut(); // R�cup�re le nom par d�faut du projet se trouvant dans WorkFolder
			} catch (Exception e) {
				nomDuProjet = "erreur_nom_projet";
			}
			
		} else {
			
			nomDuProjet = nomDuFichier.substring(0, indexUnder) + "_compile"; // R�cup�re toute la partie gauche du nom du fichier jusqu'au dernier underscore
			
		}
		
		return nomDuProjet;
	}
	
}
