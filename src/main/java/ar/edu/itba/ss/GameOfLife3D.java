package ar.edu.itba.ss;

import java.util.*;
import java.util.function.BiPredicate;

public class GameOfLife3D {

    private static final Map<RuleSet, BiPredicate<Character, Integer>> RULES;

    static {
        Map<RuleSet, BiPredicate<Character, Integer>> tmp = new HashMap<>();

        // Ruleset 1
        BiPredicate<Character, Integer> liveToLive45 = (s, n) -> s == 1 && n <= 5 && n >= 4;
        BiPredicate<Character, Integer> deadToLive55 = (s, n) -> s == 0 && n == 3;
        tmp.put(RuleSet.DEFAULT_RULE, liveToLive45.or(deadToLive55));

        // Ruleset 2
        BiPredicate<Character, Integer> liveToLive16 = (s, n) -> s == 1 && n < 16;
        BiPredicate<Character, Integer> deadToLive6 = (s, n) -> s == 0 && n > 6;
        tmp.put(RuleSet.RULE_2, liveToLive16.or(deadToLive6));

        // Ruleset 3
        BiPredicate<Character, Integer> liveToLive23 = (s, n) -> s == 1 && n >= 2 && n <= 3;
        BiPredicate<Character, Integer> deadToLive3 = (s, n) -> s == 0 && n == 3;
        tmp.put(RuleSet.RULE_3, liveToLive23.or(deadToLive3));

        RULES = Collections.unmodifiableMap(tmp);
    }

    private final char[][][] board;
    private final int xLim;
    private final int yLim;
    private final int zLim;
    private final RuleSet rule;
    private double maxDistance = 0;
    private int livingCellsCount = 0;
    private int totalCells;

    public GameOfLife3D(List<char[][]> layers, RuleSet ruleId) {
        // Setting the board limits
        this.zLim = layers.size();
        this.xLim = layers.get(0).length;
        this.yLim = layers.get(0)[0].length;

        // Setting the rules to be used
        this.rule = ruleId;

        // Setting the board
        this.board = new char[this.zLim][this.xLim][this.yLim];
        for (int i = 0; i < this.zLim; i++) {
            this.board[i] = layers.get(i);
        }

        // Setting the total number of cells
        this.totalCells = this.xLim * this.yLim * this.zLim;
    }


    public List<int[]> simulateStep() {
        // List for active cells
        List<int[]> activeCells = new ArrayList<>();

        // List for the cells that die
        List<int[]> deadCells = new ArrayList<>();

        // Variable for the live neighbours
        int liveNeighbours, newState;

        // Varaible for the distance
        double distanceToCenter;

        // Resetting the variables
        this.maxDistance = 0;
        this.livingCellsCount = 0;

        for (int z = 0; z < this.zLim ; z++) {
            for (int x = 0; x < this.xLim; x++) {
                for (int y = 0; y < this.yLim; y++) {
                    // Calculating the amount of live neighbours
                    liveNeighbours = this.countLiveNeighbours(x, y, z);

                    // Calculating the new state
                    newState = RULES.get(this.rule).test(this.board[z][x][y], liveNeighbours) ? 1 : 0;

                    // If the cell is active, add it to the list and calculate the distance to the center
                    if (newState == 1) {
                        // Adding to the list
                        activeCells.add(new int[]{x, y, z});

                        // Calculating the distance and checking if greater than max
                        distanceToCenter = this.getDistanceToCenter(x, y, z);
                        this.maxDistance = Math.max(distanceToCenter, this.maxDistance);
                    } else if (this.board[z][x][y] == 1){
                        // This are the cells that die
                        deadCells.add(new int[]{x, y, z});
                    }
                }
            }
        }

        // Setting the new dead cells
        deadCells.forEach(cell -> {
            this.board[cell[2]][cell[0]][cell[1]] = 0;//cell[2] = z,cell[0] = x, cell[1] = y
        });

        // Setting the new active cells
        activeCells.forEach(cell -> {
            this.board[cell[2]][cell[0]][cell[1]] = 1;
            this.livingCellsCount++;
        });

        return activeCells;
    }

    /**
     * Counts the given live neighbours for a given cell
     *
     * @param x X position for the cell
     * @param y Y position for the cell
     * @param z Z position for the cell
     * @return The total number of live neighbours the cell has
     */
    private int countLiveNeighbours(final int x, final int y, final int z) {
        int liveNeighbours = 0;

        // Analyze current z plane
        liveNeighbours += this.board[z][x][(y + 1) % this.yLim];
        liveNeighbours += this.board[z][x][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[z][(x + 1) % this.xLim][y];
        liveNeighbours += this.board[z][possibleNegative(x - 1, this.xLim)][y];
        liveNeighbours += this.board[z][(x + 1) % this.xLim][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[z][possibleNegative(x - 1, this.xLim)][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[z][(x + 1) % this.xLim][(y + 1) % this.yLim];
        liveNeighbours += this.board[z][possibleNegative(x - 1, this.xLim)][(y + 1) % this.yLim];

        // Analyze top z plane
        liveNeighbours += this.board[(z + 1) % this.zLim][x][(y + 1) % this.yLim];
        liveNeighbours += this.board[(z + 1) % this.zLim][x][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[(z + 1) % this.zLim][(x + 1) % this.xLim][y];
        liveNeighbours += this.board[(z + 1) % this.zLim][possibleNegative(x - 1, this.xLim)][y];
        liveNeighbours += this.board[(z + 1) % this.zLim][(x + 1) % this.xLim][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[(z + 1) % this.zLim][possibleNegative(x - 1, this.xLim)][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[(z + 1) % this.zLim][(x + 1) % this.xLim][(y + 1) % this.yLim];
        liveNeighbours += this.board[(z + 1) % this.zLim][possibleNegative(x - 1, this.xLim)][(y + 1) % this.yLim];
        liveNeighbours += this.board[(z + 1) % this.zLim][x][y];

        // Analyze bottom z plane
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][x][(y + 1) % this.yLim];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][x][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][(x + 1) % this.xLim][y];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][possibleNegative(x - 1, this.xLim)][y];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][(x + 1) % this.xLim][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][possibleNegative(x - 1, this.xLim)][possibleNegative(y - 1, this.yLim)];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][(x + 1) % this.xLim][(y + 1) % this.yLim];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][possibleNegative(x - 1, this.xLim)][(y + 1) % this.yLim];
        liveNeighbours += this.board[possibleNegative(z - 1, this.zLim)][x][y];

        return liveNeighbours;
    }

    private double getDistanceToCenter(final int x, final int y, final int z) {
        return (x - this.xLim / 2.0) + (y - this.yLim / 2.0) + (z - this.zLim / 2.0);
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public double getLivingPercentage() {
        return ((double)this.livingCellsCount / (double)this.totalCells) * 100.0;
    }

    private static int possibleNegative(int a, int b){
        int module = a % b;
        return module >= 0 ? module : module + b;
    }

    public double calculateMaxDistance() {
        double distanceToCenter;
        maxDistance = 0;

        for (int x = 0; x < this.xLim; x++) {
            for (int y = 0; y < this.yLim; y++) {
                for (int z = 0; z < this.zLim; z++) {
                    // Calculating the distance and checking if greater than max
                    if (this.board[z][x][y] == 1) {
                        distanceToCenter = this.getDistanceToCenter(x, y, z);
                        this.maxDistance = Math.max(distanceToCenter, this.maxDistance);
                    }
                }
            }
        }
        return maxDistance;
    }
}
