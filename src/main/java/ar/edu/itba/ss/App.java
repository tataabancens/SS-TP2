package ar.edu.itba.ss;

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
        // Locating inputs.txt in resources
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("input.txt");
        if (is == null) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Initiate setup
        Scanner scanner = new Scanner(is);
        GameOfLife gof = new GameOfLife(250);
//        readTxt(gof, scanner);
//        gof.addMapToJson();

        gof.initializeCells();
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            gof.next();
            long delta = System.currentTimeMillis() - t0;
            int aliveCells = gof.getAliveCells();
//            gof.printMap();
        }
        // Store results
        String filePath = "python/maps.json"; //
        App.writeToFile(filePath, gof.getMaps().toJSONString());
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
