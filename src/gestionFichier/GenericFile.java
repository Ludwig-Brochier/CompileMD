package gestionFichier;

import java.nio.file.Path;

/**
 * Classe g�rant les fichiers non g�r�s. Ces fichiers doivent �tre ni compt�s ni compil�s ni d�plac�.
 * @author Ludwig
 *
 */
public class GenericFile extends BaseFile {

	/**
	 * Constructeur explicite de la classe. D�placable
	 * @param cheminDuFichier
	 */
	protected GenericFile(Path cheminDuFichier) {
		super(cheminDuFichier);
	}

	/**
	 * Point d'entr�e principal de la classe qui lance les actions devant �tre effectu�es.
	 */
	@Override
	public void action() {
		System.out.println("GenericFile : " + this.nomDuFichier);
	}

}
