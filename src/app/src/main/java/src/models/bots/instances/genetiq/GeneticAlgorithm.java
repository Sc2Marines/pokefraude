package src.models.bots.instances.genetiq;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import src.models.ActionType;
import src.models.AttackModel;
import src.models.GameModel;
import src.models.Monster;
import src.models.MonsterModel;
import src.models.PlayerModel;
import src.models.Action;

public class GeneticAlgorithm {
    private List<Strategy> population;
    private int populationSize;
    private Random random;
    private Strategy bestStrategy;

    public GeneticAlgorithm(int populationSize) {
        this.populationSize = populationSize;
        this.population = new ArrayList<>();
        this.random = new Random();
        initializePopulation();
    }

    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            double[] weights = new double[10]; // Example: 10 weights
            for (int j = 0; j < weights.length; j++) {
                weights[j] = random.nextDouble();
            }
            population.add(new Strategy(weights));
        }
    }

    public void evaluateFitness(GameModel gameModel) {
        for (Strategy strategy : population) {
            double fitness = playGame(gameModel, strategy, bestStrategy);
            strategy.setFitness(fitness);
        }
        updateBestStrategy();
    }

    private double playGame(GameModel gameModel, Strategy strategy, Strategy opponentStrategy) {
        // Create two players
        PlayerModel bot = new PlayerModel("bot");
        PlayerModel opponent = new PlayerModel("opponent");

        // Add players to the game
        gameModel.addJoueur(bot);
        gameModel.addJoueur(opponent);

        // Choose monsters for both players
        List<MonsterModel> botMonsters = chooseMonstersForStrategy(gameModel, bot, strategy);
        List<MonsterModel> opponentMonsters = chooseMonstersForStrategy(gameModel, opponent, opponentStrategy);

        bot.setMonsters(botMonsters, gameModel.getAvailableAttacks());
        opponent.setMonsters(opponentMonsters, gameModel.getAvailableAttacks());

        // Play the game
        while (!gameModel.estTermine()) {
            Action botAction = getActionForStrategy(bot, strategy);
            Action opponentAction = getActionForStrategy(opponent, opponentStrategy);

            gameModel.executerActions(botAction, opponentAction);
        }

        // Calculate fitness based on the game result
        PlayerModel winner = gameModel.getVainqueur();
        if (winner == bot) {
            return 1.0; // Bot won
        } else if (winner == opponent) {
            return 0.0; // Opponent won
        } else {
            return 0.5; // Draw
        }
    }

    private void updateBestStrategy() {
        population.sort(Comparator.comparingDouble(Strategy::getFitness).reversed());
        bestStrategy = population.get(0);
    }

    private List<MonsterModel> chooseMonstersForStrategy(GameModel gameModel, PlayerModel player, Strategy strategy) {
        // Implement monster selection logic based on the strategy
        // For simplicity, we can use a random selection for now
        List<MonsterModel> availableMonsters = gameModel.getAvailableMonsters();
        List<MonsterModel> chosenMonsters = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = (int) (strategy.getWeights()[i] * availableMonsters.size());
            chosenMonsters.add(availableMonsters.get(index));
            availableMonsters.remove(index);
        }
        return chosenMonsters;
    }

    private Action getActionForStrategy(PlayerModel player, Strategy strategy) {
        // Implement action selection logic based on the strategy
        // For simplicity, we can use a random selection for now
        Monster actualMonster = player.getMonstreActif();
        List<AttackModel> attacks = actualMonster.getAttacks();
        int index = (int) (strategy.getWeights()[3] * attacks.size());
        return new Action(ActionType.ATTAQUE, attacks.get(index));
    }

    public List<Strategy> selectBestStrategies() {
        population.sort(Comparator.comparingDouble(Strategy::getFitness).reversed());
        return population.subList(0, populationSize / 2);
    }

    public void crossoverAndMutate(List<Strategy> selectedStrategies) {
        List<Strategy> newPopulation = new ArrayList<>(selectedStrategies);
        while (newPopulation.size() < populationSize) {
            Strategy parent1 = selectedStrategies.get(random.nextInt(selectedStrategies.size()));
            Strategy parent2 = selectedStrategies.get(random.nextInt(selectedStrategies.size()));
            Strategy child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }
        population = newPopulation;
    }

    private Strategy crossover(Strategy parent1, Strategy parent2) {
        double[] childWeights = new double[parent1.getWeights().length];
        for (int i = 0; i < childWeights.length; i++) {
            childWeights[i] = random.nextBoolean() ? parent1.getWeights()[i] : parent2.getWeights()[i];
        }
        return new Strategy(childWeights);
    }

    private void mutate(Strategy strategy) {
        for (int i = 0; i < strategy.getWeights().length; i++) {
            if (random.nextDouble() < 0.1) { // 10% mutation rate
                strategy.getWeights()[i] = random.nextDouble();
            }
        }
    }

    public void evolve(GameModel gameModel, int generations) {
        for (int i = 0; i < generations; i++) {
            evaluateFitness(gameModel);
            List<Strategy> selectedStrategies = selectBestStrategies();
            crossoverAndMutate(selectedStrategies);
        }
    }
}
