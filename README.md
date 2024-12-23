# Pocket Monster - Combat Game

This project is a Java adaptation of a turn-based combat game inspired by Pokémon. Each player can summon monsters, use attacks, and items to battle the opponent.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- [Java Development Kit (JDK)](https://adoptopenjdk.net/) (version 17 or higher)
- [Gradle](https://gradle.org/install/) (version 7.x or higher)

## Project Setup

1. **Clone the repository**:
   ```sh
   git clone https://github.com/Sc2Marines/pokefraude.git
   cd pokefraude
   ```

2. **Configure the JDK**:
   Ensure the `JAVA_HOME` environment variable points to your JDK installation directory. For example:
   ```sh
   export JAVA_HOME=/path/to/your/jdk
   ```

## Building the Project

To build the project, run the following command in the project's root directory:

```sh
./gradlew build
```

This command downloads the dependencies, compiles the source code, and runs the tests.

## Running the Game

To run the game, use the following command:

```sh
./gradlew run
```

This command launches the application using the `run` task defined in the `build.gradle` file.

## Project Structure

The project is organized as follows:

```
pocket-monster/
├── build.gradle.kts          # Gradle configuration file (Kotlin DSL)
├── settings.gradle.kts       # Gradle settings configuration file
├── src/
│   ├── main/
│   │   ├── java/             # Java source code
│   │   └── resources/        # Resources
│   └── test/
│       ├── java/             # Unit tests
│       └── resources/        # Test resources
├── README.md                 # This file
└── ...
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
