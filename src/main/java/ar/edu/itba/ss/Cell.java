package ar.edu.itba.ss;

import java.util.Objects;

public class Cell {
    private boolean alive;
    private int neighbours;
    private final int xCord;
    private final int yCord;

    public Cell(boolean isAlive, int xCord, int yCord) {
        this.alive = isAlive;
        this.neighbours = 0;
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public void addNeighbour() {
        neighbours++;
    }

    public void resetNeighbours() {
        neighbours = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setLife(boolean b) {
        alive = b;
    }

    public int getNeighbours() {
        return neighbours;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }

    public void checkNeighbour(Cell cell) {
        if(!cell.equals(this)) {
            if(cell.isAlive()) {
                addNeighbour();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return xCord == cell.xCord && yCord == cell.yCord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCord, yCord);
    }
}
