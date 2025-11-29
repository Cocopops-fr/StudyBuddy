# StudyBuddy – état des lieux
Tous les codes de chacun ont été complètement repris, sans changemet dans la logique. Seule la structure, la dépendance, l'organisation en projet, les bases de données... bref tout ce qu'il faut pour qu'on puisse faire 1: des micro services qui communiquent (pas le navigateur qui change de lien), 2: un modèle d'acteurs (dans un second temps).

## Vue d’ensemble
- Trois applications Spring Boot distinctes :
  - `interactions-service` (port 8082)
  - `user-service` (port 8081) :e embarque sa propre base H2 en mémoire et son initialisation de données = plus de mysql dont la config change sur nos différents ordis

  - `webapp` (port 8080) 



## Responsabilités par service
- **user-service** : stockage et exposition des profils (CRUD, recherche par compétence) ; gère l’authentification côté utilisateurs.
- **interaction-service** : enregistre likes/dislikes et calcule les matches entre utilisateurs.
- **webapp** : front-end sans persistance qui consomme les deux services via `RestTemplate` ou `WebClient`.

## API REST (cibles) et contrats minimalistes
Prendre en compte le code existant : certains contrôleurs répondent déjà sous `/api/**` (par exemple `user-service` expose actuellement `/api/users`). Les chemins ci-dessous décrivent la cible simple à stabiliser.

### user-service
- **GET `/users`** : liste paginable des profils.
- **GET `/users/{id}`** : détail d’un profil.
- **POST `/users`** : création d’un profil.
- **PUT `/users/{id}`** : mise à jour d’un profil.
- **DELETE `/users/{id}`** : suppression.
- **GET `/users/search?skill=...`** : recherche par compétence.

Contrat minimaliste pour un profil utilisateur (requête/réponse) :
```json
{
  "id": "42",
  "name": "Ada Lovelace",
  "skills": ["java", "spring"],
  "bio": "Développeuse full-stack",
  "university": "Université de Lyon"
}
```

> Implémentation actuelle : `UserRestController` répond sur `/api/users` avec `GET /api/users` et `GET /api/users/{id}` en exposant un DTO `UserProfileDto` minimal.

### interaction-service
- **POST `/interactions/like`** : enregistre un like. Payload JSON :
  ```json
  { "likerId": "123", "likedId": "456" }
  ```
- **GET `/interactions/matches?userId=...`** : retourne les matches du `userId` fourni.

Réponse type pour un match :
```json
[
  {
    "id": "456",
    "name": "Grace Hopper",
    "skills": ["cloud", "kubernetes"],
    "bio": "Site Reliability Engineer"
  }
]
```

> Implémentation actuelle : `InteractionRestController` écoute sous `/api/users/{sourceUserId}/like/{targetUserId}`, `/api/users/{userId}/likes` et `/api/users/{userId}/matches`.

### webapp
- Ne stocke rien en local.
- Consomme `user-service` et `interaction-service` pour afficher les profils, orchestrer les likes/matches et servir les vues Thymeleaf.

  ###########################################

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




