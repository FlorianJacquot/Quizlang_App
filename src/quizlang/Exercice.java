package quizlang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utilisateurs.Apprenant;

/**
 * Classe représentant un exercice.
 *
 * Cette classe contient plusieurs méthodes :
 */
public class Exercice {

	/** Langue de l'exercice */
	private Langue langue;

	/**
	 * Niveau de l'exercice : - DEBUTANT - INTERMEDIAIRE - AVANCE
	 */
	private BaremeNiveau niveau;

	/**
	 * ArrayList qui contient les objets {@link PhraseATrous} créés à partir de
	 * l'input donné par le professeur.
	 */
	private ArrayList<PhraseATrous> listPhrases = new ArrayList<>();

	/**
	 * Pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que
	 * l'exercice soit considéré comme réussi.
	 */
	private Float pourcentageReussite;

	/**
	 * Constructeur de la classe Exercice.
	 *
	 * @param langue      langue de l'exercice
	 * @param niveau      niveau de l'exercice
	 * @param pourcentage pourcentage de points qu'un élève doit obtenir en faisant
	 *                    l'exercice pour que l'exercice soit considéré comme
	 *                    réussi.
	 * @param parseur     parseur de phrases à trous
	 * @param inputProf   entrée du professeur contenant les phrases de l'exercice
	 */
	public Exercice(Langue langue, BaremeNiveau niveau, Float pourcentage, ParseurPhraseATrous parseur,
			String inputProf) {
		this.langue = langue;
		this.niveau = niveau;
		this.pourcentageReussite = pourcentage;
		String afterPunct = "(?<=[?!.。])";
		String[] listPhrasesNotParsed = inputProf.split(afterPunct); // on split sur la ponctuation en la gardant
		for (String phraseNotParsed : listPhrasesNotParsed) { // pour chaque phrase non parsée dans la liste des phrases
																// non parsées
			listPhrases.add(parseur.parse(phraseNotParsed)); // on parse avec le parseur de phrase à trous et on
																// l'ajoute à la liste
		}
	}

	/**
	 * Affiche un aperçu du texte de l'exercice à trous, en affichant seulement les
	 * 2 premières phrases. Si le texte contient plus de 2 phrases, affiche "..." à
	 * la fin des 2 premières phrases.
	 * 
	 * @return Aperçu du texte de l'exercice à trous.
	 */
	public String previewText() {
		StringBuilder result = new StringBuilder();
		// Affiche le niveau et le résultat requis de l'exo
		result.append(niveau).append(" ").append(pourcentageReussite).append("\n");
		// Affiche seulement les 3 premières phrases du texte
		for (int i = 0; i < 2 && i < listPhrases.size(); i++) {
			result.append(listPhrases.get(i).getPhraseAvecTrous()).append("\n");
		}

		// Ajoute un message indiquant qu'il y a plus de phrases dans le texte
		if (listPhrases.size() > 2) {
			result.append("...\n");
		} else {
			result.append("\n"); // retour à la ligne pour que ça fasse plus clean
		}

		return result.toString();
	}

	/**
	 * Affiche un aperçu du texte de l'exercice à trous pour l'apprenant, en
	 * affichant seulement les 2 premières phrases. Si le texte contient plus de 2
	 * phrases, affiche "..." à la fin des 2 premières phrases.
	 *
	 * @return Aperçu du texte de l'exercice à trous pour l'apprenant.
	 */
	public String previewTextApprenant() {
		StringBuilder result = new StringBuilder();
		result.append(niveau.name() + System.lineSeparator());
		// Affiche seulement les 3 premières phrases du texte
		for (int i = 0; i < 2 && i < listPhrases.size(); i++) {
			result.append("\n" + listPhrases.get(i).getPhraseAvecTrous()).append("\n");
		}

		// Ajoute un message indiquant qu'il y a plus de phrases dans le texte
		if (listPhrases.size() > 2) {
			result.append("...\n");
		} else {
			result.append("\n"); // retour à la ligne pour que ça fasse plus clean
		}

		return result.toString();
	}

	/**
	 * Getter permettant de récupérer la langue de l'exercice.
	 *
	 * @return la langue de l'exercice
	 */
	public Langue getLangue() {
		return this.langue;
	}

	/**
	 * Getter permettant de récupérer le niveau de l'exercice :
	 *
	 * Liste des niveaux possibles : - DEBUTANT - INTERMEDIAIRE - AVANCE
	 *
	 * @return le niveau de l'exercice
	 */
	public BaremeNiveau getNiveau() {
		return niveau;
	}

	/**
	 * Getter permettant de récupérer le pourcentage de points qu'un élève doit
	 * obtenir pour réussir l'exercice.
	 *
	 * @return Pourcentage de points qu'un élève doit obtenir pour réussir
	 *         l'exercice.
	 */
	public Float getPourcentage() {
		return pourcentageReussite;
	}

	/**
	 * Setter permettant de modifier le pourcentage de points qu'un élève doit
	 * obtenir pour réussir l'exercice.
	 *
	 * @param pourcentage Le nouveau pourcentage de points pour réussir l'exercice.
	 */
	public void setPourcentage(Float pourcentage) {
		this.pourcentageReussite = pourcentage;
	}

	/**
	 * Getter de l'attribut listPhrases. Il contient la liste de tous les objets
	 * {@link PhraseATrous} qui composent l'exercice.
	 *
	 * @return La liste des phrases à trous de l'exercice.
	 */
	public ArrayList<PhraseATrous> getListPhrases() {
		return listPhrases;
	}

	/**
	 * Cette méthode permet de construire la réponse d'un élève à un exercice.
	 *
	 * @param apprenant L'apprenant dont on souhaite construire la réponse.
	 * @return La réponse de l'élève à l'exercice.
	 */
	public ReponseApprenant construireReponse(Apprenant apprenant) {
		return new ReponseApprenant(this, apprenant);
	}
}