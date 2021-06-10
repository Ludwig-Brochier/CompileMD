package gestionFichier;

import java.nio.file.Path;

/**
 * Classe g�rant les fichiers word. Ces fichiers doivent �tre d�plac�, mais pas compil� ni compt�s.
 * @author nicolas
 *
 */
public class WordFile extends BaseFile implements IDeplacable {

	public WordFile(Path cheminDuFichier) {
		super(cheminDuFichier);
		
	}

	@Override
	public void action() {
		if(this.getIsDeplacable()) {
			System.out.println("WordFile : " + this.nomDuFichier + " ok");
			this.deplacer();
		} else {
			System.out.println("WordFile : " + this.nomDuFichier + " ko");
		}
	}

}
