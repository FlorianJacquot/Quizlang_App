package quizlang;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ImportExercice est utilisée pour importer des exercices depuis des fichiers.
 *
 * Elle contient également deux méthodes pour lire un fichier : readFromFile() et readFile().
 * Ces méthodes permettent de lire les métadonnées et l'input de l'exercice contenu dans le fichier,
 * et de créer un objet {@link Exercice} en utilisant le parseur.
 */
public class ImportExercice {

	/**
     * Lit un fichier et crée un objet {@link Exercice} à partir de son contenu.
     *
     * @param file le fichier à lire
     * @return l'objet {@link Exercice} créé à partir du fichier
     * @throws IOException si une erreur d'entrée-sortie survient lors de la lecture du fichier
     */
	public Exercice readFromFile(File file) throws IOException {
		Langue langueExo = null;
		BaremeNiveau niveauExo = null;
		Float pourcentageExo = 0.0F;
		String inputParser = "";
		Exercice exoACreer = null;

		BufferedReader reader = new BufferedReader(new FileReader(file));

		// Lit chaque ligne du fichier
		String line;
		int i = 1;
		while ((line = reader.readLine()) != null) {
			if (i == 1) {
				String[] metadata = line.split(":");
				if (metadata.length == 3) {
					langueExo = Langue.valueOf(metadata[0]);
					niveauExo = BaremeNiveau.valueOf(metadata[1]);
					pourcentageExo = Float.parseFloat(metadata[2]);
				}
			} else {
				inputParser += line;
			}
			i++;
		}

		// Ferme le fichier
		reader.close();
		ParseurPhraseATrous parseur = new ParseurPhraseATrous();
		exoACreer = new Exercice(langueExo, niveauExo, pourcentageExo, parseur, inputParser);

		Path source = Paths.get(file.getPath());
		Path target = Paths.get("EXO" + "/" + file.getName());
		if (Files.notExists(target)) {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Fichier copié avec succès.");
		}

		System.out.println("\nL'exercice a été créé avec succès.\n");

		return exoACreer;
	}

	/**
     * Lit un fichier à partir de son chemin et crée un objet {@link Exercice} à partir de son contenu.
     *
     * @param path le chemin du fichier à lire
     * @return l'objet {@link Exercice} créé à partir du fichier
     * @throws IOException si une erreur d'entrée-sortie survient lors de la lecture du fichier
     */
	public Exercice readFile(String path) throws IOException {
		// Données à remplir
		Langue langueExo = null;
		BaremeNiveau niveauExo = null;
		Float pourcentageExo = 0.0F;
		String inputParser = "";
		Exercice exoACreer = null;

		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		// Lit chaque ligne du fichier
		String line;
		int i = 1;
		while ((line = reader.readLine()) != null) {
			if (i == 1) {
				String[] metadata = line.split(":");
				if (metadata.length == 3) {
					langueExo = Langue.valueOf(metadata[0]);
					niveauExo = BaremeNiveau.valueOf(metadata[1]);
					pourcentageExo = Float.parseFloat(metadata[2]);
				}
			} else {
				inputParser += line;
			}
			i++;
		}

		// Ferme le fichier
		reader.close();
		ParseurPhraseATrous parseur = new ParseurPhraseATrous();
		exoACreer = new Exercice(langueExo, niveauExo, pourcentageExo, parseur, inputParser);

		Path source = Paths.get(file.getPath());
		Path target = Paths.get("../EXO" + "/" + file.getName());
		if (Files.notExists(target)) {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Fichier copié avec succès.");
		}

		System.out.println("\nL'exercice a été créé avec succès.\n");

		return exoACreer;
	}

	/**
     * Cette méthode permet d'importer tous les exercices contenus dans un dossier donné
     * en utilisant la méthode {@link ImportExercice#readFile(String)}.
     *
     * @param pathDossier Le chemin vers le dossier contenant les exercices à importer.
     * @return Une liste d'objets {@link Exercice} correspondant aux exercices importés.
     * @throws IOException Si un problème est survenu lors de la lecture des fichiers.
     */
	public List<Exercice> importDossier(String pathDossier) throws IOException {
		File dossier = new File(pathDossier);
		String[] files = dossier.list();
		List<Exercice> listeExercice = new ArrayList<>();

		if (files != null) {
			for (String file : files) {
				System.out.println(file);
				listeExercice.add(readFile(pathDossier + "/" + file));
			}
		} else {
			System.out.println("Directory is empty or does not exist.");
		}

		return listeExercice;
	}

}
