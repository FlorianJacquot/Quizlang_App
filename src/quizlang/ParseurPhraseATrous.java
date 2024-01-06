package quizlang;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  La classe ParseurPhraseATrous implémente l'interface Metaparse
 *  et permet de parser les phrases qui composent un {@link ExoATrous}.
 *  Elle retourne un objet de type PhraseATrous contenant la phrase correcte, la phrase avec les trous et la liste des mots à trouver.
 */
public class ParseurPhraseATrous {
	
	/**
    * Chaine de caractères utilisée pour délimiter les parties de l'input à parser.
    */
    protected String delimiter = "";
    
    /**
     * Objet {@link Pattern} utilisé pour parser l'input.
     */
    protected Pattern pattern = null;
    
    /**
     * Objet {@link Pattern} utilisé pour parser l'input dans le sens inverse.
     */
    protected Pattern reversedPattern = null;
    
    /**
     * Méthode qui retourne l'objet {@link Pattern} utilisé pour parser l'input dans le sens inverse.
     * C'est utile pour reconstituer l'exercice avec les réponses de l'élève.
     *
     * @return l'objet {@link Pattern} utilisé pour parser l'input dans le sens inverse
     */
    public Pattern getReversedPattern(){
        return this.reversedPattern;
    }

    /**
     * Constructeur de la classe ParseurPhraseATrous.
     * Initialise le délimiteur de la phrase à trous et le motif de la regex utilisé pour extraire les mots à trouver.
     */
    public ParseurPhraseATrous() {
        delimiter = "#";
        String p = String.join("",delimiter, "([^", delimiter, "]+)", delimiter);
        pattern = Pattern.compile(p);
        reversedPattern = Pattern.compile("\\b(___)\\b");
    }

    /**
     * Méthode qui permet de parser une phrase avec des trous sous la forme "#mot#" et de retourner une {@link PhraseATrous} avec la phrase correcte, la phrase avec les trous et la liste des mots à placer.
     *
     * @param phraseAParser La phrase à parser sous forme de chaîne de caractères.
     * @return La {@link PhraseATrous} créée à partir de la phrase à parser.
     */
    public PhraseATrous parse(String phraseAParser) {
        String phraseCorrecte = phraseAParser.replaceAll(delimiter, "");
        String phraseAvecTrous = phraseAParser.replaceAll(pattern.toString(), "___");

        ArrayList<String> motsAPlacer = new ArrayList<>();
        Matcher m = pattern.matcher(phraseAParser);
        while (m.find()) { //tant qu'on trouve des "#([^#]+)#"
            motsAPlacer.add(m.group(1)); //on rajoute ce qui se trouve entre les # à la liste de mots à placer
        }

        PhraseATrous phrase = new PhraseATrous(phraseCorrecte,phraseAvecTrous, motsAPlacer);
        return phrase;
    }

}
