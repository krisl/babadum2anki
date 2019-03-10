/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.babadumextractor.entities;

import wprg.babadumextractor.BankColorKey;
import wprg.babadumextractor.Constants;
import wprg.babadumextractor.App;
import wprg.babadumextractor.http.HttpApacheTest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WPRG
 */
public class Word {

    Properties values = new Properties();
    static int already = 0;

    public void saveWord() throws IOException {
        String propertiesFolder = Constants.DATA_DIR + "/" + Constants.PROPERTIES_FOLDER;
        File f = new File(propertiesFolder + "/" + values.getProperty(WordPropertyString.id.name()) + ".txt");
        if (f.exists()) {
            already++;
            System.out.println(already);
            System.out.println(new Date() + "Word already recorded : " + values.getProperty(WordPropertyString.word.name()) + " " + values.getProperty(WordPropertyString.id.name()));
        } else {
            System.out.println("");
            System.out.println(new Date() + " New word recorded : " + values.getProperty(WordPropertyString.word.name()) + " " + values.getProperty(WordPropertyString.id.name()));
            saveProperties();
            saveHtml();
            saveSvg();
        }
    }

    public void downloadOgg() {
        String url = "https://babadum.com/audio/"
                + getLanguage().code
                + "/" + getAudioFilename() + ".ogg";
        String folder = Constants.DATA_DIR
                + "/"
                + getLanguage().name()
                + "/"
                + Constants.OGG_FOLDER;
        new File(folder).mkdirs();
        try {
            HttpApacheTest.downloadFile(url, folder + "/" + getAudioFilename() + ".ogg");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveProperties() throws IOException {
        File folder = new File(Constants.DATA_DIR + "/" + getLanguage().name().toLowerCase() + "/" + Constants.PROPERTIES_FOLDER);
        folder.mkdirs();
        OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(
                        new File(
                                //        FileWriter fw = new FileWriter(
                                Constants.DATA_DIR + "/" + getLanguage().name().toLowerCase() + "/" + Constants.PROPERTIES_FOLDER + "/" + values.getProperty(WordPropertyString.id.name()) + ".txt"
                        //        );
                        )), StandardCharsets.UTF_8);
        values.store(osw, "");
    }

    public void saveSvg() throws IOException {
        BankColorKey.loadColors();
        updateImgWithColors();
        String folder = Constants.DATA_DIR
                + "/"
                + getLanguage().name()
                + "/"
                + Constants.SVG_FOLDER;
        String svgFile = folder
                + "/"
                + values.getProperty(WordPropertyString.id.name())
                + ".svg";
        File f = new File(svgFile);
        new File(folder).mkdirs();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        String img = values.getProperty(WordPropertyString.img.name());
        bw.write(img, 0, img.length());
        bw.close();
        fw.close();
    }

    public void saveHtml() throws IOException {
        String htmlFile = Constants.DATA_DIR
                + "/"
                + Constants.HTML_FOLDER
                + "/"
                + values.getProperty(WordPropertyString.id.name())
                + ".xhtml";
        FileWriter fw = new FileWriter(new File(htmlFile));
        BufferedWriter bw = new BufferedWriter(fw);
        String img = values.getProperty(WordPropertyString.img.name());
        bw.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<body>");
        bw.append(img);
        bw.append("</body>\n"
                + "</html>");
        bw.close();
    }

    /**
     * REQUIRES LOADED COLORS
     */
    public void updateImgWithColors() {
        String img = getImg();
        img = img.replaceAll("fill=\"#FFFFFF\"", "fill=\"" + BankColorKey.getFillColor(getColorKey()) + "\"");
        img = img.replaceAll("class=\"stroke\"", "class=\"stroke\" " + "fill=\"" + BankColorKey.getStrokeColor(getColorKey()) + "\" ");
        setImg(img);
    }

    public void setProperty(WordPropertyString wp, String value) {
        values.put(wp.name(), value);
    }

    public void setProperty(WordPropertyInt wp, int value) {
        values.put(wp.name(), Integer.toString(value));
    }

    public void setProperties(Properties p) {
        values = p;
    }

    public String getImg() {
        return values.getProperty(WordPropertyString.img.name());
    }

    public String getLevel() {
        return values.getProperty(WordPropertyString.level.name());
    }

    public String getWord() {
        return values.getProperty(WordPropertyString.word.name());
    }

    public String getId() {
        return values.getProperty(WordPropertyString.id.name());
    }

    public Language getLanguage() {
        return Language.from(getLangKey());
    }

    public String getLangKey() {
        return values.getProperty(WordPropertyInt.lang_key.name());
    }

    public String getColorKey() {
        return values.getProperty(WordPropertyString.color_key.name());
    }

    private void setImg(String img) {
        values.put(WordPropertyString.img.name(), img);
    }

    public String getAudioFilename() {
        return values.getProperty(WordPropertyString.audio_file.name());
    }

    @Override
    public String toString() {
        return "Word{" + "values=" + values + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        if (!Objects.equals(this.getWord(), other.getWord())) {
            return false;
        }
        return true;
    }

    public String getWordKey() {
        return values.getProperty(WordPropertyString.word_key.name());
    }

}
