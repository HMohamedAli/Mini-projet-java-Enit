# Projet de Matching de Noms

## Description

Ce projet Java est un moteur de matching de noms qui compare deux listes de personnes ou d'entités et identifie des paires similaires. Il est conçu pour illustrer une chaîne de traitement modulaire comprenant :

- l'import de fichiers CSV,
- le prétraitement des noms,
- la génération de candidats,
- la comparaison de chaînes de caractères,
- la sélection des meilleurs résultats.

Le projet est structuré autour d'un moteur central `MoteurDeMatching` et d'une configuration interactive via la console.

## Fonctionnalités principales

- Chargement d'une liste source et d'une liste cible depuis des fichiers CSV.
- Détection automatique d'un en-tête `id`, `nom`, `name` ou `prenom`.
- Prétraitements disponibles :
  - mise en majuscules,
  - suppression des accents,
  - suppression des espaces,
  - remplacement des caractères spéciaux,
  - tokenisation par espace.
- Générateurs de candidats disponibles :
  - produit cartésien,
  - taille proche par nombre de caractères,
  - taille proche par pourcentage,
  - décomposition par tokens,
  - indexeur de tokens,
  - permutation optimale.
- Comparateurs disponibles :
  - comparaison exacte,
  - distance de Levenshtein,
  - comparaison partielle,
  - comparaison par tokens,
  - Soundex phonétique,
  - Jaro-Winkler,
  - ordre des tokens.
- Sélection des résultats basée sur :
  - un seuil minimum de score,
  - les premiers N résultats.
- Affichage console des paires retenues avec leurs scores.

## Structure du projet

- `src/main` : classes principales du moteur, configuration, objets de domaine et interface console.
- `src/pretraitement` : classes de prétraitement des noms.
- `src/generateur` : classes de génération des candidats.
- `src/comparaison` : classes et interfaces de comparaison des noms.
- `src/selectionneur` : classes de sélection des résultats.
- `listsource/` et `listecible/` : dossiers créés automatiquement pour stocker les listes chargées.

## Installation et compilation

Depuis le répertoire racine du projet (`projet java`), utilisez :

```bash
javac -d bin src/main/*.java src/pretraitement/*.java src/generateur/*.java src/comparaison/*.java src/selectionneur/*.java
```

Puis lancez l'application avec :

```bash
java -cp bin main.Main
```

## Utilisation

1. Démarrez l'application.
2. Choisissez `1. Charger une liste` pour importer un fichier CSV source ou cible.
3. Choisissez `2. Chercher un nom` pour sélectionner les listes préalablement importées.
4. Configurez les prétraitements, le générateur de candidats, l'algorithme de comparaison et le sélectionneur.
5. Visualisez les résultats dans la console.

### Format CSV attendu

- Le fichier peut être séparé par `;` ou `,`.
- La première colonne correspond à l'ID.
- La deuxième colonne contient le nom (si absente, la première colonne est utilisée comme nom).
- Un en-tête est autorisé et reconnu automatiquement si la première ligne contient `id`, `nom`, `name` ou `prenom`.

Exemple :

```csv
id;nom
1;Dupont Jean
2;Doe John
```

## Extension et personnalisation

Le projet est conçu pour être facilement étendu :

- Ajouter de nouveaux prétraitements en implémentant `pretraitement.PretraiteurNom`.
- Ajouter de nouveaux générateurs en implémentant `generateur.GenerateurDeCandidats`.
- Ajouter de nouveaux comparateurs en implémentant `comparaison.ComparateurDeNoms`.
- Ajouter de nouveaux sélectionneurs en implémentant `selectionneur.SelectionneurDeResultat`.

## Pistes d'amélioration

- Ajouter une interface graphique (JavaFX ou Swing) ou une API REST.
- Ajouter la prise en charge de formats supplémentaires (JSON, XML).
- Introduire des tests automatiques (JUnit).
- Améliorer la détection et la gestion des erreurs CSV.
- Ajouter un mode batch pour traiter plusieurs fichiers.
- Permettre la configuration via un fichier externe (`properties`, JSON, YAML).
- Enrichir les algorithmes de matching avec des techniques plus avancées (n-grammes, embeddings, apprentissage automatique).

## Auteurs

- Wissem Toujani
- Hedy Ben Hamadou
- Omar Karoui
- Mohamed Ali Hachicha

## Licence

Ce projet est fourni sans licence explicite. Ajoutez-en une si nécessaire pour clarifier les droits d'utilisation et de partage.
