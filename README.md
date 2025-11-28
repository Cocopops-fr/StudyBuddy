# StudyBuddy

Mini plateforme type "Study buddy" composée de trois microservices Spring Boot :
- **user-service** : gestion des données utilisateurs.
- **interactions-service** : matching / interactions entre profils.
- **webapp** : front-end Thymeleaf consommant les deux services backend.

## Préparation rapide avant de coder
- Java 17 et Maven sont requis (`mvn -v`).
- Ports réservés : **8081** (user-service), **8082** (interactions-service), **8080** (webapp). Les valeurs sont déjà définies dans chaque `application.properties`.
- URLs à connaître :
  - `user-service` est appelé via `http://localhost:8081`.
  - `interactions-service` est appelé via `http://localhost:8082`.
  - `webapp` consomme les deux services via `webapp/src/main/resources/application.properties`.
- Base de données : H2 est déjà déclaré dans les POM. Des paramètres commentés sont fournis dans chaque `application.properties` pour activer rapidement une base en mémoire et la console H2.
- Git : les fichiers d’IDE et les dossiers de build sont ignorés (`.gitignore`).

## Dépendances communes
- Spring Web
- Spring Data JPA

## User-service
Microservice pour gérer les données utilisateurs (`com.studybuddy.users`).

### Structure
- `model/` pour les entités JPA.
- `repository/` pour les interfaces `JpaRepository`.
- `controller/` pour les endpoints REST.
- `service/` pour la logique métier.

### Dépendances propres
- H2 Database (démarrage rapide sans SGBD externe).
- Spring Boot DevTools (hot reload).

### Paramètres
- `server.port=8081` dans `src/main/resources/application.properties`.
- Bloc H2 commenté prêt à être activé pour les tests.

## Interactions-service
Microservice pour gérer le matching (`com.studybuddy.interactions`).

### Structure
- `model/` pour les entités JPA (si persistance nécessaire).
- `repository/` pour les interfaces `JpaRepository`.
- `controller/` pour les endpoints REST.
- `service/` pour la logique métier.

### Dépendances propres
- H2 Database (optionnel si l’algorithme a besoin d’un stockage local).

### Paramètres
- `server.port=8082` dans `src/main/resources/application.properties`.
- Propriété `users.service.url=http://localhost:8081` pour appeler le service utilisateurs.
- Bloc H2 commenté prêt à être activé pour les tests.

## Webapp (Thymeleaf)
Microservice pour l’affichage web (`com.studybuddy.webapp`).

### Structure
- `controller/` pour les contrôleurs MVC.
- `service/` pour les appels aux autres microservices.
- `src/main/resources/templates/` pour les vues.
- `src/main/resources/static/` pour les assets.

### Dépendances propres
- Spring Boot DevTools (hot reload).
- Spring Security (optionnel pour l’authentification ultérieure).

### Paramètres
- `server.port=8080` dans `src/main/resources/application.properties`.
- Propriétés de consommation backend :
  - `users.service.url=http://localhost:8081`
  - `interactions.service.url=http://localhost:8082`

## Lancer les services
Dans chaque sous-dossier, exécuter :
```
./mvnw spring-boot:run
```
Vérifier que les ports ne sont pas déjà utilisés avant de démarrer plusieurs services en parallèle.

## Importer du code existant
Pour réintégrer rapidement les microservices depuis un autre dépôt (sans exposer ce dépôt privé), suivez le guide détaillé dans [IMPORTING_CODE.md](./IMPORTING_CODE.md) : stratégies de copie, points de vigilance par service et checklist post-import.
