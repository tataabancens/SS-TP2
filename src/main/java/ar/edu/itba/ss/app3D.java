package ar.edu.itba.ss;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class app3D {
    private final static String RADIUS_FILE = "./outputSimulation/radius_vs_time.txt";
    private final static String LIVING_PERCENT_FILE = "./outputSimulation/living_percent_vs_time.txt";

    public static void main(String[] args) {

        FlagParser.ParseOptions(args);

        try{
            // Parsing the initial configuration
            ConfigParser.ParseConfiguration(FlagParser.dynamicFile, FlagParser.staticFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
        // Generate GameOfLife board and rule sets to follow
        List<char[][]> board = ConfigParser.board;
        RuleSet ruleSet = FlagParser.ruleSet;
        GameOfLife3D gol = new GameOfLife3D(board, ruleSet);
        double initialMaxDistance = gol.calculateMaxDistance();

        List<int[]> pointsToWrite;

        // To create the graph evolution vs time
        List<Double> livingVsTime = new ArrayList<>();
        List<Double> radiusVsTime = new ArrayList<>();
        livingVsTime.add(ConfigParser.livingTotalPercentage);
        radiusVsTime.add(initialMaxDistance);
        boolean flag = true;
        for (int i = 1; i < FlagParser.timeInterval && flag; i++) {
            // Simulating the step
            pointsToWrite = gol.simulateStep();
            flag = gol.isReachedMax();


            livingVsTime.add(gol.getLivingPercentage());
            radiusVsTime.add(gol.getMaxDistance());

            // Writing results to file
            GenerateOutputFile(pointsToWrite, i);
        }
        AddToEvolutionStatisticsFile(ConfigParser.livingLimitedPercentage, FlagParser.ruleSet, livingVsTime, LIVING_PERCENT_FILE);
        AddToEvolutionStatisticsFile(ConfigParser.livingLimitedPercentage, FlagParser.ruleSet, radiusVsTime, RADIUS_FILE);
    }
    private static void GenerateOutputFile(List<int[]> cells, int iteration) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(FlagParser.dynamicFile, true));
            bf.append(String.format("%d\n", iteration));

            // Creating the output for the file
            cells.forEach(cell -> {
                try {
                    if (cell.length == 3) {
                        bf.append(String.format("%d %d %d\n", cell[0], cell[1], cell[2]));
                    }
                } catch (IOException e) {
                    System.out.println("Error writing to the output file");
                }
            });

            bf.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error writing to the output file");
        }
    }

    private static void AddToEvolutionStatisticsFile(double initialPercentage, RuleSet rule, List<Double> evolution, String file) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.US,"%.3f %d", initialPercentage, rule.getRuleId()));
        for (Double aDouble : evolution) {
            sb.append(String.format(Locale.US, " %.3f", aDouble));
        }
        sb.append("\n");
        try {
            Files.write(Paths.get(file), sb.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (FileNotFoundException e) {
            System.out.println(file + " not found");
        } catch (IOException e) {
            System.out.println("Error writing to the statistics file: " + file);
        }
    }
}
