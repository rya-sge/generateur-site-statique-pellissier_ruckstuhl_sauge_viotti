# Statique
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv)](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/releases/latest)
[![GitHub repo size](https://img.shields.io/github/repo-size/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv)](#)
[![GitHub issues](https://img.shields.io/github/issues-raw/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv)](https://github.com/david-pellissier/RES_PrankBot/issues)
[![GitHub all releases](https://img.shields.io/github/downloads/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/total)](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/releases/latest)

[![Java CI with Maven](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/actions/workflows/maven.yml/badge.svg)](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/actions/workflows/maven.yml)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/actions/workflows/CodeCoverage.yml)

Générateur de site statique léger.

Développé dans le cadre du cours de Génie Logiciel à la HEIG-VD. Par le groupe PSRV constitué de : David Pellissier, Michael Ruckstuhl, Ryan Sauge et Nicolas Viotti.

## Table des matières

- [Statique](#statique)
  - [Table des matières](#table-des-matières)
  - [Installation](#installation)
    - [Prérequis](#prérequis)
    - [Depuis la dernière release](#depuis-la-dernière-release)
    - [Linux & MacOS](#linux-&-macos)
    - [Windows](#windows)
    - [Installer à partir du le code source](#installer-à-partir-du-code-source)
  - [Utilisation](#utilisation)
    - [Initialiser un nouveau site statique](#initialiser-un-nouveau-site-statique)
    - [Ajouter une page](#ajouter-une-page)
    - [Compiler le site](#compiler-le-site)
    - [Nettoyer les fichiers de build](#nettoyer-les-fichiers-de-build)

## Installation

### Prérequis

- Java JDK 11
- Maven pour compiler depuis le code source
  - pas nécessaire si vous utilisez la version déjà compilée (statique-x.x.x.zip)

En utilisant ce logiciel, vous acceptez les [conditions d'utilisation](LICENSE.md).

### Depuis la dernière release

Télécharger et dézipper la [dernière version](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/releases/latest) du programme.

### Linux & MacOS

Pour pouvoir utiliser le programme depuis n'importe où dans le terminal, vous pouvez créer un lien symbolique dans */usr/bin*:

```bash
cd statique
ln -s bin/statique.sh /usr/bin/statique
```

### Windows

Ajouter dans la variable d'environnement *PATH* le chemin vers *statique/bin/statique*

[Tutoriel si vous ne savez pas comment le faire](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/)

## Installer à partir du code source

Cloner le repo ou télécharger et dézipper le code source de la [dernière version](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/releases/latest) du programme.

Générez ensuite le package avec maven : `mvn package`

Désarchivez le fichier *target/statique.zip* et suivez ensuite la [procédure normale](#Installation).

## Utilisation

### Initialiser un nouveau site statique

```bash
statique init <root>
```

Le programme demandera alors à l'utilisateur les informations de config

### Ajouter une page

Il suffit de créer un fichier Markdown (ayant l'extension *.md*) dans la racine ou un répertoire du site statique.

Il est possible de modifier les templates en éditant les fichiers .html dans le dossier *template*

### Compiler le site

```bash
statique build <root>
```

Le programme créera transformera toutes les pages *.md* en HTML. Le résultat est stocké dans le dossier "build".

Si la racine contient déjà un dossier build, l'ancien sera écrasé par le nouveau build.

### Nettoyer les fichiers de build

```bash
statique clean <root>
```

Le répertoire build est effacé.
