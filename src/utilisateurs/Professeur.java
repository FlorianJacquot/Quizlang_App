package utilisateurs;

import java.util.List;

//import quizlang.Exercice;
import quizlang.Langue;

/*
 * La classe Professeur hérite de la classe Utilisateur et représente un professeur dans l'application.
 * Elle a un attribut 'langue' qui représente la langue que le professeur enseigne.
 */
public class Professeur extends Utilisateur {
	private String langue;
//    private List<Exercice> listeExercices;
	
	/*
	 * Constructeur pour la classe Professeur.
	 * Il permet d'instancier un objet Professeur en renseignant le login et la langue enseignée par ce professeur.
	 * @param login le login du professeur
	 * @param langue la langue enseignée par le professeur
	 */
	public Professeur(String id, String motDePasse, String nom, String prenom, String langue) {
		super(id, motDePasse, nom, prenom);
		this.langue = langue;
	}
	
	public String getLanguage() {
		return langue;
	}
	
//    /**
//     *  Méthode qui retourne la langue du professeur en String.
//     *  @return Un String la langue enseignée par le professeur.
//     */
//    public String LangueString() {
//        return this.getLanguage().toString();
//    }

	public void createExercise() {
        // Logique de création d'exercice
		//TODO
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
