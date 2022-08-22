package ar.edu.itba.ss;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameOfLife {
    private final List<Cell> cells;
    private final int M;
    private int t;
    private JSONArray maps = new JSONArray();

    public GameOfLife(int M) {
        this.cells = new ArrayList<>();
        this.M = M;
        t = 0;
        // Initialize cells
        initializeCells();
    }

    private void initializeCells() {
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
        addMapToJson();
        t++;
    }

    public void addMapToJson() {
        JSONObject jsMap = new JSONObject();
        jsMap.put("t", getT());
        JSONArray rows = new JSONArray();
        for (int i = 0; i < getM(); i++) {
            JSONArray col = new JSONArray();
            for (int j = 0; j < getM(); j++) {
                col.add(getCells().get(i + j * M).isAlive());
            }
            rows.add(col);
        }
        jsMap.put("map", rows);
        maps.add(jsMap);
    }

    public JSONArray getMaps() {
        return maps;
    }

    public void printMap() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getM(); i++) {
            for (int j = 0; j < getM(); j++) {
                if (cells.get(i + j * getM()).isAlive()) {
                    sb.append("*");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


    private void calculateNeighbours() {
        for(Cell c : getCells()) {
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
            if (c.isAlive()) {
                if (c.getNeighbours() < 2 || c.getNeighbours() > 3) {
                    c.setLife(false);
                }
            } else if (c.getNeighbours() == 3) {
                c.setLife(true);
            }
            c.resetNeighbours();
        }
    }

    public int getM() {
        return M;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public int getT() {
        return t;
    }
}
