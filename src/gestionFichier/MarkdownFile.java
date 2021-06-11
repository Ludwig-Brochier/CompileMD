package gestionFichier;

import java.nio.file.Path;
import texteFichier.ICompilableFile;

/**
 * Classe g�rant les fichiers markdown. Ces fichiers doivent �tre compt�s, compil�s, mais pas d�plac�.
 * @author Ludwig
 *
 */
public class MarkdownFile extends TextFile implements ICompilableFile {

	/**
	 * Constructeur explicite de la classe.
	 * @param cheminDuFichier
	 */
	protected MarkdownFile(Path cheminDuFichier) {
		super(cheminDuFichier);
	}
	
	/**
	 * Point d'entr�e principal de la classe qui lance les actions devant �tre effectu�es.
	 */
	@Override
	public void action() {
		
		System.out.println("MarkdownFile : " + this.nomDuFichier +
				" NbrMots : " + this.getNbrMots() + 
				" NbrCaracteres : " + this.getNbrCaracteres() +
				" projet : " + this.getNomDuProjet());
		
	}
	
}
