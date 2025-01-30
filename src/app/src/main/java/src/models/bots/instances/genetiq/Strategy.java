package src.models.bots.instances.genetiq;

public class Strategy {
    private double[] weights;
    private double fitness;

    public Strategy(double[] weights) {
        this.weights = weights;
        this.fitness = 0.0;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
