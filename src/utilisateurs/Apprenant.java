package utilisateurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import quizlang.BaremeNiveau;
import quizlang.Langue;

/**
 * Classe représentant un apprenant de langue dans l'application quizlang qui permet de faire des exercices de langue.
 * Un apprenant est un utilisateur de l'application qui peut s'inscrire auprès de différents professeurs et participer à leurs quizz.
 *
 * Un apprenant a un niveau ({@link BaremeNiveau}) dans chaque langue enseignée par ses {@link Professeur}. Ce niveau détermine le nombre de points qu'il peut obtenir pour chaque réponse juste, fausse ou non répondue lorsqu'il fait un exercice.
 * L'élève peut monter ou descendre d'un niveau dans une langue donnée en réussissant ou en ratant des exercices. Ce passage de niveau est réalisé par rapport au score qu'il a dans la langue.
 * @see BaremeNiveau
 * @see Professeur
 */

public class Apprenant extends Utilisateur {
	
//	/**
//	 * identifiant du professeur.
//	 */
//	public static final String PROFESSOR_ID_FIELD_NAME = "professeur_id";
//
//	/**
//	 * Map associant à chaque langue enseignée par ses professeurs le niveau de l'élève dans cette langue
//	 */
//	private Map<Langue, BaremeNiveau> niveaux = new HashMap<>();
//
//	/**
//	 * Liste des professeurs auprès desquels l'élève est inscrit.
//	 */
//	private ArrayList<Professeur> listProfesseurs = new ArrayList<>();
	private String niveauLangue;
	
	/**
	 * Constructeur permettant de créer un apprenant avec un login donné.
	 * @param login login de l'élève
	 */
	public Apprenant(String id, String mdp, String nom, String prenom, String niveauLangue) {
		super(id, mdp, nom, prenom);
		this.niveauLangue = niveauLangue;
	}
//	
//	/**
//	 * Méthode permettant à un élève de s'inscrire auprès d'un professeur.
//	 *
//	 * @param prof professeur auprès duquel l'élève s'inscrit
//	 */
//	public void ajouterProf(Professeur prof) {
//		this.listProfesseurs.add(prof);
//		this.niveaux.put(prof.getLanguage(), BaremeNiveau.DEBUTANT);
//	}
	
	public String getLanguageLevel() {
		return niveauLangue;
	}
	
	public void selectExercise() {
        // Logique de sélection d'exercice
    }

    public void submitExercise() {
        // Logique de soumission d'exercice
    }
	
    public void viewGrades() {
        // Logique pour afficher les notes obtenues dans les exercices
    }

}
