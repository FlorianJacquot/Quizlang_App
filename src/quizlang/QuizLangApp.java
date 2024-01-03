package quizlang;

import javax.swing.SwingUtilities;

public class QuizLangApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			    GestionnaireDonnees gestionnaire = new GestionnaireDonnees();
			    new InterfaceUtilisateur(gestionnaire);
			}
		});
    }
}