package ar.edu.itba.ss.gof2D;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GofPrinter {
    private JSONArray maps;
    private JSONArray aliveCellsTArray;
    private JSONArray distanceTArray;
    private JSONArray finalMaxDistances;
    private JSONArray finalAliveCells;

    public GofPrinter() {
        aliveCellsTArray = new JSONArray();
        distanceTArray = new JSONArray();
        finalMaxDistances = new JSONArray();
        finalAliveCells = new JSONArray();
        maps = new JSONArray();
    }

    public void addFinalDistanceToJson(DataAccumulator data) {
        JSONObject jsMaxDistance = new JSONObject();
        jsMaxDistance.put("maxDistanceProm", data.getMaxDistanceProm());
        jsMaxDistance.put("N", data.getProb());
        jsMaxDistance.put("error", data.getMaxDistanceError());
        finalMaxDistances.add(jsMaxDistance);
    }

    public void addFinalAliveCellsToJson(DataAccumulator data) {
        JSONObject jsAliveCells = new JSONObject();
        jsAliveCells.put("aliveCellsProm", data.getAliveCellsProm());
        jsAliveCells.put("N", data.getProb());
        jsAliveCells.put("error", data.getFinalAliveError());
        finalAliveCells.add(jsAliveCells);
    }

    public void addMapToJson(GameOfLife gof) {
        JSONObject jsMap = new JSONObject();
        jsMap.put("t", gof.getT());
        JSONArray rows = new JSONArray();
        for (int i = 0; i < gof.getM(); i++) {
            JSONArray col = new JSONArray();
            for (int j = 0; j < gof.getM(); j++) {
                if (gof.getCells().get(i * gof.getM() + j).isAlive()) {
                    col.add(255);
                } else {
                    col.add(0);
                }
            }
            rows.add(col);
        }
        jsMap.put("map", rows);
        maps.add(jsMap);
    }

    public void aliveCellsToJson(GameOfLife gof) {
        JSONObject data = new JSONObject();
        data.put("t", gof.getT());
        data.put("alive", gof.getAliveCells());
        aliveCellsTArray.add(data);
    }

    public void distanceTArrayToJson(GameOfLife gof) {
        JSONObject data = new JSONObject();
        data.put("t", gof.getT());
        data.put("maxDistance", gof.getMaxDistance());
        distanceTArray.add(data);
    }

    public JSONArray getMaps() {
        return maps;
    }

    public JSONArray getAliveCellsTArray() {
        return aliveCellsTArray;
    }

    public JSONArray getDistanceTArray() {
        return distanceTArray;
    }

    public JSONArray getFinalMaxDistances() {
        return finalMaxDistances;
    }

    public JSONArray getFinalAliveCells() {
        return finalAliveCells;
    }
}
