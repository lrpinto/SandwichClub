package com.udacity.sandwichclub.utils;

import android.util.JsonReader;

import com.udacity.sandwichclub.model.Sandwich;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JsonReader reader = new JsonReader(new StringReader(json));
        try {
            return readSandwich(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Sandwich readSandwich(JsonReader reader) throws IOException {

        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                Map<String, Object> sandwichName = readSandwichName(reader);
                mainName = (String) sandwichName.get("mainName");
                alsoKnownAs = (List<String>) sandwichName.get("alsoKnownAs");
            } else if (name.equals("placeOfOrigin")) {
                placeOfOrigin = reader.nextString();
            } else if (name.equals("description")) {
                description = reader.nextString();
            } else if (name.equals("image")) {
                image = reader.nextString();
            } else if (name.equals("ingredients")) {
                ingredients = readSandwichIngredients(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    public static Map<String, Object> readSandwichName(JsonReader reader) throws IOException {
        String mainName = null;
        List<String> alsoKnownAs = new LinkedList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("mainName")) {
                mainName = reader.nextString();
            } else if (name.equals("alsoKnownAs")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    alsoKnownAs.add(reader.nextString());
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        Map name = new HashMap<String, Object>();
        name.put("mainName", mainName);
        name.put("alsoKnownAs", alsoKnownAs);

        return name;
    }

    public static List<String> readSandwichIngredients(JsonReader reader) throws IOException {

        List<String> readIngredients = new LinkedList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            readIngredients.add(reader.nextString());
        }
        reader.endArray();

        return readIngredients;
    }
}
