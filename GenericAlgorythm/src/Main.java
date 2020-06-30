import java.util.Random;

public class Main {

    private Chromosome[] population = new Chromosome[Config.POPULATION_COUNT];

    public static int function(int u, int w, int x, int y, int z) {
        return u * w * x * x * y +
                y +
                w * w * x * z +
                u * w * x * z +
                u * w * x * z;
    }

    public static void main(String[] args) {
        Main test = new Main();
        test.createInitialPopulation();
        int iterationsNumber = 0;
        do {
            int fillingWithFitnessesResult = test.fillChromosomesWithFitnesses();
            if (fillingWithFitnessesResult != Config.TARGET_NOT_REACHED_FLAG) {
                System.out.println("\nSolution found: " + test.getPopulation()[fillingWithFitnessesResult]);
                return;
            }
            test.fillChromosomeWithLikelihoods();
            int[][] pairs = test.getPairsForCrossover();
            test.analizePairs(pairs);
            Chromosome[] nextGeneration = test.performCrossoverAndMutationForThePopulationAndGetNextGeneration(pairs);
            test.setPopulation(nextGeneration);
            System.out.println("*** Iteration " + iterationsNumber + " is over ***");
        } while (iterationsNumber++ < Config.MAX_ITERATIONS);
        System.out.println("Solution not found.");
    }

    private void createInitialPopulation() {
        for (int i = 0; i < Config.POPULATION_COUNT; ++i) {
            population[i] = new Chromosome();
            fillChromosomeWithRandomGenes(population[i]);
        }
    }

    private void fillChromosomeWithRandomGenes(Chromosome chromosome) {
        for (int i = 0; i < Config.GENES_COUNT; ++i) {
            chromosome.getGenes()[i] = getRandomGene();
        }
    }

    public static int getRandomGene() {
        return getRandomInt(Config.GENE_MIN + 150, Config.GENE_MAX - 100);
    }

    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max + 1) + min;
    }

    private int fillChromosomesWithFitnesses() {
        for (int i = 0; i < Config.POPULATION_COUNT; ++i) {
            float currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);
            if (currentFitness == Config.TARGET_IS_REACHED_FLAG) return i;
        }
        return Config.TARGET_NOT_REACHED_FLAG;
    }

    private void fillChromosomeWithLikelihoods() {
        float allFitnessesSum = getAllFitnessesSum();
        float last = 0.0F;
        int i;
        for (i = 0; i < Config.POPULATION_COUNT; ++i) {
            float likelihood = last + (100 * population[i].getFitness() / allFitnessesSum);
            last = likelihood;
            population[i].setLikelihood(likelihood);
        }
        population[i - 1].setLikelihood(100);
    }

    private float getAllFitnessesSum() {
        float allFitnessesSum = 0.0F;
        for (int i = 0; i < Config.POPULATION_COUNT; ++i)
            allFitnessesSum += population[i].getFitness();
        return allFitnessesSum;
    }

    private int[][] getPairsForCrossover() {
        int[][] pairs = new int[Config.POPULATION_COUNT][2];
        for (int i = 0; i < Config.POPULATION_COUNT; ++i) {
            float p = getRandomFloat(0, 100);
            int firstChromosome = getChromosomeNumber(p);
            int secondChromosome;
            do {
                p = getRandomFloat(0, 100);
                secondChromosome = getChromosomeNumber(p);
            } while (firstChromosome == secondChromosome);
            pairs[i][0] = firstChromosome;
            pairs[i][1] = secondChromosome;
        }
        return pairs;
    }

    private int getChromosomeNumber(float rand) {
        int i;
        for (i = 0; i < Config.POPULATION_COUNT; ++i)
            if (rand <= population[i].getLikelihood())
                return i;
        return 0;
    }

    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * max + min);
    }

    private Chromosome[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(int[][] pairs) {
        Chromosome[] nextGeneration = new Chromosome[Config.POPULATION_COUNT];
        for (int i = 0; i < Config.POPULATION_COUNT; ++i) {
            Chromosome firstParent = population[pairs[i][0]];
            Chromosome secondParent = population[pairs[i][1]];
            Chromosome result = firstParent.singleCrossover(secondParent);
            nextGeneration[i] = result;
            nextGeneration[i] = nextGeneration[i].mutateWithGivenProbability();
        }
        return nextGeneration;
    }

    public Chromosome[] getPopulation() {
        return population;
    }

    public void setPopulation(Chromosome[] population) {
        this.population = population;
    }

    public static void log(String message) {
        System.out.println(message);
    }

    private void analizePairs(int[][] pairs) {
        int[] totals = new int[Config.POPULATION_COUNT];
        for (int i = 0; i < Config.POPULATION_COUNT; ++i) {
            totals[i] = 0;
        }
        for (int i = 0; i < Config.POPULATION_COUNT; ++i) {
            for (int j = 0; j < 2; ++j) {
                totals[pairs[i][j]]++;
            }
        }
    }

}