package ar.edu.itba.ss;

public class NeighbourCells {
    int xStart, xEnd, yStart, yEnd;

    public NeighbourCells(Cell c, int M) {
        // set xStart
        if (c.getXCord() == 0) {
            xStart = 0;
        } else {
            xStart = c.getXCord() - 1;
        }
        // Set xEnd
        if (c.getXCord() == M - 1) {
            xEnd = M;
        } else {
            xEnd = c.getXCord() + 2;
        }
        // Set yStart
        if (c.getYCord() == 0) {
            yStart = 0;
        } else {
            yStart = c.getYCord() - 1;
        }
        // Set yEnd
        if (c.getYCord() == M - 1) {
            yEnd = M;
        } else {
            yEnd = c.getYCord() + 2;
        }
    }
}
