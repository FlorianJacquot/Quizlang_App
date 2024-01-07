package utilisateurs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import quizlang.BaremeNiveau;
import quizlang.Exercice;
import quizlang.ImportExercice;
import quizlang.Langue;

/**
 * La classe Professeur hérite de la classe Utilisateur et représente un
 * professeur dans l'application. Elle a un attribut 'langue' qui représente la
 * langue que le professeur enseigne.
 */
public class Professeur extends Utilisateur {

	private Langue langue;

	/**
	 * Représente le sélecteur de fichier utilisé pour ouvrir les fichiers .txt
	 * contenant les exercices.
	 */
	private static JFileChooser fileChooser = new JFileChooser(".");

	/**
     * Constructeur pour la classe Professeur.
     * Il permet d'instancier un objet Professeur en renseignant le login et la langue enseignée par ce professeur.
     *
     * @param id          Le login du professeur
     * @param motDePasse  Le mot de passe du professeur
     * @param nom         Le nom du professeur
     * @param prenom      Le prénom du professeur
     * @param langue      La langue enseignée par le professeur
     */
	public Professeur(String id, String motDePasse, String nom, String prenom, Langue langue) {
		super(id, motDePasse, nom, prenom);
		this.langue = langue;
	}

	/**
     * Récupère la langue enseignée par le professeur.
     *
     * @return La langue enseignée par le professeur
     */
	public Langue getLangue() {
		return langue;
	}

	/**
     * Récupère la représentation en chaîne de caractères de la langue enseignée par le professeur.
     *
     * @return La représentation en chaîne de caractères de la langue enseignée par le professeur
     */
	public String getLangueString() {
		return langue.getLangueString();
	}

	/**
     * Affiche les exercices disponibles pour la langue du professeur.
     *
     * @return Une chaîne de caractères représentant les exercices disponibles
     * @throws IOException En cas d'erreur lors de l'importation des exercices
     */
	public String viewAvailableExercises() throws IOException {
		ImportExercice ie = new ImportExercice();
		StringBuilder result = new StringBuilder();

		System.out.println("Exercices disponibles :");
		List<Exercice> listeExercices = ie.importDossier("../EXO");
		for (Exercice exercice : listeExercices) {
			// N'affiche que les exercices associés à la langue du professeur
			if (exercice.getLangue() == langue) {
				String exoView = exercice.previewText();
				result.append("- ").append(exoView).append("\n");
			}
		}
		return result.toString();
	}

	/**
     * Crée un nouvel exercice à partir d'un fichier sélectionné par le professeur.
     *
     * @throws IOException En cas d'erreur lors de la création de l'exercice
     */
	public void createExercise() throws IOException {
		List<String> languesPossibles = new ArrayList<>();
		for (Langue langue : Langue.values()) {
			languesPossibles.add(langue.name());
		}
		List<String> niveauxPossibles = new ArrayList<>();
		for (BaremeNiveau niveau : BaremeNiveau.values()) {
			niveauxPossibles.add(niveau.name());
		}
		// affichage des consignes pour le formatage des métadonnées de l'exercice
		String message = "\nIl faut bien préciser les métadonnées sur la première lignes du fichier : \n"
				+ "LANG:NIVEAU:POURCENTAGE_POINT_POUR_REUSSIR\n"
				+ "Par exemple: 'FR:DEBUTANT:0.5' signifie qu'il s'agit d'un exercice de français pour débutants et qu'il faut obtenir 50% des points pour que l'exercice soit considéré comme réussi.\n"
				+ "- Langues disponibles : " + String.join(", ", languesPossibles) + "\n" + "- Niveaux : "
				+ String.join(", ", niveauxPossibles) + "\n" + "- Pourcentage : entre 0 et 1\n";
		JOptionPane.showMessageDialog(null, message, "ATTENTION", JOptionPane.INFORMATION_MESSAGE);

		// ouverture de la boîte de dialogue pour sélectionner le fichier
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			String destinationPath = "../EXO/" + selectedFile.getName();

			try (FileInputStream inputStream = new FileInputStream(selectedFile);
					FileOutputStream outputStream = new FileOutputStream(destinationPath)) {

				byte[] buffer = new byte[1024];
				int bytesRead;

				while ((bytesRead = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, bytesRead);
				}

				System.out.println("Copie du fichier terminée.");
				JOptionPane.showMessageDialog(null, "Exercice ajouté !", "Bravo", JOptionPane.INFORMATION_MESSAGE);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
