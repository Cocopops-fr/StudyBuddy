# StudyBuddy – état des lieux

## Vue d’ensemble
- Trois applications Spring Boot distinctes :
  - `interactions-service` (port 8082)
  - `user-service` (port 8081)
  - `webapp` (port 8080)
- chaque service embarque sa propre base H2 en mémoire et son initialisation de données = plus de mysql dont la config change sur nos différents ordis

## Couplage actuel
- **Pas d’appels inter-services** :
  - la webapp se contente d’exposer des liens HTML vers `http://localhost:8081` et `http://localhost:8082`. Elle ne proxifie ni n’orchestrationne leurs API REST
  - `interactions-service` ne consomme pas les données du `user-service`. Les profils utilisés pour les likes/matches sont créés localement au démarrage (`config/DataInit.java`).
  - `user-service` gère authentification/CRUD utilisateur sans publier d’intégration vers les autres services

## Conséquence fonctionnelle
Le système se comporte comme trois applications indépendantes accessibles dans le navigateur via des URL différentes. Il manque une couche de communication (REST client/Feign/WebClient, API Gateway, etc.) pour parler de véritable architecture microservices.


##############################################################################

# StudyBuddy

les 3 micro-services sont créés avec le même 'Spring Starter Project', avec un nom de package cohérent à travers les 3 projets : com.studybuddy

Java : 17 ; Springboot : 4.0.0


Les dépendances communes: 
 - Spring Web
 - Spring Data JPA


## User-service
 microservice pour gérer les données utilisateurs
 
**com.studybuddy.users**

 ### Structure :
- *model/* pour les entités JPA.
- *repository/* pour les interfaces JpaRepository.
- *controller/* pour les endpoints REST.
- *service/* pour la logique métier.

### Dépendances propres :
 - H2 Database : pour éviter d'avoir full mysql à gérer dès le début + très léger (2mb de ram) = valider le wiring Spring/JPA sans dépendance externe
  - Spring Boot DevTools : pour le hot reload


### Paramètres :
- server.port=8082


## Interactions-service
 microservice pour gérer le matching
 
**com.studybuddy.interactions**

 ### Structure : - idem par défaut.
- *model/* pour les entités JPA.
- *repository/* pour les interfaces JpaRepository.
- *controller/* pour les endpoints REST.
- *service/* pour la logique métier.

### Dépendances propres :
 - H2 Database : pour éviter d'avoir full mysql à gérer dès le début + très léger (2mb de ram)


### Paramètres :
- server.port=8081








## Webapp (Thymemeaf)
 microservice pour gérer l'affichage, page web, etc.
 
**com.studybuddy.webapp**

 ### Structure :
- *controller/* pour les controlleurs MVC
- *service/* pour les appels aux autres microservices.

Dans src/main/ressources  :
- *static/* 
- *templates/*  pour les vues et assets


### Dépendances propres :
  - Spring Boot DevTools : pour le hot reload
 - Spring Security : optionnel, mais pour ajouter de l'authentififcation + tard



### Paramètres :
- server.port=8080




