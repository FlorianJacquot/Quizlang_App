# Application Quizlang
Projet d'application dans le cadre du cours de Programmation Objet du Master TAL INALCO de l'année 2023/2024.

## Table des Matières

- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Captures d'écran](#captures-décran)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Contribuer](#contribuer)
- [Licence](#licence)

## Aperçu

Quizland est une application qui cherche à répondre aux besoins suivants :
- les utilisateurs peuvent s'inscrire à l'application en temps qu'apprenant ou professeur
- les utilisateurs peuvent se connecter à l'application via un identifiant et un mot de passe
- les apprenants peuvent faire des exercices de langues selon leur niveau
- les exercices sont corrigés et notés automatiquement par le système
- en fonction de la note reçue par l'apprenant, son niveau peut évoluer
- les professeurs peuvent ajouter leurs propres exercices
- les utilisateurs peuvent supprimer leur compte

## Fonctionnalités

### Les utilisateurs

Toutes les données utilisateur sont conservées dans des fichiers `.txt` contenus dans le dossier `DATA/`.

#### Les professeurs

Lors de la création d'un compte professeur, l'utilisateur doit fournir un identifiant, un mot de passe, un nom, un prénom et préciser quelle langue il veut enseigner.

Une fois connecté, un professeur peut :
1. créer un nouvel exercice : une fenêtre s'ouvre pour sélectionner son ficher exercice et l'importer dans le dossier `EXO/` de l'application ;
2. voir les exercices enregistrés dans l'application : il voit une vue simplifiée (les deux première phrases) de chaque exercice de sa langue d'enseignement ;
3. supprimer son compte ;
4. se déconnecter


#### Les apprenants

Lors de la création d'un compte apprenant, l'utilisateur doit fournir un identifiant, un mot de passe, un nom, un prénom, préciser quelle langue il veut apprendre et à quel niveau (DEBUTANT, INTERMEDIAIRE ou AVANCE).

Une fois connecté, un apprenant peut :
1. faire un exercice :
    1. Une vue simplifiée de chaque exercice accessible à l'apprenant  s'affiche dans des boutons. Seuls les exercices à niveau inférieur ou égale à celui de l'apprenant s'affichent. Par exemple, un apprenant du japonais niveau INTERMEDIAIRE verra s'afficher les exercices japonais de niveaux DEBUTANT et INTERMEDIAIRE mais pas les exercices AVANCE.  
    2. Il suffit à l'apprenant de cliquer sur le bouton de l'exercice qu'il souhaite réaliser.
    3. L'apprenant doit ensuite remplir les cases correspondantes de l'exercices et soumettre ses réponses.
    4. Après soumission, l'application corrige automatiquement les réponses de l'apprenant et lui donne une note. Si celle-ci est suppérieure à la note minimale attendue par le professeur alors l'exercice est réussi et l'apprenant augmente son score d'apprentissage. Le niveau de l'apprenant dépend de ce score.
2. supprimer son compte ;
3. se déconnecter

## Les règles de gestion

### Le passage de niveau

Il y a trois niveaux : DEBUTANT, INTERMEDIAIRE, AVANCE. Ces niveaux sont associés à des plages de scores d'apprentissages :
- DEBUTANT : moins de 5
- INTERMEDIAIRE : entre 5 et 10
- AVANCE : plus de 10

Le passage d'un niveau à l'autre se fait en fonction du score. L'amélioration et la régression sont possibles.

Pour que le score augmente, il faut que l'élève fasse un exercice et que l'exercice soit réussi. Un exercice est réussi si la note obtenue dépasse la note seuil fixée avec les métadonnées fournies par le professeur lors de la création de l'exercice. En effet, quand le prof crée un exercice, il doit fournir le pourcentage de points qu'il faut obtenir pour que l'exercice soit considéré comme réussi. La note seuil est calculée à partir de ce pourcentage et du nombre de réponses à fournir en tout.

Par exemple, la métadonnées `FR:DEBUTANT:0.5` informe le systeme que l'exercice est pour la langue française, le niveau DEBUTANT et qu'il faut que l'apprenant ait au moins 50% des points totaux de l'exercice. 

### Notation

Selon le niveau de l'apprenant, l'évaluation change (cf. classe `BaremeNiveau`) :
- DEBUTANT :
    - +1 par bonne réponse
    - 0 par mauvaise réponse
    - 0 par réponse non-répondue

- INTERMEDIAIRE
    - +1 par bonne réponse
    - -1 par mauvaise réponse
    - 0 par réponse non-répondue

- AVANCE
    - +1 par bonne réponse
    - -1 par mauvaise réponse
    - -1 par réponse non-répondue

### Correction

La correction est automatique. Elle se fait via la classe `ReponseApprenant` et son attribut `corrige()` qui attribue une `ValeurReponse` à chaque réponse. Celle-ci peut être VRAI, FAUX ou NR (non-répondue). Ces valeur sont liées à la notation présentée ci-dessus. 

## Installation

Pour lancer l'application :
```bash
git clone 
cd build/release
java -jar Application_java.jar
```


[Expliquez ici comment installer et configurer votre projet. Assurez-vous de mentionner les dépendances nécessaires.]

```bash
# Exemple d'instructions d'installation
git clone https://github.com/votre-utilisateur/votre-projet.git
cd votre-projet
npm install
