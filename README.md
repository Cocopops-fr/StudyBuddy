# StudyBuddy

les 3 micro-services sont créés avec le même 'Spring Starter Project', avec un nom de package cohérent à travers les 3 projets : com.studybuddy

Les dépendances communes: 
 - Spring Web
 - Spring Data JPA
 - Spring Boot DevTools : pour le hot reload

## User-service
 microservice pour gérer les données utilisateurs
 
 com.studybuddy.users
 
 ### Structure :
- model/ pour les entités JPA.

- repository/ pour les interfaces JpaRepository.

- controller/ pour les endpoints REST.

- service/ pour la logique métier.

 #### Dépendances propres :
 - H2 Database : pour éviter d'avoir full mysql à gérer dès le début


#### Paramètres :
- server.port=8081


