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

- [Features](#features)
- [Quick Start](#quick-start)
    - [Requirement :](#requirement-)
    - [Démarrage de l'application](#demarrage-de-lapplication)

## Features

- Projet construit sur une base Spring boot.
- Stockage dse données basé sur la base in-memory H2.
- Architectue de code basé sur le concept DDD.
- Fonctionnalités d'API Restful.
- Fonctionnalités permettant la publication et la consommation de message inter et intra micro-service (basé sur Redis).
- Des exemples de tests unitaires / tests d'intégration avec création de container à la volée pour des tests de bout en bout, y compris sur les composants nécéssitant des dépendances (ex : Redis)
- Configuration de base pour lancer l'application en standalone sur un docker (en incluant ses dépendances redis / traefik)
- Configuration des GitHub Actions (Pipeline CI) de permettant de jouer les builds et de générer les rapports à l'aide de l'outil SonarCloud.io

## Quick Start

### Requirement :

- Docker
- JDK 19

### Démarrage de l'application

Le lancement de l'application en local nécessite simplement la génération du package .jar et le lancement du docker compose : 

```shell
mvn clean package
docker compose up -d --build
```