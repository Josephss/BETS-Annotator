/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BETS_exctractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class takes in a JSON file and extracts the name and description of the
 * tools as an arrayList.
 *
 * @author Joseph
 */
public class betsExtractor {

    static ArrayList<String> Names = new ArrayList<String>();
    static ArrayList<String> Descriptions = new ArrayList<String>();

    public static void jsonRead(String json) throws FileNotFoundException, IOException, JSONException {

        JSONObject obj = new JSONObject(json);
        System.out.println("jArray_5: " + obj.getJSONArray("tool").get(5));

        JSONArray jArray = obj.getJSONArray("tool");
        System.out.println("Array length: " + jArray.length() + "jArray:" + jArray.toString());

        System.out.println("jObject: " + obj.getString("tool"));

        System.out.println();

        ArrayList<String> names;
        names = new ArrayList<>();

        ArrayList<String> description;
        description = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            // System.out.println(jArray.get(i));
            JSONObject temp = new JSONObject(jArray.get(i).toString());
            //System.out.println("temp.toString();" + temp.get("summary"));
            names.add(temp.getString("name"));
            description.add(temp.getString("description"));
        }
        /*
         We can use a parrallel array to match the name and description of the tools
         */
        // System.out.println("names: " + names.toString());
        Names = names;
        // System.out.println("description: " + description.toString());
        Descriptions = description;
        //JSONArray jArray = new JSONArray(obj.getJSONArray("tool"));
        //System.out.println("jArray: " + jArray);
        //System.out.println("TEST: " + obj.getString("name"));

    }

    public static ArrayList<String> getNames() {
        return Names;
    }

    public static ArrayList<String> getDescriptions() {
        return Descriptions;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, JSONException {
        String json = "";
        String line;

        FileReader fileReader;
        fileReader = new FileReader("C:/Working/tools_structured.json");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            json = json + line;
        }
        System.out.println(json);
        jsonRead(json);
        System.out.println(getNames());
        System.out.println(getDescriptions());
    }
}
