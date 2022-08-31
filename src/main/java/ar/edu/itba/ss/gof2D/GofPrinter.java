package ar.edu.itba.ss.gof2D;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GofPrinter {
    private JSONArray maps;
    private JSONArray aliveCellsTArray;
    private JSONArray distanceTArray;

    public GofPrinter() {
        aliveCellsTArray = new JSONArray();
        distanceTArray = new JSONArray();
        maps = new JSONArray();
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
}
