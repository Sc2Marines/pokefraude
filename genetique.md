### Analyse des Caractéristiques

1. **Terrain** :
   - État du terrain (2 états possibles : NORMAL, INONDE)
   - Nombre de tours avant que le terrain redevienne normal

2. **Joueur** :
   - Liste de 2 objets (chaque objet a un type : POTION, MEDICAMENT)
   - Monstre actuellement sélectionné (11 caractéristiques)
   - Liste de monstres disponibles (chaque monstre a 11 caractéristiques)

3. **Objet** :
   - Type de l'objet (2 types possibles : POTION, MEDICAMENT)

4. **Monstre** :
   - Liste d'attaques (chaque attaque a 4 caractéristiques)
   - Points de vie
   - Type du monstre (8 types possibles)
   - Points de dégâts
   - Points de défense
   - Points de vitesse
   - Points de chance d'inonder le terrain
   - Points de tomber quand le terrain est inondé
   - Boolean si le monstre est paralysé
   - Boolean si le monstre est empoisonné
   - Boolean si le monstre est brûlé

5. **Attaque** :
   - Type de l'attaque (8 types possibles)
   - Puissance
   - Nombre d'utilisations (nbUse)
   - Pourcentage de chance que l'attaque échoue (fail)

### Détermination des Poids

Pour chaque tour de jeu, l'IA doit prendre une décision basée sur les caractéristiques actuelles du terrain, du joueur, et de l'adversaire. Nous devons donc assigner des poids à chaque caractéristique pertinente pour influencer cette décision.

#### Poids pour le Terrain

- **État du terrain** : 1 poids
- **Nombre de tours avant que le terrain redevienne normal** : 1 poids

#### Poids pour le Joueur

- **Objets** : 2 objets * 1 type = 2 poids
- **Monstre actuel** : 11 caractéristiques = 11 poids
- **Monstres disponibles** : 2 monstres * 11 caractéristiques = 22 poids

#### Poids pour l'Adversaire

- **Objets** : 2 objets * 1 type = 2 poids
- **Monstre actuel** : 11 caractéristiques = 11 poids
- **Monstres disponibles** : 2 monstres * 11 caractéristiques = 22 poids

#### Poids pour les Attaques

- **Attaques du monstre actuel** : 3 attaques * 4 caractéristiques = 12 poids

### Total des Poids

En additionnant tous les poids nécessaires, nous obtenons :

- **Terrain** : 2 poids
- **Joueur** : 2 + 11 + 22 = 35 poids
- **Adversaire** : 2 + 11 + 22 = 35 poids
- **Attaques** : 12 poids

Total : 2 + 35 + 35 + 12 = 84 poids