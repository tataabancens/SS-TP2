package ar.edu.itba.ss.gof2D;


import java.io.*;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        // Locating variables.txt in resources
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("input.txt");
        InputStream variables = classloader.getResourceAsStream("variables.txt");
        if (is == null) {
            System.out.println("File not found");
            System.exit(1);
        }
        if (variables == null) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Initiate setup
        Scanner scanner = new Scanner(is);
        Scanner varScanner = new Scanner(is);
        String prob = varScanner.nextLine();

        sixMaps();

        onlyFinals();
    }

    private static void onlyFinals() {
        for (int rule = 0; rule < 3; rule++) {
            GofPrinter printer = new GofPrinter();
            for (int i = 1; i < 10; i++) {
                if (!(i == 5 || i == 6 || i == 4))  {
                    DataAccumulator data = new DataAccumulator(i * 10);
                    for (int k = 0; k < 5; k++) {
                        GameOfLife gof = new GameOfLife(50, i * 10, rule);
                        gof.initializeCells();

                        for (int j = 0; j < 250; j++) {
                            gof.next();
                            if (gof.stopCondition()) {
                                break;
                            }
                        }
                        data.addMaxDistance(gof.getMaxDistance());
                        data.addAliveCells(gof.getAliveCells());
                    }
                    printer.addFinalDistanceToJson(data);
                    printer.addFinalAliveCellsToJson(data);
                }
            }
            writeFinalJsons(printer, rule);
        }
    }

    public static void writeFinalJsons(GofPrinter printer, int rule) {
        String pathAliveCells = "python/gof2D/results/rule" + rule + "/finalAliveCells.json";
        String pathMaxDistance = "python/gof2D/results/rule" + rule + "/finalDistances.json";

        new File(pathAliveCells).delete();
        new File(pathMaxDistance).delete();

        App.writeToFile("python/gof2D/results/rule" + rule +"/finalDistances.json", printer.getFinalMaxDistances().toJSONString());
        App.writeToFile("python/gof2D/results/rule" + rule +"/finalAliveCells.json", printer.getFinalAliveCells().toJSONString());
    }

    private static void sixMaps() {
        for (int rule = 0; rule < 3; rule++) {
            for (int i = 1; i < 10; i++) {
                if (!(i == 5 || i == 6 || i == 4))  {
                    GameOfLife gof = new GameOfLife(50, i * 10, rule);
                    gof.initializeCells();

                    GofPrinter printer = new GofPrinter();
                    printMapJson(gof, printer);

                    for (int j = 0; j < 250; j++) {
                        gof.next();
                        printMapJson(gof, printer);
                        printDataJson(gof, printer);
                        if (gof.stopCondition()) {
                            break;
                        }
                    }
                // Store results
                writeJsons(printer, String.format("%d", i * 10), rule);
                }
            }
        }
    }




    private static void writeToFile(String filepath, String toWrite) {
        try {
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.print(toWrite);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    public static void printMapJson(GameOfLife gof, GofPrinter printer) {
        printer.addMapToJson(gof);

    }
    public static void printDataJson(GameOfLife gof, GofPrinter printer) {
        printer.aliveCellsToJson(gof);
        printer.distanceTArrayToJson(gof);
    }

    public static void writeJsons(GofPrinter printer, String prob, int rule) {
        String pathMaps = "python/gof2D/results/rule" + rule + "/maps" + prob + ".json";
        String pathAliveCells = "python/gof2D/results/rule" + rule + "/aliveCells" + prob +".json";
        String pathMaxDistance = "python/gof2D/results/rule" + rule + "/maxDistance" + prob + ".json";

        new File(pathMaps).delete();
        new File(pathAliveCells).delete();
        new File(pathMaxDistance).delete();

        App.writeToFile(pathMaps, printer.getMaps().toJSONString());
        App.writeToFile(pathAliveCells, printer.getAliveCellsTArray().toJSONString());
        App.writeToFile(pathMaxDistance, printer.getDistanceTArray().toJSONString());
    }

    public static void readTxt(GameOfLife gof, Scanner scanner) {
        // Read N value
        if (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            gof.setM(Integer.parseInt(in));
        }
        List<Cell> cells = gof.getCells();
        for (int i = 0; i < gof.getM(); i++) {
            String in = scanner.nextLine();

            for (int j = 0; j < gof.getM(); j++) {
                if (in.charAt(j) == '0') {
                    cells.add(new Cell(true, j, i));
                } else if (in.charAt(j) == '-') {
                    cells.add(new Cell(false, j, i));
                }
            }
        }
    }
}

