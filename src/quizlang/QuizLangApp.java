package quizlang;

import javax.swing.SwingUtilities;

/**
 * La classe QuizLangApp est la classe principale du programme QuizLang. Elle
 * contient la méthode main, qui est le point d'entrée du programme.
 * 
 * @author Florian Jacquot
 */
public class QuizLangApp {

	/**
	 * Le point d'entrée principal du programme QuizLang. Cette méthode est appelée
	 * au lancement de l'application.
	 *
	 * @param args Les arguments de la ligne de commande (non utilisés dans cette
	 *             application).
	 */
	public static void main(String[] args) {
		// Utilisation de SwingUtilities.invokeLater pour garantir que l'interface
		// utilisateur est créée dans le thread de l'interface graphique.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Création d'un gestionnaire de données pour gérer les données de
				// l'application.
				GestionnaireDonnees gestionnaire = new GestionnaireDonnees();
				new InterfaceUtilisateur(gestionnaire);
			}
		});
	}
}