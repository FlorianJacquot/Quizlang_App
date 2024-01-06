package utilisateurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quizlang.BaremeNiveau;
import quizlang.Exercice;
import quizlang.ImportExercice;
//import quizlang.BaremeNiveau;
import quizlang.Langue;
//import quizlang.NiveauApprenant;

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
	/**
	 * Map associant à chaque langue enseignée par ses professeurs le niveau de l'élève dans cette langue
	 */
//	private Map<Langue, BaremeNiveau> niveaux = new HashMap<>();
	private Langue langue;
	private BaremeNiveau niveau;
	private List<Exercice> exercices;

//	/**
//	 * Liste des professeurs auprès desquels l'élève est inscrit.
//	 */
//	private ArrayList<Professeur> listProfesseurs = new ArrayList<>();
//	public Apprenant(String id, String mdp, String nom, String prenom, ArrayList<Professeur> professeurs) {
//		super(id, mdp, nom, prenom);
//		this.listProfesseurs = professeurs;
//		for (Professeur prof: professeurs) {
//			this.niveaux.put(prof.getLangue(), BaremeNiveau.DEBUTANT);
//		}
//	}
	
	/**
	 * Constructeur permettant de créer un apprenant avec un login donné.
	 * @param login login de l'élève
	 */
	public Apprenant(String id, String mdp, String nom, String prenom, Langue langue, BaremeNiveau niveau) {
		super(id, mdp, nom, prenom);
		this.langue = langue;
		this.niveau = niveau;
	}

//	public void niveauApprenant() {
//		super(id, )
//	}
	
//	public String getLanguageLevelString(Langue langue) {
//		BaremeNiveau niveau = niveaux.get(langue);
//		String niveauString = niveau.name();
//		String langueString = langue.name();
//		String niveauLangue = langueString + ":" + niveauString;
//		return niveauLangue;
//	}
	/**
	 * Méthode permettant de récupérer le niveau de l'élève dans une langue donnée.
	 *
	 * @param langue langue pour laquelle on souhaite récupérer le niveau de l'élève
	 * @return niveau de l'élève dans la langue donnée
	 */
	public BaremeNiveau getBaremeNiveau(){
		return niveau;
	}
	public Langue getLangue(){
		return langue;
	}
//	public String getLangueString() {
//		return langue.getLangueString();
//	}
//	public BaremeNiveau getBaremeNiveau(Langue langue){
//		return this.niveaux.get(langue);
//	}
//	public Map<Langue, BaremeNiveau> getNiveauLangue(){
//		return this.niveaux;
//	}
	
	
	
	/**
	 * Méthode permettant de modifier le niveau de l'élève dans une langue donnée.
	 *
	 * @param niveau nouveau niveau de l'élève dans la langue donnée
	 * @param langue langue pour laquelle on souhaite modifier le niveau de l'élève
	 */
	public void setNiveau(BaremeNiveau newNiveau) {
		this.niveau = niveau;
	}
//	public void setNiveau(BaremeNiveau niveau, Langue langue) {
//		this.niveaux.put(langue, niveau);
//	}
	
//	/**
//	 * Récupère la liste des exercices accessibles pour l'utilisateur (élève) courant en fonction de ses niveaux en langues et de la liste des exercices disponibles.
//	 * Un exercice est considéré comme accessible s'il a la même langue et le même niveau que l'utilisateur, ou s'il a la même langue et un niveau inférieur.
//	 *
//	 * @param listNiveauxUtilisateur la liste de tous les niveaux de tous les élèves
//	 * @param listExercices la liste de tous les exercices disponibles dans l'application
//	 * @return la liste des exercices accessibles pour l'utilisateur (élève) courant
//	 */
//	public ArrayList<Exercice> getExercicesAccessibles(ArrayList<NiveauApprenant> listNiveauxUtilisateur, List<Exercice> listExercices) {
//		ArrayList<Exercice> exercicesAccessibles = new ArrayList<>();
//		// Pour chaque enregistrement de niveau de l'utilisateur actif
//		for (NiveauApprenant niveauApprenant: listNiveauxUtilisateur) {
//			// Si l'enregistrement concerne l'utilisateur actif
//			if (niveauApprenant.getIdApprenant().equals(this.getId())) {
//				// Pour chaque exercice de la liste
//				for (Exercice exercice : listExercices) {
//					// Si l'exercice a la même langue et le même niveau que l'enregistrement de l'utilisateur actif, on l'ajoute à la liste des exercices accessibles
//					if (exercice.getLangue().equals(niveauApprenant.getLangue()) && exercice.getNiveau().equals(niveauApprenant.getNiveau())) {
//						exercicesAccessibles.add(exercice);
//					}
//					// Si l'exercice a la même langue et un niveau inférieur à celui de l'enregistrement de l'utilisateur actif, on l'ajoute également à la liste des exercices accessibles
//					else if (exercice.getLangue().equals(niveauApprenant.getLangue()) && exercice.getNiveau().ordinal() < niveauApprenant.getNiveau().ordinal()) {
//						exercicesAccessibles.add(exercice);
//					}
//				}
//			}
//		}
//		if(exercicesAccessibles.isEmpty()){
//			System.out.println("Il n'y a encore aucun exercice accessible pour vos langues et votre niveau.");
//		}
//		return exercicesAccessibles;
//	}
	
	public ArrayList<Exercice> getExercicesAccessibles() throws IOException {
		ArrayList<Exercice> exoAccessibles = new ArrayList<Exercice>();
		ImportExercice ie = new ImportExercice();
		StringBuilder result = new StringBuilder();
		
		System.out.println("Exercices disponibles :");
		List<Exercice> listeExercices = ie.importDossier("../EXO");
		for (Exercice exercice : listeExercices) {
			// n'affiche que les exercices associés à la langue du professeur
			if (exercice.getLangue() == langue && exercice.getNiveau() == niveau) {
				exoAccessibles.add(exercice);
			}
		}
		return exoAccessibles;
	}
	
    public String viewAvailableExercises() throws IOException {
    	ImportExercice ie = new ImportExercice();
    	StringBuilder result = new StringBuilder();
    	
        System.out.println("Exercices disponibles :");
        List<Exercice> listeExercices = ie.importDossier("../EXO");
        for (Exercice exercice : listeExercices) {
        	// n'affiche que les exercices associés à la langue du professeur
        	if (exercice.getLangue() == langue && exercice.getNiveau() == niveau) {
        		String exoView = exercice.previewText();
        		result.append("- ").append(exoView).append("\n");
        	}
        }
        return result.toString();
    }
    
//    public int numberAvailableExercises() {
//    	ImportExercice ie = new ImportExercice();
//    	StringBuilder result = new StringBuilder();
//    	
//        System.out.println("Exercices disponibles :");
//        List<Exercice> listeExercices = ie.importDossier("../EXO");
//        int nbEx = 0;
//        for (Exercice exercice : listeExercices) {
//        	// n'affiche que les exercices associés à la langue du professeur
//        	if (exercice.getLangue() == langue && exercice.getNiveau() == niveau) {
//        		nbEx++;
//        	}
//        return nbEx;
//    }
	
	
	public void selectExercise() throws IOException {
		ImportExercice importExercice = new ImportExercice();
        exercices = importExercice.importDossier("../EXO/");
        
    }

    public void submitExercise() {
        // Logique de soumission d'exercice
    }
	
    public void viewGrades() {
        // Logique pour afficher les notes obtenues dans les exercices
    }

}
