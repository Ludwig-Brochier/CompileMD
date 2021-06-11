/**
 * 
 */
package gestionFichier;

import java.nio.file.Path;

/**
 * Classe g�rant les fichiers excel. Ces fichiers doivent �tre d�plac�, mais pas compil� ni compt�s.
 * @author Ludwig
 *
 */
public class ExcelFile extends BaseFile implements IDeplacable{

	protected ExcelFile(Path cheminDuFichier) {
		super(cheminDuFichier);
	}
	
	@Override
	public void action() {
		if (this.getIsDeplacable()) {
			System.out.println("ExcelFile : " + this.nomDuFichier + " ok");
			this.deplacer();
		} else {
			System.out.println("ExcelFile : " + this.nomDuFichier + " ko");
		}	
	}
}

