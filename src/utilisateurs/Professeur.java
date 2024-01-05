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

/*
 * La classe Professeur hérite de la classe Utilisateur et représente un professeur dans l'application.
 * Elle a un attribut 'langue' qui représente la langue que le professeur enseigne.
 */
public class Professeur extends Utilisateur {
//	private String langue;
	private Langue langue;
	
	/**
     * Représente le sélecteur de fichier utilisé pour ouvrir les fichiers .txt contenant les exercices.
     */
    private static JFileChooser fileChooser = new JFileChooser(".");
    
    
    private List<Exercice> listeExercices;
	
	/*
	 * Constructeur pour la classe Professeur.
	 * Il permet d'instancier un objet Professeur en renseignant le login et la langue enseignée par ce professeur.
	 * @param login le login du professeur
	 * @param langue la langue enseignée par le professeur
	 */
	public Professeur(String id, String motDePasse, String nom, String prenom, Langue langue) {
		super(id, motDePasse, nom, prenom);
		this.langue = langue;
	}
	
	public String getLanguage() {
		return langue.getLangue();
	}
	
//    /**
//     *  Méthode qui retourne la langue du professeur en String.
//     *  @return Un String la langue enseignée par le professeur.
//     */
//    public String LangueString() {
//        return this.getLanguage().toString();
//    }
	
    /*
     * Méthode pour voir les exercices disponibles pour la langue du professeur
     */
//	public String viewAvailableExercises() throws IOException {
//	    ImportExercice ie = new ImportExercice();
//	    StringBuilder result = new StringBuilder();
//	    result.append("Exercices disponibles :\n");
//	    List<Exercice> listeExercices = ie.importDossier("../EXO");
//
//	    for (Exercice exercice : listeExercices) {
//	        // n'affiche que les exercices associés à la langue du professeur
//	        if (exercice.getLangue() == langue) {
//	            result.append("- ").append(exercice.toString()).append("\n");
//	        }
//	    }
//
//	    return result.toString();
//	}

    public String viewAvailableExercises() throws IOException {
    	ImportExercice ie = new ImportExercice();
    	StringBuilder result = new StringBuilder();
    	
        System.out.println("Exercices disponibles :");
        List<Exercice> listeExercices = ie.importDossier("../EXO");
        for (Exercice exercice : listeExercices) {
        	// n'affiche que les exercices associés à la langue du professeur
        	if (exercice.getLangue() == langue) {
        		String exoView = exercice.previewText();
        		result.append("- ").append(exoView).append("\n");
        	}
        }
        return result.toString();
    }

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
        String message = "\nIl faut bien préciser les métadonnées sur la première lignes du fichier : \n" + "LANG:NIVEAU:POURCENTAGE_POINT_POUR_REUSSIR\n" +
                "Par exemple: 'FR:DEBUTANT:0.5' signifie qu'il s'agit d'un exercice de français pour débutants et qu'il faut obtenir 50% des points pour que l'exercice soit considéré comme réussi.\n" +
                "- Langues disponibles : " + String.join(", ", languesPossibles) + "\n" +
                "- Niveaux : " + String.join(", ", niveauxPossibles) + "\n" +
                "- Pourcentage : entre 0 et 1\n";
        JOptionPane.showMessageDialog(null, message, "ATTENTION", JOptionPane.INFORMATION_MESSAGE);

        // ouverture de la boîte de dialogue pour sélectionner le fichier
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Spécifiez le chemin de destination où vous souhaitez copier le fichier
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
        
        
        
        
        
//        // si l'utilisateur a sélectionné un fichier
//        if (res == JFileChooser.APPROVE_OPTION) {
//        	ImportExercice importExercice = new ImportExercice();
//        	listeExercices.add(importExercice.readFromFile(fileChooser.getSelectedFile())); // on crée l'exercice et on l'ajoute à la liste des exercices
//        }
    }
	
	public void manageExercise() {
        // Logique de gestion des exercices existants
		//TODO
    }
	
	public void viewStudentProgress() {
        // Logique pour consulter les progrès des élèves
		//TODO
    }

}
