public class Chromosome {

    private int[] genes = new int[Config.GENES_COUNT];
    private float fitness;
    private float likelihood;

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public float getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(float likelihood) {
        this.likelihood = likelihood;
    }

    public float calculateFitness() {
        int u = genes[0];
        int w = genes[1];
        int x = genes[2];
        int y = genes[3];
        int z = genes[4];
        int closeness = Math.abs(Config.TARGET_VALUE - Main.function(u, w, x, y, z));
        Main.log("Closeness: " + closeness);
        return 0 != closeness ? 1 / (float) closeness : Config.TARGET_IS_REACHED_FLAG;
    }

    public Chromosome mutateWithGivenProbability() {
        Chromosome result = (Chromosome) this.cloneChromosome();
        for (int i = 0; i < Config.GENES_COUNT; ++i) {
            float randomPercent = Main.getRandomFloat(0, 100);
            if (randomPercent < Config.MUTATION_PROBABILITY) {
                int oldValue = result.getGenes()[i];
                int newValue = Main.getRandomGene();
                result.getGenes()[i] = newValue;
            }
        }
        return result;
    }

    public Chromosome[] doubleCrossover(Chromosome chromosome) {
        int crossoverLine = getRandomCrossoverLine();
        Chromosome[] result = new Chromosome[2];
        result[0] = new Chromosome();
        result[1] = new Chromosome();
        for (int i = 0; i < Config.GENES_COUNT; ++i) {
            if (i <= crossoverLine) {
                result[0].getGenes()[i] = this.getGenes()[i];
                result[1].getGenes()[i] = chromosome.getGenes()[i];
            } else {
                result[0].getGenes()[i] = chromosome.getGenes()[i];
                result[1].getGenes()[i] = this.getGenes()[i];
            }
        }
        return result;
    }

    public Chromosome singleCrossover(Chromosome chromosome) {
        Chromosome[] children = doubleCrossover(chromosome);
        int childNumber = Main.getRandomInt(0, 1);
        return children[childNumber];
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        for (int i = 0; i < Config.GENES_COUNT; ++i) {
            result.append(genes[i]);
            result.append(i < Config.GENES_COUNT - 1 ? ", " : "");
        }
        result.append(")");
        return result.toString();
    }

    private static int getRandomCrossoverLine() {
        int line = Main.getRandomInt(0, Config.GENES_COUNT - 2);
        return line;
    }

    protected Object cloneChromosome() {
        Chromosome resultChromosome = new Chromosome();
        resultChromosome.setFitness(this.getFitness());
        resultChromosome.setLikelihood(this.getLikelihood());
        resultChromosome.setGenes(this.genes.clone());
        return resultChromosome;
    }
}