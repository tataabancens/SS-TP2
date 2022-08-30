package ar.edu.itba.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigParser {
    public static int xLim = 60;
    public static int yLim = 60;
    public static int zLim = 60;
    public static double livingTotalPercentage = 0.0;
    public static List<char[][]> board = new ArrayList<>();

    public static void ParseConfiguration(String dynamicFileName) throws FileNotFoundException {
        ParseDynamicData(dynamicFileName);
    }

    private static void ParseDynamicData(String dynamicFileName) throws FileNotFoundException {
        File file = new File(dynamicFileName);
        Scanner sc = new Scanner(file);
        int livingCells = 0;

        for (int z = 0; z < zLim; z++) {
            board.add(new char[xLim][yLim]);
            for (int x = 0; x < xLim; x++) {
                for (int y = 0; y < yLim; y++) {
                    board.get(z)[x][y] = 0;
                }
            }
        }

        // Skipping the time of the file which is 0
        sc.nextInt();

        while (sc.hasNext()){
            // Parsing the x position
            int x = sc.nextInt();

            // Parsing the y position
            int y = sc.nextInt();

            // Setting the z in case it is only 3D
            int z = sc.nextInt();

            // Setting the board cell (x, y, z) as alive
            board.get(z)[x][y] = 1;
            livingCells++;
        }
        double totalCells = xLim * yLim * zLim;
        livingTotalPercentage = ((double)livingCells / totalCells) * 100.0;
    }
}
