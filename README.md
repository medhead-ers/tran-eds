<div align="center">
<img  width="75" src="project-icon.png" />
<br>
<br>
<h1>TRAN EDS - Emergency Dispatching System</h1>
</div>

<br>
<br>

Ce dépot contient le micro-service transverse dédié au dispatching des urgences en fonction de leurs localisations et de leur spécialité.


# Table of contents
- [Quick Start](#quick-start)
  - [Requirement](#requirement)
  - [Démarrage de l'application](#dmarrage-de-lapplication)
  - [Tests automatisés](#tests-automatiss)
- [GitHub Actions CI/CD Pipelines](#github-actions-cicd-pipelines)
- [Workflow GIT](#workflow-git)

## Quick Start

### Requirement

- Docker (service démarré)
- JDK 19

### Démarrage de l'application

Le lancement de l'application en local nécessite simplement la génération du package .jar et le lancement du docker compose :

```shell
mvn clean package
docker compose up -d --build
```

### Tests automatisés

Il est possible d'exécuter les tests automatisés à l'aide d'une simple commande maven :
> La commande "package" de maven lance par default les différents tests automatisés.

```shell
mvn clean test
```

Il est à noter que pour certain test, le service docker doit être démarré sur la machine afin de pouvoir créer les conteneurs à la volée. Plus d'info :  https://www.testcontainers.org/features/creating_container/.


## GitHub Actions CI/CD Pipelines

La configuration des pipelines CI/CD peut être retrouvée dans le dossier `.github/workflows`.
Les pipelines sont construits à partir de modèles pour favoriser leur réutilisation dans les différents services. Plus d'info : https://github.com/medhead-ers/ci-cd-templates

| Pipeline          | Déclencheur                                             | Description                                                                                                                                                                          |
|-------------------|---------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Build and Test    | push (*)                                                | Build l'application à l'aide de maven et déclenche les tests automatisés (mvn package). Si les tests ne sont pas tous valide, la pipeline déclenchera une erreur (+ mail d'alerte).  |
| Static Analysis   | push (develop ou main) pull request (created + updated) | Déclenche les tests automatisés avec le profil *coverage*. Les résultats seront transmis à la plateforme SonarCloud pour analyse.                                                    |
| Package on Github | release (created)                                       | Créé un artefact `.jar` avec le tag de version indiqué pour la release.                                                                                                              |
| Publish to Docker | release (published)                                     | Créé et publie sur le compte docker medhead ERS l'image docker associé à la release (embarque le `.jar` prêt à l'emploi).                                                            |


## Workflow GIT

Le workflow git "[Git-flow](https://git-flow.readthedocs.io/fr/latest/presentation.html)" a été retenu pour le développement. Le code ayant été produit par un unique développeur, son utilisation a été simplifiée pour les développements liés à la PoC.

Il est possible de résumer le workflow de la manière suivante :

- Une branche `develop`
- Une branche `main`
- Pas de commit direct sur `develop` ni sur `main`
- Création d'une branche `features/{nom_feature}` à partir de `develop` pour chaque fonctionnalité.
- Une fois la fonctionnalité prête (et testée), ouverture d'une `pull request` vers `develop`.
- Après analyse des métriques qualité, code review etc... : merge de la `pull request` (squash recommandé pour garder 1 commit / feature).
- Une fois la version basée sur `develop` acceptée, ouverture d'une `pull request` vers `main`.
- Création d'une release à partir de `main` une fois la version acceptée.