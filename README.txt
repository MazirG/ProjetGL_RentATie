Description des dossiers du projet et instructions pour le lancement de l'application :

Dans le dossier Fichiers :
    - Javadoc de l'application nommée "RentATieJavadoc", pour l'ouvrir il faut ouvrir le fichier index.html .
    - Un compte rendu des tests JUnit réalisés.
    - Le rapport du projet.

Dans le dossier UML :
    - Diagrammes réalisés avec starUML au début du projet (fichiers en .mdj).
    - Diagrammes de classes haut niveau et bas niveau (en .png) générés grâce au plugin "Diagrams" d'IntelliJ.

Pour lancer les tests :
    - Utiliser JUnit 5.

Pour lancer l'application :
    - Intégrer la librairie mysql-connector-java-8.0.28 (le dossier compréssé se trouve dans le dossier RentATie).
    - Lancer la class Main du package main situé ici : RentATie/src/main.
    - La partie "Application finale" du rapport peut servir de guide pour l'application.
    - Pour se connecter en tant qu'officer : username = Pseudo1
                                                 mdp  = 123

    - Pour se connecter en tant que pilote : username = Pilote1
                                                 mdp  = 321

Remarques :
    - Le package bCrypt utilisé pour le cryptage des mots de passe à été récupéré sur internet (la source se trouve dans les commentaires de la classe BCrypt).
    - Dans le dossier RentATie/ressources se trouve un fichier conf.properties qui contient les logs pour se connecter à la base de donnée (la connexion est automatique dans l'application).
