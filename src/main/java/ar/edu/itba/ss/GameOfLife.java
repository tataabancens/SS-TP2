package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameOfLife {
    private final List<Cell> cells;
    private final int M;

    public GameOfLife(int M) {
        this.cells = new ArrayList<>();
        this.M = M;
        // Initialize cells
        initializeCells();
    }

    public void initializeCells() {
        Random r = new Random();
        for(int i = 0; i < getM(); i++) {
            for(int j = 0; j < getM(); j++) {
                cells.add(new Cell(r.nextBoolean(), i, j));
            }
        }
    }

    public void randomizeMap() {
        for (Cell c : getCells()) {
            Random r = new Random();
            c.setLife(r.nextBoolean());
        }
    }

    public void next() {
        calculateNeighbours();

        updateMap();
    }

    public void calculateNeighbours() {
        for(Cell c : getCells()) {
            NeighbourCells nc = new NeighbourCells(c, M);

            for (int i = nc.xStart; i < nc.xEnd; i++) {
                for (int j = nc.yStart; j < nc.yEnd; j++) {
                    c.checkNeighbour(cells.get(i + j * M));
                }
            }
        }
    }

    public void updateMap() {
        for(Cell c : getCells()) {
            if (c.isAlive()) {
                if (c.getNeighbours() < 2 || c.getNeighbours() > 3) {
                    c.setLife(false);
                }
            } else if (c.getNeighbours() == 3) {
                c.setLife(true);
            }
        }
    }

    public int getM() {
        return M;
    }

    public List<Cell> getCells() {
        return cells;
    }


}
