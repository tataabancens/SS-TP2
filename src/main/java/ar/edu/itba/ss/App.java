package ar.edu.itba.ss;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GameOfLife gof = new GameOfLife(100);

        for (int i = 0; i < 1000; i++) {
            gof.next();
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
}
