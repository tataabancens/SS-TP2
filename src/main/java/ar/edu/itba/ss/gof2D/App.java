package ar.edu.itba.ss.gof2D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // TODO: Falta calcular la distancia y realizar los observables, porcentaje inicial vs porcentaje final
        // TODO: Y ademas Porcentaje de particulas vs tiempo, ver cuando se estabiliza

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

        GameOfLife gof = new GameOfLife(50, 50);
//        readTxt(gof, scanner);
        gof.initializeCells();

        GofPrinter printer = new GofPrinter();
        printJsons(gof, printer);

        for (int i = 0; i < 250; i++) {
            gof.next();
            printJsons(gof, printer);
            if (gof.stopCondition()) {
                break;
            }
        }
        // Store results
        writeJsons(printer, prob);
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

    public static void printJsons(GameOfLife gof, GofPrinter printer) {
        printer.addMapToJson(gof);
        printer.aliveCellsToJson(gof);
        printer.distanceTArrayToJson(gof);
    }

    public static void writeJsons(GofPrinter printer, String prob) {
        App.writeToFile("python/gof2D/results/maps" + prob + ".json", printer.getMaps().toJSONString());
        App.writeToFile("python/gof2D/results/aliveCells" + prob +".json", printer.getAliveCellsTArray().toJSONString());
        App.writeToFile("python/gof2D/results/maxDistance" + prob + ".json", printer.getDistanceTArray().toJSONString());
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
