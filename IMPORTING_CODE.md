# Importer les microservices existants dans ce dépôt

Objectif : réutiliser au maximum le code des microservices d'un autre dépôt (non accessible publiquement) en l'intégrant dans l'arborescence actuelle `user-service/`, `interactions-service/`, `webapp/` sans optimisation préalable.

## Préparation
- Cloner le dépôt actuel (`StudyBuddy`) et le dépôt source dans des dossiers frères pour pouvoir copier/commiter facilement.
- Vérifier la version de Java et de Spring Boot utilisée dans le code source; si elle diffère de la configuration actuelle (Java 17), ajuster dans les `pom.xml` après import.

## Stratégie d'import (sans exposer le dépôt privé)
1. **Copie locale manuelle** : ouvrir le dépôt source en local, copier le contenu du microservice voulu vers le dossier correspondant ici (ex. copier l'ancien `users` dans `user-service/`).
2. **Préserver l'arborescence** : conserver les packages tels quels; si le package racine n'est pas `com.studybuddy.*`, décider si un renommage est nécessaire pour la cohérence (sinon le laisser tel quel pour l'instant, tant que les classes Spring Boot sont trouvées).
3. **Garder les POM d'origine** : coller le `pom.xml` du service importé et ajouter/ajuster uniquement ce qui est nécessaire pour que Maven résolve les dépendances et que les ports/URLs restent conformes aux propriétés déjà définies.
4. **Ne pas importer les dossiers de build** (`target/`, `.settings/`, `.idea/`, `*.iml`).

## Points de vigilance par microservice
### user-service
- Classe principale : vérifier la présence d'une classe `*Application` dans le package racine et qu'elle porte `@SpringBootApplication`.
- Port et DB : conserver `server.port=8081` et le bloc H2 ou MySQL dans `src/main/resources/application.properties`.
- Entités/Repositories : placer les entités JPA dans `model/` et les `JpaRepository` dans `repository/`. Si d'autres packages sont utilisés, les garder tels quels mais vérifier que l'annotation `@EntityScan` ou le `scanBasePackages` sont cohérents.

### interactions-service
- Port cible : `server.port=8082`.
- Appel vers users : propriété `users.service.url=http://localhost:8081` doit être consommée (Feign/RestTemplate). Adapter le client HTTP si le code source utilise un autre nom de propriété.
- Modèle : si le service persiste des données, vérifier que les entités sont scannées depuis le package racine et que la configuration JPA correspond au stockage choisi (H2 pour démarrage rapide).

### webapp
- Port cible : `server.port=8080`.
- Clients backend : rebrancher les appels vers les propriétés `users.service.url` et `interactions.service.url`. Si le code source utilise d'autres noms, soit les renommer dans le code, soit ajouter des propriétés supplémentaires dans `application.properties` pour la compatibilité.
- Vues : placer les templates dans `src/main/resources/templates/` et les assets dans `src/main/resources/static/`.

## Méthode d'import pas à pas (exemple user-service)
1. Supprimer le contenu actuel de `user-service/src` si nécessaire (mais garder `application.properties` pour référence des ports/propriétés). 
2. Copier `src/` et `pom.xml` du service d'origine dans `user-service/`.
3. Comparer le `pom.xml` avec la version actuelle :
   - s'assurer que `spring-boot-starter-web` et `spring-boot-starter-data-jpa` sont présents;
   - ajouter la dépendance H2 si besoin pour les tests locaux.
4. Ajuster `application.properties` :
   - conserver `server.port=8081`;
   - si la datasource est différente, commenter temporairement MySQL et décommenter H2 pour un démarrage rapide;
   - vérifier `spring.jpa.hibernate.ddl-auto` (ex. `update` ou `create-drop` selon le besoin initial).
5. Lancer `./mvnw spring-boot:run` dans `user-service/` pour vérifier que le service démarre. Corriger les packages scannés si nécessaire.

Répéter la même approche pour `interactions-service` et `webapp`, en réutilisant leurs propriétés respectives et en vérifiant que les dépendances du code importé correspondent aux starters déjà utilisés ici.

## Gestion Git et collaboration (Eclipse / IntelliJ)
- Commits : regrouper les imports par microservice pour faciliter la revue (un commit par service importé est idéal).
- Si vous souhaitez conserver l'historique du dépôt source sans l'exposer, utilisez une **archive** (`git archive`) du dépôt source plutôt qu'un merge direct; cela évite d'ajouter la remote privée.
- Les utilisateurs IntelliJ pourront ouvrir chaque sous-dossier comme projet Maven sans configuration supplémentaire.

## Checklist rapide après import
- [ ] Les classes `*Application` sont présentes et dans un package scanné.
- [ ] Les ports 8080/8081/8082 sont respectés.
- [ ] Les propriétés d'URL (`users.service.url`, `interactions.service.url`) sont cohérentes avec les clients HTTP.
- [ ] Les dépendances Maven sont résolues (`./mvnw -q dependency:resolve`).
- [ ] Lancement réussi de chaque service en local (au moins avec H2).
