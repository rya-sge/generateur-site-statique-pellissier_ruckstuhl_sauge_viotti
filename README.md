# Statique

[![Java CI with Maven](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/actions/workflows/maven.yml/badge.svg)](https://github.com/gen-classroom/projet-pellissier_ruckstuhl_sauge_viotti-prsv/actions/workflows/maven.yml)

Générateur de site statique léger. 

Développé dans le cadre du cours de Génie Logiciel à la HEIG-VD. Par le groupe PSRV constitué de : David Pellissier, Michael Ruckstuhl, Ryan Sauge et Nicolas Viotti.

## Table des matières

- [Statique](#statique)
  - [Table des matières](#table-des-matières)
  - [Installation](#installation)
    - [Prérequis](#prérequis)
    - [Depuis la dernière release](#depuis-la-dernière-release)
    - [Build depuis le code source](#build-depuis-le-code-source)
    - [Linux & MacOS](#linux-&-macos)
    - [Windows](#windows)
    - [Installer à partir du le code source](#installer-à-partir-du-le-code-source)
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

En utilisant ce logiciel, vous acceptez les [conditions d'utilisation](LICENSE).

### Depuis la dernière release

Télécharger et dézipper la [dernière version](releases/latest) du programme.

### Build depuis le code source

Télécharger et dézipper le code source de la [dernière version](releases/latest) du programme.

```bash
mvn clean install
unzip -o target/statique.zip
```

### Linux & MacOS

Pour pouvoir utiliser le programme depuis n'importe où dans le terminal, vous pouvez créer un lien symbolique dans */usr/bin*:

```bash
cd statique
ln -s bin/statique.sh /usr/bin/statique
```

### Windows

Ajouter dans la variable d'environnement *PATH* le chemin vers *statique/bin/statique*

[Tutoriel si vous ne savez pas comment le faire](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/)

## Installer à partir du le code source

Télécharger et dézipper le code source de la [dernière version](releases/latest) du programme.

Générez ensuite le package avec maven: `mvn package`

Désarchivez le fichier *target/statique.zip* et suivez ensuite la [procédure normale](#Installation).

## Utilisation

### Initialiser un nouveau site statique

```bash
statique init <root>
```

Le programme demandera alors à l'utilisateur les informations de config

### Ajouter une page

Il suffit de créer un fichier Markdown (ayant l'extension *.md*) dans la racine ou un répertoire du site statique.

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

