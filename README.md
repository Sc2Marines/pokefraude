# Pockefraude - Turn-Based Combat Game

This project is a Java-based turn-based combat game inspired by the classic Pokémon series. Players can engage in strategic battles by summoning monsters, utilizing diverse attacks, and employing consumable items.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Building the Project](#building-the-project)
- [Running the Game](#running-the-game)
- [Project Structure](#project-structure)
- [License](#license)

## Features

This project implements the core mechanics outlined in the TP document, including:

-   **Monster Types:**
    -   Implemented the following monster types: Electric, Water, Fire, Nature, and Dirt.
    -   Each type has strengths and weaknesses against others, as detailed in the TP document.
    -   Plant and Insect types are subtypes of the Nature type.
-   **Monster Characteristics:**
    -   Each monster has attributes such as:
        -   Health Points (HP)
        -   Attack
        -   Defense
        -   Speed
    -   These attributes are randomly generated within a specified range upon instantiation.
-   **Attacks:**
    -   Monsters can have up to four different attacks.
    -   Attacks have a type (Normal, Electric, Fire, Water, Nature, Dirt, Plant, Insect), power, number of uses, and a failure probability.
    -   Special attacks are linked to the monster type.
-   **Combat Mechanics:**
    -   Turn-based combat system where players choose actions simultaneously.
    -   Actions include:
        -   Attacking with a monster.
        -   Using an item.
        -   Changing the active monster.
    -   Attack damage is calculated based on the attacker's attack, the defender's defense, and type advantages.
    -   The monster with the higher speed attacks first.
-   **Status Effects:**
    -   Implemented status effects such as:
        -   **Paralysis:** Reduces the chance of a monster successfully attacking.
        -   **Burn:** Damages the monster at the start of each turn.
        -   **Poison:** Damages the monster at the start of each turn.
    -   Monsters can only have one status effect at a time.
-   **Special Abilities:**
    -   **Electric:** Chance to paralyze the opponent after an attack.
    -   **Water:** Chance to flood the terrain, causing a chance for the opponent to slip and take damage.
    -   **Fire:** Chance to burn the opponent after an attack.
    -   **Dirt:** Chance to hide under the ground, doubling defense.
    -   **Nature:** Regenerate health if on flooded terrain.
    -   **Plant:** Chance to heal at the end of the turn, removing status effects.
    -   **Insect:** Chance to poison the opponent after an attack.
-   **Items:**
    -   Implemented two types of items:
        -   **Potions:** Restore a monster's health.
        -   **Medicines:** Cure a monster of status effects.
-   **Game Loading:**
    -   Monsters and attacks are loaded from text files (`monsters.txt` and `attacks.txt`).
    -   The game allows for the addition of new monsters and attacks by modifying these files.
-   **Game Modes:**
    -   Two game modes are available:
        -   Human vs Human
        -   Human vs Bot
-   **Bot Implementation:**
    -   A basic bot is implemented, which chooses actions based on the current game state.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

-   [Java Development Kit (JDK)](https://adoptopenjdk.net/) (version 17 or higher)
-   [Gradle](https://gradle.org/install/) (version 7.x or higher)

## Project Setup

1.  **Clone the repository**:
    ```sh
    git clone https://github.com/Sc2Marines/pokefraude.git
    cd pokefraude
    ```

2.  **Configure the JDK**:
    Ensure the `JAVA_HOME` environment variable points to your JDK installation directory. For example:
    -   On Windows:
        ```cmd
        set JAVA_HOME=C:\path\to\your\jdk
        ```
    -   On Unix-based systems (Linux, macOS):
        ```sh
        export JAVA_HOME=/path/to/your/jdk
        ```

## Building the Project

To build the project, run the following command in the project's root directory:

-   On Windows:
    ```cmd
    gradlew.bat build
    ```
-   On Unix-based systems (Linux, macOS):
    ```sh
    ./gradlew build
    ```

This command downloads the dependencies, compiles the source code, and runs the tests.

## Running the Game

To run the game, use the following command:

-   On Windows:
    ```cmd
    gradlew.bat clean build run --console=plain
    ```
-   On Unix-based systems (Linux, macOS):
    ```sh
    ./gradlew clean build run --console=plain
    ```

This command launches the application using the `run` task defined in the `build.gradle` file.

## Project Structure

The project is organized as follows:

```
pocket-monster/
├── code/                                       # Contains the main application code
│   └── app/                                    # Application module
│       ├── src/                                # Source code directory
│       │   ├── main/                           # Main source code
│       │   │   ├── java/                       # Java source files
│       │   │   │   └── src/                    # Source code root
│       │   │   │       ├── controller/         # Controller classes
│       │   │   │       ├── models/             # Model classes
│       │   │   │       │   └── bots/           # Bot related classes
│       │   │   │       ├── utils/              # Utility classes
│       │   │   │       └── view/               # View classes
│       │   │   └── resources/                  # Resources for the main application
│       │   │       └── config/                 # Configuration files
│       │   └── test/                           # Test source code
│       │       └── java/                       # Java test files
│       └── build.gradle                        # Gradle build file for the app module
├── src/                                        # Contains the gradle build files
│   └── app/                                    # Application module
│       └── build/                              # Build output directory
└── README.md                                   # This file
```
## Project UML

Here is the UML for our final project:

![[plaintuml.png]]