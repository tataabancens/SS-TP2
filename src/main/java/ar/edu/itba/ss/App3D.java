package ar.edu.itba.ss;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App3D {
    public static void main(String[] args) {
        // Parsing the options
        FlagParser.ParseOptions(args);

        try {
            // Parsing the initial configuration
            ConfigParser.ParseConfiguration(FlagParser.dynamicFile);
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

        for (int i = 1; i < FlagParser.timeInterval; i++) {
            // Simulating the step
            pointsToWrite = gol.simulateStep();

            // Writing results to file
            GenerateOutputFile(pointsToWrite, i);
        }
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
}
