package utilisateurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import quizlang.BaremeNiveau;
import quizlang.Exercice;
import quizlang.ImportExercice;
import quizlang.Langue;

/**
 * Classe représentant un apprenant de langue dans l'application quizlang qui
 * permet de faire des exercices de langue. Un apprenant est un utilisateur de
 * l'application qui peut s'inscrire sur l'application et
 * participer à des quizz définies par des professeurs.
 *
 * Un apprenant a un niveau ({@link BaremeNiveau}). Ce niveau détermine le nombre de points qu'il
 * peut obtenir pour chaque réponse juste, fausse ou non répondue lorsqu'il fait
 * un exercice. L'élève peut monter ou descendre d'un niveau dans une langue
 * donnée en réussissant ou en ratant des exercices. Ce passage de niveau est
 * réalisé par rapport au score qu'il a dans la langue.
 * 
 * @see BaremeNiveau
 */

public class Apprenant extends Utilisateur {

	private Langue langue;
	private BaremeNiveau niveau;
	private int score;

	/**
     * Constructeur permettant de créer un apprenant avec un login donné.
     * 
     * @param id     Identifiant de l'apprenant
     * @param mdp    Mot de passe de l'apprenant
     * @param nom    Nom de l'apprenant
     * @param prenom Prénom de l'apprenant
     * @param langue Langue dans laquelle l'apprenant participe aux exercices
     * @param niveau Niveau initial de l'apprenant dans la langue
     * @param score  Score initial de l'apprenant dans la langue
     */
	public Apprenant(String id, String mdp, String nom, String prenom, Langue langue, BaremeNiveau niveau, int score) {
		super(id, mdp, nom, prenom);
		this.langue = langue;
		this.niveau = niveau;
		this.score = score;
	}
	
	/**
     * Méthode permettant de récupérer le niveau de l'apprenant.
     *
     * @return Niveau de l'apprenant dans la langue donnée
     */
	public BaremeNiveau getBaremeNiveau() {
		return niveau;
	}

	/**
     * Modifie le niveau de l'apprenant dans la langue.
     * 
     * @param newNiveau Nouveau niveau de l'apprenant dans la langue
     */
	public void setNiveau(BaremeNiveau newNiveau) {
		this.niveau = newNiveau;
	}

	/**
     * Récupère la langue dans laquelle l'apprenant participe aux exercices.
     * 
     * @return Langue dans laquelle l'apprenant participe aux exercices
     */
	public Langue getLangue() {
		return langue;
	}

	/**
     * Récupère le score de l'apprenant dans la langue.
     * 
     * @return Score de l'apprenant dans la langue
     */
	public int getScore() {

		return score;
	}

	/**
     * Met à jour le score de l'élève dans la langue.
     * 
     * @param newScore Nouveau score de l'élève dans la langue
     */
	public void updateScore(int newScore) {
		this.score = newScore;
	}

	/**
     * Récupère la liste des exercices accessibles à l'élève.
     * 
     * @return Liste des exercices accessibles à l'élève
     * @throws IOException En cas d'erreur lors de l'importation des exercices
     */
	public ArrayList<Exercice> getExercicesAccessibles() throws IOException {
		ArrayList<Exercice> exoAccessibles = new ArrayList<Exercice>();
		ImportExercice ie = new ImportExercice();
		StringBuilder result = new StringBuilder();

		System.out.println("Exercices disponibles :");
		List<Exercice> listeExercices = ie.importDossier("../EXO");
		for (Exercice exercice : listeExercices) {
			
            // Ajoute les exercices accessibles à la liste en fonction de la langue et du niveau
			if (exercice.getLangue() == langue && exercice.getNiveau().plusPetitQue(niveau)) {
				exoAccessibles.add(exercice);
			}
		}
		return exoAccessibles;
	}
}
