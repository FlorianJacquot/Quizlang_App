//package quizlang;
//
//import java.util.List;
//
//public class Exercice {
//    private List<String> questions;
//    private List<String> reponses;
//    private String niveau;
//
//    public void generate() {
//        // Logique de génération du contenu de l'exercice
//    }
//
//    public void evaluate() {
//        // Logique d'évaluation des réponses soumises par l'élève
//    }
//}
//
//
//
//
//
package quizlang;

import java.util.ArrayList;
import java.util.Collections;

import utilisateurs.Apprenant;

//
//import fake_quizlet.notation.BaremeNiveau;
//import fake_quizlet.reponse.ReponseEleve;
//import fake_quizlet.utilisateurs.Eleve;
//
/**
 * Classe abstraite représentant un exercice. 
 *
 * Cette classe contient plusieurs méthodes :
 * - afficheExercice() : permet d'afficher l'exercice à l'apprenant
 * - afficherCorrection() : permet d'afficher la correction
 */
public class Exercice {
	
    /** Langue de l'exercice */
    private Langue langue;

    /** Niveau de l'exercice :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     * */
    private BaremeNiveau niveau;
    
    /**
     * ArrayList qui contient les objets {@link PhraseATrous} créés à partir de l'input donné par le professeur.
     */
    private ArrayList<PhraseATrous> listPhrases = new ArrayList<>();


    /**
     * Pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    private Float pourcentageReussite;

    /**
     * Constructeur de la classe Exercice.
     *
     * @param langue langue de l'exercice
     * @param niveau niveau de l'exercice
     * @param pourcentage pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     * @param parseur parseur de phrases à trous
     * @param inputProf entrée du professeur contenant les phrases de l'exercice
     */
    public Exercice(Langue langue, BaremeNiveau niveau, Float pourcentage, ParseurPhraseATrous parseur, String inputProf) {
        this.langue = langue;
        this.niveau = niveau;
        this.pourcentageReussite = pourcentage;
        String afterPunct = "(?<=[?!.。])";
        String[] listPhrasesNotParsed = inputProf.split(afterPunct); //on split sur la ponctuation en la gardant
        for(String phraseNotParsed : listPhrasesNotParsed){ //pour chaque phrase non parsée dans la liste des phrases non parsées

            listPhrases.add(parseur.parse(phraseNotParsed)); //on parse avec le parseur de phrase à trous et on l'ajoute à la liste

        }
    }
    
    /**
     * Affiche un aperçu du texte de l'exercice à trous, en affichant seulement les 3 premières phrases.
     * Si le texte contient plus de 3 phrases, affiche "..." à la fin des 3 premières phrases.
     */
    public String previewText() {
        StringBuilder result = new StringBuilder();
        // Affiche le niveau et le résultat requis de l'exo
        result.append(niveau).append(" ").append(pourcentageReussite).append("\n");
        // Affiche seulement les 3 premières phrases du texte
        for (int i = 0; i < 3 && i < listPhrases.size(); i++) {
            result.append(listPhrases.get(i).getPhraseAvecTrous()).append("\n");
        }
        
        // Ajoute un message indiquant qu'il y a plus de phrases dans le texte
        if (listPhrases.size() > 3) {
            result.append("...\n");
        } else {
            result.append("\n"); // retour à la ligne pour que ça fasse plus clean
        }

        return result.toString();
    }
//    public void previewText() {
//        // Affiche seulement les 3 premières phrases du texte
//        for (int i = 0; i < 3 && i < listPhrases.size(); i++) {
//            System.out.println(listPhrases.get(i).getPhraseAvecTrous());
//        }
//        // Affiche un message indiquant qu'il y a plus de phrases dans le texte
//        if (listPhrases.size() > 3) {
//            System.out.println("...");
//        }
//        else {
//            System.out.println(""); //retour à la ligne pour que ça fasse plus clean
//        }
//    }

    /**
     * Implémentation de la méthode abstraite afficheExercice().
     * Elle affiche :
     *
     * - la liste des mots à placer arrangés de manière aléatoire
     * - le texte avec des "___" à la place des mots à placer
     */
    public void afficheExercice() {

        //on récupère tous les mots à placer de chaque phrase de l'exercice
        ArrayList<String> allMotsAPlacer = new ArrayList<>();

        for (PhraseATrous phrase : listPhrases) {
            allMotsAPlacer.addAll(phrase.getMotsAPlacer());
        }

        // on randomise la liste des des mots à placer
        Collections.shuffle(allMotsAPlacer);

        // on affiche la liste des mots à placer
        System.out.println("Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n"); //on affiche la liste de tous les mots à placer

        // on affiche la phrase avec les trousS.
        for (PhraseATrous phrase : listPhrases) {
            System.out.println(phrase.getPhraseAvecTrous());
        }
    }

    /**
     * Getter permettant de récupérer la langue de l'exercice.
     *
     * @return langue de l'exercice
     */
    public Langue getLangue(){
        return this.langue;
    }

    /**
     * Getter permettant de récupérer le niveau de l'exercice :
     *
     * Liste des niveaux possibles :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     *
     * @return niveau de l'exercice
     */
    public BaremeNiveau getNiveau() {
        return niveau;
    }

    /**
     * Getter permettant de récupérer le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit
     * considéré comme réussi.
     *
     * @return pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    public Float getPourcentage() {
        return pourcentageReussite;
    }

    /**
     * Setter permettant de modifier le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     * @param pourcentage le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
    */
    public void setPourcentage(Float pourcentage) {
        this.pourcentageReussite = pourcentage;
    }
    
    /**
     * Getter de l'attribut listPhrases. Il contient la liste de tous les objets {@link PhraseATrous} qui composent l'exercice
     * @return listPhrase
     */
    public ArrayList<PhraseATrous> getListPhrases(){
        return listPhrases;
    }

//    /**
//     * Affiche un aperçu de l'exercice. Elle est utilisée pour que les professeurs puissent voir les exercices déjà enregistrés et que les élèves puissent choisir l'exercice qu'ils veulent faire.
//     * Elle est à définir dans les classe filles de `Exercice`
//     */
//    public abstract void previewText();

    /**
     *  Cette méthode permet de construire la réponse d'un élève à un exercice.
     *  Elle est définie dans les classes filles de `Exercice`.
     *  @param apprenant l'apprenant dont on souhaite construire la réponse
     *  @return La réponse de l'élève à l'exercice
     */
    public ReponseApprenant construireReponse(Apprenant apprenant) {
    	return new ReponseApprenant(this, apprenant);
    }

    /**
     * Affiche la correction de l'exercice, c'est-à-dire les vraies réponses qu'il fallait fournir.
     */
    public void afficherCorrection() {
        System.out.println("\n");
        for(PhraseATrous phrase: listPhrases){
            System.out.println(phrase.getPhraseCorrecte());
        }
        System.out.println("\n");
    }
    
    /**
     * Redéfinition de la méthode toString() de la classe Object.
     * @return (String) Une chaîne de caractères qui représente l'objet ExoATrous.
     */
    @Override
    public String toString() {
        return "Exo{" +
                "listPhrases=" + listPhrases +
                '}';
    }

}