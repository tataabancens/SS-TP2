package ar.edu.itba.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigParser {
    public static int xLim;
    public static int yLim;
    public static int zLim;
    public static double livingLimitedPercentage;
    public static double livingTotalPercentage = 0.0;
    public static int totalCells;
    public static List<char[][]> board = new ArrayList<>();

    public static void ParseConfiguration(String dynamicFileName, String staticFileName) throws FileNotFoundException {
        ParseStaticData(staticFileName);
        ParseDynamicData(dynamicFileName);
    }

    private static void ParseStaticData(String staticFileName) throws FileNotFoundException {
        File file = new File(staticFileName);
        Scanner sc = new Scanner(file);

        // Parsing the x limit of the area
        xLim = sc.nextInt();

        // Parsing the y limit of the area
        yLim = sc.nextInt();

        // Parsing the limit of the area if it has any
        zLim = sc.nextInt();
        
        livingLimitedPercentage = sc.nextDouble();
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
        totalCells = sc.nextInt();

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
            sc.nextLine();
        }
        double totalCells = xLim * yLim * zLim;
        livingTotalPercentage = ((double)livingCells / totalCells) * 100.0;
    }

}
