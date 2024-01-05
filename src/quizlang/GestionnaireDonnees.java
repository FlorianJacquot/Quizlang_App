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

public class GestionnaireDonnees {
	private List<Apprenant> apprenants = new ArrayList<>();
	private List<Professeur> professeurs = new ArrayList<>();
	
	public GestionnaireDonnees() {
        loadLearnerFromFile("../DATA/Apprenants.txt");
        loadTeacherFromFile("../DATA/Professeurs.txt");
    }

	private void loadLearnerFromFile(String filename) {
//		GestionnaireDonnees gestionnaire = new GestionnaireDonnees();
		try (Scanner fileScanner = new Scanner(new File(filename))) {
	        while (fileScanner.hasNextLine()) {
	        	String line = fileScanner.nextLine();
	        	String[] parts = line.split(";");
	        	if (parts.length == 5) {
	        		String id = parts[0];	        		
	        		String mdp = parts[1];
	        		String nom = parts[2];
	        		String prenom = parts[3];
	        		String niveauLangue = parts[4];
	        		
//	        		String[] listLN = parts[4].split(":");
//                    String langueString = listLN[0];
//                    String niveauString = listLN[1];
//                    Langue l = Langue.fromString(langueString);
//                    BaremeNiveau b = BaremeNiveau.fromString(niveauString);
//                    Map<Langue, BaremeNiveau> langueNiveauApprenant = new HashMap<Langue, BaremeNiveau>();
//                    langueNiveauApprenant.put(l, b);
	        		
	        		Map<Langue, BaremeNiveau> niveauLangueApprenant = NiveauLangueFromString(niveauLangue);	
	        		this.addLearner(new Apprenant(id, mdp, nom, prenom, niveauLangueApprenant), false);
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
	
	private void loadTeacherFromFile(String filename) {
//		GestionnaireDonnees gestionnaire = new GestionnaireDonnees();
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
//                    if (langue.equals("Français")) {
//                    	langueProf = Langue.FR;
//                    } else {
//                    	langueProf = Langue.JP;
//                    }
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
    
    public void addLearner(Apprenant apprenant, boolean writeToFile) {
		apprenants.add(apprenant);
		if (writeToFile) {			
			System.out.println("on écrit dans le fichier ../DATA/Apprenants.txt");
			writeLearnerToFile(apprenant, "../DATA/Apprenants.txt");
		}
	}
    
    public void addTeacher(Professeur professeur, boolean writeToFile) {
    	professeurs.add(professeur);
    	if (writeToFile) {			
    		System.out.println("on écrit dans le fichier ../DATA/Professeurs.txt");
    		writeTeacherToFile(professeur, "../DATA/Professeurs.txt");
    	}
    }
    
    private void writeLearnerToFile(Apprenant apprenant, String filename) {
		
//		String learnerData = apprenant.getId() + "," + apprenant.getMdp() + "," + apprenant.getName() + "," + apprenant.getSurname() + "," + apprenant.getLanguageLevel();
		String learnerData = apprenant.getId() + ";" + apprenant.getMdp() + ";" + apprenant.getName() + ";" + apprenant.getSurname() + ";" + apprenant.getNiveauLangue();
		
		try (FileWriter myWriter = new FileWriter(filename, true)){ //true pour ajouter à la fin du fichier (append)			
			myWriter.write(learnerData + System.lineSeparator());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture dans le fichier: " + e.getMessage());
		}
	}
    
    private void writeTeacherToFile(Professeur professeur, String filename) {
    	
    	String learnerData = professeur.getId() + ";" + professeur.getMdp() + ";" + professeur.getName() + ";" + professeur.getSurname() + ";" + professeur.getLanguage();
    	
    	try (FileWriter myWriter = new FileWriter(filename, true)){ //true pour ajouter à la fin du fichier (append)			
    		myWriter.write(learnerData + System.lineSeparator());
    		myWriter.close();
    	} catch (IOException e) {
    		System.out.println("Erreur lors de l'écriture dans le fichier: " + e.getMessage());
    	}
    }
    
//    public void deleteTeacher(String idProf) {
//    	System.out.println(idProf);
//    	System.out.println(professeurs.size());
//    	for (Professeur professeur : professeurs) {
//    		System.out.println(professeur.getId());
//    		if (professeur.getId().equals(idProf)) {
//    			System.out.println("idPro == id dans list prof");
//    			professeurs.remove(professeur);
//    			deleteTeacherFromFile(professeur, "../DATA/Professeurs.txt");
//    		}
//    	}
//    }
    
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

    
    private void deleteTeacherFromFile(Professeur professeur, String filename) {
        try {
            File inputFile = new File(filename);
            File tempFile = new File("../DATA/tempFile.txt");

            try (Scanner scanner = new Scanner(inputFile);
                 FileWriter writer = new FileWriter(tempFile)) {

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
    
    private void deleteLearnerFromFile(Apprenant apprenant, String filename) {
    	try {
    		File inputFile = new File(filename);
    		File tempFile = new File("../DATA/tempFile.txt");
    		
    		try (Scanner scanner = new Scanner(inputFile);
    				FileWriter writer = new FileWriter(tempFile)) {
    			
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				String[] parts = line.split(";");
    				if (parts.length == 5) {
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
    
    public Map<Langue, BaremeNiveau> NiveauLangueFromString(String input) {
//      String input = "{FR=DEBUTANT}";

      // Supprime les caractères { et } pour obtenir une chaîne JSON valide
      String jsonString = input.replaceAll("[{}]", "");

      // Divise la chaîne en paires clé-valeur
      String[] keyValuePairs = jsonString.split(",");
      
      // Crée la HashMap résultante
      Map<Langue, BaremeNiveau> resultMap = new HashMap<>();

      for (String pair : keyValuePairs) {
          // Divise chaque paire en clé et valeur
          String[] entry = pair.split("=");

          // Obtient la Langue à partir de la clé
          Langue langue = Langue.fromString(entry[0]);

          // Obtient le BaremeNiveau à partir de la valeur
          BaremeNiveau niveau = BaremeNiveau.fromString(entry[1]);

          // Ajoute la paire à la HashMap
          resultMap.put(langue, niveau);
      }
      return resultMap;
	}

}
