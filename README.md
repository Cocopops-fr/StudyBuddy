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
 - H2 Database : pour éviter d'avoir full mysql à gérer dès le début + très léger (2mb de ram)
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




