package ar.edu.itba.ss;

import ar.edu.itba.ss.gof2D.DataAccumulator;
import ar.edu.itba.ss.gof2D.GofPrinter;

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

import static ar.edu.itba.ss.gof2D.App.writeFinalJsons;

public class app3D {
    private final static String RADIUS_FILE = "./outputSimulation/radius_vs_time.txt";
    private final static String LIVING_PERCENT_FILE = "./outputSimulation/living_percent_vs_time.txt";

    public static void main(String[] args) {

        FlagParser.ParseOptions(args);
        //onlyFinals();

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
        int initialMaxDistance = gol.calculateMaxDistance();

        List<int[]> pointsToWrite;

        // To create the graph evolution vs time
        List<Integer> livingVsTime = new ArrayList<>();
        List<Integer> radiusVsTime = new ArrayList<>();
        livingVsTime.add(ConfigParser.totalCells);
        radiusVsTime.add(initialMaxDistance);
        boolean flag = true;
        for (int i = 1; i < FlagParser.timeInterval && flag; i++) {
            // Simulating the step
            pointsToWrite = gol.simulateStep();
            flag = gol.isReachedMax();


            livingVsTime.add(pointsToWrite.size());
            radiusVsTime.add((int) Math.floor(gol.getMaxDistance()));

            // Writing results to file
            GenerateOutputFile(pointsToWrite, i,gol);
        }
        //generateLivingFile(ConfigParser.totalCells, FlagParser.ruleSet, livingVsTime, LIVING_PERCENT_FILE);
        //AddToEvolutionStatisticsFile(ConfigParser.livingLimitedPercentage, FlagParser.ruleSet, radiusVsTime, RADIUS_FILE);
    }
    private static void GenerateOutputFile(List<int[]> cells, int iteration,GameOfLife3D gol) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(FlagParser.dynamicFile, true));
            bf.append(String.format("%d\n\n", cells.size()+8));
            bf.append("0 0 0 43\n").append(String.format("0 0 %d 43\n",ConfigParser.xLim));
            bf.append(String.format("0 %d 0 43\n",ConfigParser.xLim)).append(String.format("0 %d %d 43\n",ConfigParser.xLim,ConfigParser.xLim));
            bf.append(String.format("%d 0 0 43\n",ConfigParser.xLim)).append(String.format("%d 0 %d 43\n",ConfigParser.xLim,ConfigParser.xLim));
            bf.append(String.format("%d %d 0 43\n",ConfigParser.xLim,ConfigParser.xLim)).append(String.format("%d %d %d 43\n",ConfigParser.xLim,ConfigParser.xLim,ConfigParser.xLim));

            // Creating the output for the file
            cells.forEach(cell -> {
                try {
                    if (cell.length == 3) {
                        bf.append(String.format("%d %d %d %d\n", cell[0], cell[1], cell[2],gol.getDistanceToCenter(cell[0],cell[1],cell[2])));
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

    private static String buildStaticPath(int k, int i){
        StringBuilder str = new StringBuilder();
        str.append("./input/static").append(k).append(i*10).append(".txt");
        return str.toString();
    }
    private static String buildDynamicPath(int k, int i){
        StringBuilder str = new StringBuilder();
        str.append("./input/dynamic").append(k).append(i*10).append(".xyz");
        return str.toString();
    }
    private static void onlyFinals() {
            GofPrinter printer = new GofPrinter();
            for (int i = 1; i < 10; i++) {
                if (!(i == 5 || i == 6 || i == 4))  {
                    DataAccumulator data = new DataAccumulator(i * 10);
                    for (int k = 0; k < 5; k++) {
                        String staticPath = buildStaticPath(k, i);
                        String dynamicPath = buildDynamicPath(k, i);
                        try{
                            // Parsing the initial configuration
                            ConfigParser.ParseConfiguration(dynamicPath, staticPath);
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found");
                            System.exit(1);
                        }
                        List<char[][]> board = ConfigParser.board;
                        RuleSet ruleSet = FlagParser.ruleSet;
                        GameOfLife3D gol = new GameOfLife3D(board, ruleSet);
                        int initialMaxDistance = gol.calculateMaxDistance();

                        List<int[]> pointsToWrite;
                        boolean flag = true;
                        int livingCells = 0;

                        for (int j = 1; j < FlagParser.timeInterval && flag; j++) {
                            // Simulating the step
                            pointsToWrite = gol.simulateStep();
                            flag = gol.isReachedMax();
                            livingCells = pointsToWrite.size();

                        }
                        data.addMaxDistance((int) Math.floor(gol.getMaxDistance()));
                        data.addAliveCells(livingCells);
                    }
                    printer.addFinalDistanceToJson(data);
                    printer.addFinalAliveCellsToJson(data);
                }
            }
            writeFinalJsons(printer, 3);
    }
    private static void AddToEvolutionStatisticsFile(double initialPercentage, RuleSet rule, List<Integer> evolution, String file) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.US,"%.3f %d", initialPercentage, rule.getRuleId()));
        for (Integer aInteger : evolution) {
            sb.append(String.format(Locale.US, " %d", aInteger));
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
