/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.babadumextractor;

import wprg.babadumextractor.entities.WordPropertyString;
import wprg.babadumextractor.entities.Word;
import wprg.babadumextractor.entities.WordPropertyInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author WPRG
 */
public class JsonParser {

    public static List<Word> parseWords(File file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String json = br.readLine();
        JSONObject obj = new JSONObject(json);

        List<Word> list = new ArrayList<>();

        JSONObject data = obj.getJSONObject("data");
        JSONArray words = data.getJSONArray("words");
        for (int i = 0; i < words.length(); i++) {
            Word w = new Word();
            for (WordPropertyString wp : WordPropertyString.values()) {
                w.setProperty(wp, words.getJSONObject(i).getString(wp.name()));
            }
            for (WordPropertyInt wp : WordPropertyInt.values()) {
                w.setProperty(wp, words.getJSONObject(i).getInt(wp.name()));
            }
            WordPropertyInt a;
            list.add(w);
        }
        br.close();
        fr.close();
        return list;
    }
}
