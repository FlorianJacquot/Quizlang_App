package quizlang;

import java.awt.Component;


//import fake_quizlet.exercice.Exercice;
//import fake_quizlet.notation.ValeurReponse;
//import fake_quizlet.notation.Correction;
//import fake_quizlet.notation.Notation;
//import fake_quizlet.utilisateurs.Eleve;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utilisateurs.Apprenant;

/**
 * La classe ReponseEleve représente la réponse d'un apprenant à un exercice. 
 * Elle contient : 
 * 		- l'exercice que l'apprenant a fait, 
 * 		- l'apprenant qui a répondu, 
 * 		- les réponses fournies par l'apprenant, 
 * 		- la correction de ses réponses : chaque réponse fournie est attribuée une
 * {@link ValeurReponse} : VRAI, FAUX ou NR (non répondu) 
 * 		- la note calculée 
 * 		- et le seuil de passation : la note qu'il faut atteindre pour que l'execice
 * soit considéré comme réussi.
 */
public class ReponseApprenant {

	/**
	 * L'exercice auquel appartiennent ces réponses
	 */
	Exercice exercice;

	/**
	 * L'apprenant qui a fait l'exercice.
	 */
	Apprenant apprenant;

	/**
	 * Les réponses que l'apprenant a founies
	 */
	ArrayList<ArrayList<String>> reponsesFournies = new ArrayList<>();

	/**
	 * La correction des réponses fournies par l'apprenant
	 */
	private ArrayList<ArrayList<ValeurReponse>> reponsesCorrection = new ArrayList<>();

	/**
	 * La note calculée avec l'interface
	 */
	protected Float noteDonnee = 0.0F;

	/**
	 * La note qu'il faut atteindre (fixée par le professeur) pour que l'exercice
	 * soit considéré comme réussi
	 */
	protected Float seuilPassation = 0.0F;

	/**
	 * Constructeur pour la classe ReponseApprenant.
	 * 
	 * @param exercice l'exercice que l'apprenant a fait
	 * @param apprenant    l'apprenant qui a répondu à l'exercice
	 */
	public ReponseApprenant(Exercice exercice, Apprenant apprenant) {
		this.exercice = exercice;
		this.apprenant = apprenant;

		// Définition du seuil de passation pour l'exercice
		this.setSeuilPassation();

		// Initialisation des réponses fournies
		this.reponsesFournies = new ArrayList<>();
	}

	/**
	 * Méthode qui collecte les réponses de l'apprenant depuis un panneau Swing.
	 * 
	 * @param panel Le panneau Swing contenant les réponses de l'apprenant.
	 */
	public void collecterReponses(JPanel panel) {
		ArrayList<String> listeTampon = new ArrayList<>();
		for (Component component : panel.getComponents()) {
			listeTampon.clear(); // on vide la liste tampon
			if (component instanceof JPanel) {
				JPanel phrasePanel = (JPanel) component;
				for (Component innerComponent : phrasePanel.getComponents()) {
					if (innerComponent instanceof JPanel) {
						JPanel motPanel = (JPanel) innerComponent;
						for (Component subComponent : motPanel.getComponents()) {
							if (subComponent instanceof JTextField) {
								JTextField motTextField = (JTextField) subComponent;
								String reponse = motTextField.getText();
								listeTampon.add(reponse);
							}
						}
					}
				}
				this.reponsesFournies.add(new ArrayList<String>(listeTampon));
			}
		}
	}

	/**
	 * Getter pour l'exercice correspondant à cette réponse.
	 * 
	 * @return l'exercice correspondant à cette réponse.
	 */
	public Exercice getExercice() {
		return this.exercice;
	}

	/**
	 * Getter pour l'apprenant ayant répondu à l'exercice.
	 * 
	 * @return l'apprenant ayant répondu à l'exercice.
	 */
	public Apprenant getApprenant() {
		return this.apprenant;
	}

	/**
	 * Getter pour les réponses fournies par l'apprenant.
	 * 
	 * @return les réponses fournies par l'apprenant.
	 */
	public ArrayList<ArrayList<String>> getReponsesFournies() {
		return this.reponsesFournies;
	}

	/**
	 * Getter pour la correction des réponses fournies : chaque réponse est attribuée
	 * une {@link ValeurReponse}.
	 * 
	 * @return la correction des réponses fournies.
	 */
	public ArrayList<ArrayList<ValeurReponse>> getReponsesCorrection() {
		return reponsesCorrection;
	}

	/**
	 * Instancie la correction des réponses fournies.
	 * 
	 * @param reponsesCorrection la correction des réponses fournies.
	 */
	public void setReponsesCorrection(ArrayList<ArrayList<ValeurReponse>> reponsesCorrection) {
		this.reponsesCorrection = reponsesCorrection;
	}

	/**
	 * Retourne la note calculée pour cette réponse.
	 * 
	 * @return la note calculée pour cette réponse.
	 */
	public Float getNoteDonnee() {
		return noteDonnee;
	}

	/**
	 * Instancie la note donnée à cette réponse.
	 * 
	 * @param noteDonnee la nouvelle note donnée à cette réponse.
	 */
	public void setNoteDonnee(Float noteDonnee) {
		this.noteDonnee = noteDonnee;
	}

	/**
	 * Retourne vrai si l'apprenant valide (si la note calculée est supérieure ou égale
	 * au seuil de passation), faux sinon.
	 * 
	 * @return vrai si l'élève valide, faux sinon.
	 */
	public Boolean valide() {
		return this.noteDonnee >= this.seuilPassation;
	}

	/**
	 * Méthode pour mettre à jour le score global de l'apprenant. 
	 * S'il valide l'exercice son score augmente de 1, s'il ne valide pas son score baisse de 1.
	 * 
	 * @param eleveValide
	 */
	public void updateScore(Boolean eleveValide) {
		// Mise à jour du score en fonction
		int score = apprenant.getScore();
		if (eleveValide) {
			score++;
		} else {
			score--;
		}
		apprenant.updateScore(score);
	}

	/**
	 * Méthode pour mettre à jour le niveau de l'apprenant à partir du score.
	 * Il y a trois niveaux :
	 * 		- score < 5 : niveau débutant
	 * 		- 5 <= score < 10 : niveau intermédiaire
	 * 		- score >= 10 : niveau avancé
	 */
	public void updateNiveau() {
		if (apprenant.getScore() < 5) {
			apprenant.setNiveau(BaremeNiveau.DEBUTANT);
		} else if (apprenant.getScore() >= 5 && apprenant.getScore() < 10) {
			apprenant.setNiveau(BaremeNiveau.INTERMEDIAIRE);
		} else if (apprenant.getScore() >= 10) {
			apprenant.setNiveau(BaremeNiveau.AVANCE);
		}
	}

	/**
	 * Méthode setSeuilPassation qui définit le seuil de réussite d'un exercice en
	 * fonction de son pourcentage de réussite et du nombre de réponses à fournir.
	 * Elle calcule le nombre total de réponses à fournir en comptant le nombre de
	 * mots à placer dans chaque phrase de l'exercice, puis définit le seuil de
	 * réussite en multipliant ce nombre par le pourcentage de réussite de
	 * l'exercice.
	 */
	public void setSeuilPassation() {
		Exercice exo = (Exercice) this.exercice;
		Float totalReponsesAFournir = 0.0F;
		for (PhraseATrous phrase : exo.getListPhrases()) {
			totalReponsesAFournir += phrase.getMotsAPlacer().size();
		}
		this.seuilPassation = exo.getPourcentage() * totalReponsesAFournir;
	}

	/**
	 * Getter qui permet de récupérer la note seuil que l'élève doit atteindre pour
	 * que l'exercice soit considéré comme réussi, validé.
	 * 
	 * @return la note seuil que l'élève doit atteindre pour que l'exercice soit
	 *         considéré comme réussi.
	 */
	public Float getSeuilPassation() {
		return this.seuilPassation;
	}

	/**
	 * Méthode qui corrige les réponses de l'élève à l'exercice à trous. Pour chaque
	 * phrase de l'exercice, la méthode compare chaque réponse de l'élève à la
	 * réponse attendue et attribue la valeur VRAI si la réponse de l'élève est
	 * correcte, FAUX si elle est incorrecte et NR si elle est vide.
	 */
	public void corrige() {
		ArrayList<ValeurReponse> listTampon = new ArrayList<>();
		Exercice exo = (Exercice) exercice;
		ArrayList<PhraseATrous> listPhrases = exo.getListPhrases();

		for (int i = 0; i < listPhrases.size(); i++) { // pour i allant de 0 au nombre de phrases dans l'exo

			listTampon.clear(); // on vide la liste tampon après chaque phrase
			PhraseATrous phrase = listPhrases.get(i);

			for (int j = 0; j < phrase.getMotsAPlacer().size(); j++) { // pour j allant de 0 au nombre de mots à placer
																		// dans une phrase
				String reponseElevePourLaPhraseIEtLeMotJ = this.reponsesFournies.get(i).get(j);

				if (reponseElevePourLaPhraseIEtLeMotJ.equals(phrase.getMotsAPlacer().get(j))) { // si la réponse de
																								// l'élève correspond à
																								// la bonne réponse
					listTampon.add(ValeurReponse.VRAI);
				} else if (reponseElevePourLaPhraseIEtLeMotJ.isEmpty()) { // si l'élève n'a pas répondu
					listTampon.add(ValeurReponse.NR);
				} else { // si la réponse de l'élève est fausse
					listTampon.add(ValeurReponse.FAUX);
				}

			}

			this.getReponsesCorrection().add(new ArrayList<ValeurReponse>(listTampon));
		}
	}

	/**
	 * Calcule la note attribuée à la réponse de l'apprenant en utilisant le barème de
	 * notation spécifié dans le niveau de l'exercice. La note est calculée en
	 * parcourant la correction des réponses, et en attribuant des points en
	 * fonction de la valeur de chaque réponse (VRAI, FAUX, NR). Les points sont
	 * définis par le barème du niveau de l'exercice.
	 * 
	 * Le calcul de la note prend en compte les points attribués pour chaque phrase
	 * corrigée, en ajoutant les points correspondants pour chaque type de réponse.
	 * 
	 * @see BaremeNiveau
	 * @see ValeurReponse
	 */
	public void calculNote() {
		BaremeNiveau niveauExercice = this.getExercice().getNiveau();
		for (ArrayList<ValeurReponse> phraseCorrigee : this.getReponsesCorrection()) {
			for (ValeurReponse v : phraseCorrigee) {
				switch (v) {
				case NR:
					this.noteDonnee += niveauExercice.getNr();
					break;
				case FAUX:
					this.noteDonnee += niveauExercice.getFaux();
					break;
				case VRAI:
					this.noteDonnee += niveauExercice.getVrai();
					break;
				default:
					System.out.println("Valeur incorrecte");
					break;
				}
			}
		}
	}
}
