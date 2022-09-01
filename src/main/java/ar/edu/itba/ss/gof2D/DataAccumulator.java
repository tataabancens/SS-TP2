package ar.edu.itba.ss.gof2D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataAccumulator {
    private List<Integer> maxDistances;
    private List<Integer> finalAliveCells;
    private int prob;

    public DataAccumulator(int prob) {
        this.maxDistances = new ArrayList<>();
        this.finalAliveCells = new ArrayList<>();
        this.prob = prob;
    }

    public List<Integer> getFinalAliveCells() {
        return finalAliveCells;
    }

    public void addAliveCells(Integer i) {
        finalAliveCells.add(i);
    }

    public void clearFinalAliveCells() {
        finalAliveCells.clear();
    }

    public float getAliveCellsProm() {
        int accum = 0;
        int count = 0;
        for (Integer i : finalAliveCells) {
            accum += i;
            count++;
        }
        return (float)accum / count;
    }

    public int getFinalAliveError() {
        return finalAliveCells.stream().max(Comparator.naturalOrder()).get() - finalAliveCells.stream().min(Comparator.naturalOrder()).get();
    }

    public List<Integer> getMaxDistances() {
        return maxDistances;
    }

    public void addMaxDistance(Integer i) {
        maxDistances.add(i);
    }

    public void clearMaxDistances() {
        maxDistances.clear();
    }

    public float getMaxDistanceProm() {
        int accum = 0;
        int count = 0;
        for (Integer i : maxDistances) {
            accum += i;
            count++;
        }
        return (float)accum / count;
    }

    public int getMaxDistanceError() {
        return maxDistances.stream().max(Comparator.naturalOrder()).get() - maxDistances.stream().min(Comparator.naturalOrder()).get();
    }

    public int getProb() {
        return prob;
    }
}
