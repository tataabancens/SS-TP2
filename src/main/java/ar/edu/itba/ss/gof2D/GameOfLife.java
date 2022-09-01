package ar.edu.itba.ss.gof2D;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameOfLife {
    private final List<Cell> cells;
    private final CenterPoint cp;
    private int M;
    private int t;
    private int prob;

    private int aliveCells = 0;
    private int maxDistance = 0;

    public GameOfLife(int M, int prob) {
        this.cells = new ArrayList<>();
        this.M = M;
        this.t = 0;
        this.cp = new CenterPoint(M);
        this.prob = prob;
    }

    public void initializeCells() {
        for(int i = 0; i < getM(); i++) {
            for(int j = 0; j < getM(); j++) {
                Cell c = new Cell(j, i);
                if (distanceToCenter(c) < 10) {
                    c.setLife(getRandomBoolean(prob));
                } else {
                    c.setLife(false);
                }
                cells.add(c);
            }
        }
    }

    static boolean getRandomBoolean(int probability) {
        double randomValue = Math.random() * 100;
        return randomValue <= probability;
    }

    public void next() {
        calculateNeighboursAndData();
        updateMap();
        t++;
    }

    public void printMap() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getM(); i++) {
            sb.append("|");
            for (int j = 0; j < getM(); j++) {
                if (cells.get(i + j * getM()).isAlive()) {
                    sb.append("0");
                } else {
                    sb.append("-");
                }
            }
            sb.append("|\n");
        }
        System.out.println(sb);
    }

    private void calculateNeighboursAndData() {
        resetAliveCells();
        resetMaxDistance();
        for(Cell c : getCells()) {
            if (c.isAlive()) {
                setAliveCells(aliveCells + 1);
                if (maxDistance < getM() / 2) {
                    setMaxDistance(Math.max(distanceToCenter(c), maxDistance));
                }
            }
            NeighbourCells nc = new NeighbourCells(c, M);
            for (int i = nc.xStart; i < nc.xEnd; i++) {
                for (int j = nc.yStart; j < nc.yEnd; j++) {
                    c.checkNeighbour(cells.get(i + j * M));
                }
            }
        }
    }

    private void updateMap() {
        for(Cell c : getCells()) {
            rule1(c);
//            rule2(c);
//            rule3(c);
            c.resetNeighbours();
        }
    }

    public void rule1(Cell c) {
        if (c.isAlive()) {
            if (c.getNeighbours() < 2 || c.getNeighbours() > 3) {
                c.setLife(false);
            }
        } else if (c.getNeighbours() == 3) {
            c.setLife(true);
        }
    }

    public boolean stopCondition() {
        return maxDistance > M / 2 || aliveCells == 0;
    }

    public boolean isStable() {
        return false;
    }

    public void rule2(Cell c) {
        if (c.isAlive()) {
            if (c.getNeighbours() > 4) {
                c.setLife(false);
            }
        } else if (c.getNeighbours() > 4) {
            c.setLife(true);
        }
    }

    public void rule3(Cell c) {
        if (c.isAlive()) {
            if (c.getNeighbours() > 0) {
                c.setLife(false);
            }
        } else if (c.getNeighbours() > 1) {
            c.setLife(true);
        }
    }

    private int distanceToCenter(Cell cell) {
        return (int) Math.floor(Math.abs(cell.getXCord() - getCenter().getXCord()) + Math.abs(cell.getYCord() - getCenter().getYCord()));
    }

    public CenterPoint getCenter() {
        return cp;
    }

    private static class CenterPoint {
        private final float xCord, yCord;

        public CenterPoint(int M) {
            xCord = (float) M / 2;
            yCord = (float) M / 2;
        }

        public float getXCord() {
            return xCord;
        }
        public float getYCord() {
            return yCord;
        }
    }

    public int getAliveCells() {
        return aliveCells;
    }

    public void setAliveCells(int aliveCells) {
        this.aliveCells = aliveCells;
    }

    public void resetAliveCells() {
        aliveCells = 0;
    }
    public int getM() {
        return M;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void resetMaxDistance() {
        maxDistance = 0;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public int getT() {
        return t;
    }
    public void setM(int M) {
        this.M = M;
    }
}
