package quizlang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import utilisateurs.Apprenant;
import utilisateurs.Professeur;

/**
 * Classe représentant un gestionnaire de données pour les apprenants et les
 * professeurs.
 * 
 * Cette classe permet de charger les données à partir de fichiers, d'ajouter
 * des apprenants et des professeurs, d'écrire les informations dans des
 * fichiers, de supprimer des apprenants et des professeurs, et de mettre à jour
 * les informations d'un apprenant.
 */
public class GestionnaireDonnees {

	/** Liste des apprenants */
	private List<Apprenant> apprenants = new ArrayList<>();

	/** Liste des professeurs */
	private List<Professeur> professeurs = new ArrayList<>();

	/**
	 * Constructeur de la classe. Charge les apprenants et les professeurs à partir
	 * de fichiers.
	 */
	public GestionnaireDonnees() {
		loadLearnerFromFile("../DATA/Apprenants.txt");
		loadTeacherFromFile("../DATA/Professeurs.txt");
	}

	/**
	 * Charge les apprenants à partir d'un fichier.
	 *
	 * @param filename Nom du fichier contenant les informations des apprenants.
	 */
	private void loadLearnerFromFile(String filename) {
		try (Scanner fileScanner = new Scanner(new File(filename))) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(";");
				if (parts.length == 7) {
					String id = parts[0];
					String mdp = parts[1];
					String nom = parts[2];
					String prenom = parts[3];
					String langue = parts[4];
					String niveau = parts[5];
					String score = parts[6];

					Langue langueA = Langue.fromString(langue);
					BaremeNiveau niveauA = BaremeNiveau.fromString(niveau);
					int scoreA = Integer.parseInt(score);
					this.addLearner(new Apprenant(id, mdp, nom, prenom, langueA, niveauA, scoreA), false);
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier apprenant introuvable: " + filename);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Format de données invalide dans le fichier apprenant.");
		}
	}

	/**
	 * Charge les professeurs à partir d'un fichier.
	 *
	 * @param filename Nom du fichier contenant les informations des professeurs.
	 */
	private void loadTeacherFromFile(String filename) {
		try (Scanner fileScanner = new Scanner(new File(filename))) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(";");
				if (parts.length == 5) {
					String id = parts[0];
					String mdp = parts[1];
					String nom = parts[2];
					String prenom = parts[3];
					String langue = parts[4];
					Langue langueProf = Langue.fromString(langue);
					this.addTeacher(new Professeur(id, mdp, nom, prenom, langueProf), false);
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier apprenant introuvable: " + filename);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Format de données invalide dans le fichier apprenant.");
		}
	}

	/**
	 * Ajoute un apprenant à la liste et, si nécessaire, écrit les informations dans
	 * un fichier.
	 *
	 * @param apprenant   L'apprenant à ajouter.
	 * @param writeToFile Indique s'il faut écrire les informations dans un fichier.
	 */
	public void addLearner(Apprenant apprenant, boolean writeToFile) {
		apprenants.add(apprenant);
		if (writeToFile) {
			System.out.println("on écrit dans le fichier ../DATA/Apprenants.txt");
			writeLearnerToFile(apprenant, "../DATA/Apprenants.txt");
		}
	}

	/**
	 * Ajoute un professeur à la liste et, si nécessaire, écrit les informations
	 * dans un fichier.
	 *
	 * @param professeur  Le professeur à ajouter.
	 * @param writeToFile Indique s'il faut écrire les informations dans un fichier.
	 */
	public void addTeacher(Professeur professeur, boolean writeToFile) {
		professeurs.add(professeur);
		if (writeToFile) {
			System.out.println("on écrit dans le fichier ../DATA/Professeurs.txt");
			writeTeacherToFile(professeur, "../DATA/Professeurs.txt");
		}
	}

	/**
	 * Écrit les informations d'un apprenant dans un fichier.
	 *
	 * @param apprenant L'apprenant dont les informations doivent être écrites.
	 * @param filename  Nom du fichier dans lequel écrire les informations.
	 */
	private void writeLearnerToFile(Apprenant apprenant, String filename) {
		int score = 0;
		if (apprenant.getBaremeNiveau() == BaremeNiveau.INTERMEDIAIRE) {
			score = 5;
		} else if (apprenant.getBaremeNiveau() == BaremeNiveau.AVANCE) {
			score = 10;
		}

		String learnerData = apprenant.getId() + ";" + apprenant.getMdp() + ";" + apprenant.getName() + ";"
				+ apprenant.getSurname() + ";" + apprenant.getLangue().name() + ";" + apprenant.getBaremeNiveau().name()
				+ ";" + score;

		try (FileWriter myWriter = new FileWriter(filename, true)) { // true pour ajouter à la fin du fichier (append)
			myWriter.write(learnerData + System.lineSeparator());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture dans le fichier: " + e.getMessage());
		}
	}

	/**
	 * Écrit les informations d'un professeur dans un fichier.
	 *
	 * @param professeur Le professeur dont les informations doivent être écrites.
	 * @param filename   Nom du fichier dans lequel écrire les informations.
	 */
	private void writeTeacherToFile(Professeur professeur, String filename) {

		String learnerData = professeur.getId() + ";" + professeur.getMdp() + ";" + professeur.getName() + ";"
				+ professeur.getSurname() + ";" + professeur.getLangueString();

		try (FileWriter myWriter = new FileWriter(filename, true)) { // true pour ajouter à la fin du fichier (append)
			myWriter.write(learnerData + System.lineSeparator());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture dans le fichier: " + e.getMessage());
		}
	}

	/**
	 * Supprime un professeur de la liste et du fichier.
	 *
	 * @param idProf L'identifiant du professeur à supprimer.
	 */
	public void deleteTeacher(String idProf) {
		Iterator<Professeur> iterator = professeurs.iterator();
		while (iterator.hasNext()) {
			Professeur professeur = iterator.next();
			if (professeur.getId().equals(idProf)) {
				iterator.remove(); // Utiliser l'itérateur pour supprimer l'élément en toute sécurité
				deleteTeacherFromFile(professeur, "../DATA/Professeurs.txt");
			}
		}
	}

	/**
	 * Supprime un apprenant de la liste et du fichier.
	 *
	 * @param idLearner L'identifiant de l'apprenant à supprimer.
	 */
	public void deleteLearner(String idLearner) {
		Iterator<Apprenant> iterator = apprenants.iterator();
		while (iterator.hasNext()) {
			Apprenant apprenant = iterator.next();
			if (apprenant.getId().equals(idLearner)) {
				iterator.remove(); // Utiliser l'itérateur pour supprimer l'élément en toute sécurité
				deleteLearnerFromFile(apprenant, "../DATA/Apprenants.txt");
			}
		}
	}

	/**
	 * Supprime un professeur du fichier.
	 * 
	 * @param professeur Le professeur à supprimer.
	 * @param filename   Nom du fichier dans lequel supprimer les informations.
	 */
	private void deleteTeacherFromFile(Professeur professeur, String filename) {
		try {
			File inputFile = new File(filename);
			File tempFile = new File("../DATA/tempFile.txt");

			try (Scanner scanner = new Scanner(inputFile); FileWriter writer = new FileWriter(tempFile)) {

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(";");
					if (parts.length == 5) {
						String id = parts[0];
						if (!id.equals(professeur.getId())) {
							writer.write(line + System.lineSeparator());
						}
					}
				}
				scanner.close();
			}
			Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			// Remplacer le fichier d'origine par le fichier temporaire
//            if (!Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING)) {
//                throw new IOException("Erreur lors du remplacement du fichier.");
//            }

			JOptionPane.showMessageDialog(null, "Votre compte a bien été supprimé.");
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erreur lors de la manipulation du fichier: " + e.getMessage());
		}
	}

	/**
	 * Supprime un apprenant du fichier.
	 * 
	 * @param professeur L'apprenant à supprimer.
	 * @param filename   Nom du fichier dans lequel supprimer les informations.
	 */
	private void deleteLearnerFromFile(Apprenant apprenant, String filename) {
		try {
			File inputFile = new File(filename);
			File tempFile = new File("../DATA/tempFile.txt");

			try (Scanner scanner = new Scanner(inputFile); FileWriter writer = new FileWriter(tempFile)) {

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(";");
					if (parts.length == 6) {
						String id = parts[0];
						if (!id.equals(apprenant.getId())) {
							writer.write(line + System.lineSeparator());
						}
					}
				}
				scanner.close();
			}
			Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			// Remplacer le fichier d'origine par le fichier temporaire
//            if (!Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING)) {
//                throw new IOException("Erreur lors du remplacement du fichier.");
//            }

			JOptionPane.showMessageDialog(null, "Votre compte a bien été supprimé.");
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erreur lors de la manipulation du fichier: " + e.getMessage());
		}
	}

	/**
	 * Met à jour les informations d'un apprenant dans la liste et dans le fichier.
	 *
	 * @param apprenant L'apprenant dont les informations doivent être mises à jour.
	 */
	public void updateApprenant(Apprenant apprenant) {
		try {
			File inputFile = new File("../DATA/Apprenants.txt");
			File tempFile = new File("../DATA/tempFile.txt");

			try (Scanner scanner = new Scanner(inputFile); FileWriter writer = new FileWriter(tempFile)) {

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(";");
					if (parts.length == 7) {
						String id = parts[0];
						if (!id.equals(apprenant.getId())) {
							writer.write(line + System.lineSeparator());
						} else {
							String mdp = parts[1];
							String nom = parts[2];
							String prenom = parts[3];
							String langue = parts[4];
							String niveau = apprenant.getBaremeNiveau().name();
							String score = String.valueOf(apprenant.getScore());
							writer.write(id + ";" + mdp + ";" + nom + ";" + prenom + ";" + langue + ";" + niveau + ";"
									+ score + System.lineSeparator());
						}
					}
				}
				scanner.close();
			}
			Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erreur lors de la manipulation du fichier: " + e.getMessage());
		}
	}

}
