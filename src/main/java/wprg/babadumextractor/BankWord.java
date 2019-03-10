/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.babadumextractor;

import wprg.babadumextractor.entities.Language;
import wprg.babadumextractor.entities.Word;
import wprg.babadumextractor.http.Request;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import wprg.comparators.WordComparatorLevel;

/**
 *
 * @author WPRG
 */
public class BankWord {

    Language lang;
    List<Word> words = new ArrayList<>();

    public BankWord(Language lang) {
        this.lang = lang;
    }

    public void loadWords() throws IOException {
        if (Constants.VERBOSE) {
            System.out.println("Loading words for " + lang.name());
        }
        List<File> files = fileList(Constants.DATA_DIR + "/" + lang.name() + "/" + Constants.PROPERTIES_FOLDER);
        for (File f : files) {
            InputStreamReader in = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
            Properties p = new Properties();
            p.load(in);
            Word w = new Word();
            w.setProperties(p);
            words.add(w);
        }
        if (Constants.VERBOSE) {
            System.out.println("Words loaded for " + lang.name() + " : " + words.size());
        }
    }

    public void sortWords(Comparator<Word> c) {
        Collections.sort(words, c);
    }

    public void generateHtmlFile() throws IOException {
        List<Word> sublist = new ArrayList<>();
        //sublist = words.subList(0, 1);
        String htmlFile = Constants.DATA_DIR
                + "/"
                + "full.xhtml";
        FileWriter fw = new FileWriter(new File(htmlFile));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<body>");
        for (Word w : words) {
            if (w.getId().equals("2781")) {
//            System.out.println(w.getId());
                String img = w.getImg();
                img = img.replaceAll("fill=\"#FFFFFF\"", "fill=\"" + BankColorKey.getFillColor(w.getColorKey()) + "\"");
                img = img.replaceAll("class=\"stroke\"", "class=\"stroke\" " + "fill=\"" + BankColorKey.getStrokeColor(w.getColorKey()) + "\" ");
                bw.append(img);
                bw.newLine();
                bw.append("<span>" + w.getColorKey() + " " + w.getId() + " " + "</span>");
                bw.newLine();
            }
        }
        bw.append("</body>\n"
                + "</html>");
        bw.close();
        fw.close();
    }

    public void updateWordsWithColors() {
        for (Word w : words) {
            w.updateImgWithColors();
        }
    }

    public void saveSvgs() {
        if (Constants.VERBOSE) {
            System.out.println("Saving SVG files for " + lang.name());
            System.out.println("Progress : ");
        }
        int percentage = 0;
        int count = 0;
        for (Word w : words) {
            try {
                w.saveSvg();
            } catch (IOException ex) {
                Logger.getLogger(BankWord.class.getName()).log(Level.SEVERE, null, ex);
            }
            count++;
            int val = (count * 100 / words.size());
            if (val > percentage && val % 10 == 0) {
                percentage = val;
                if (Constants.VERBOSE) {
                    System.out.print(percentage);
                    if (percentage < 100) {
                        System.out.print("%...");
                    } else {
                        System.out.println("%");
                    }
                }
            }
        }
        if (Constants.VERBOSE) {
            System.out.println("SVG files Saved");
        }
    }

    public void generateAnkiFile() throws IOException {
        sortWords(new WordComparatorLevel());
        String folder = Constants.DATA_DIR
                + "/"
                + lang.name()
                + "/"
                + Constants.ANKI_FOLDER;
        String anki = folder
                + "/"
                + Constants.ANKI_FILE;
        new File(folder).mkdirs();
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(anki), StandardCharsets.UTF_8);
        
        //BufferedWriter bw = new BufferedWriter(osw);
        for (Word w : words) {
            String tag = "<img src=\"" + w.getId() + ".svg\"/>";
            String audio = "[sound:" + w.getAudioFilename() + ".ogg]";
            osw.append(tag + ";" + w.getWord() + audio + ";" + w.getLevel() + ";" + audio);
            osw.append("\n");
        }
        osw.close();
    }

    public static List<File> fileList(String directory) {
        List<File> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : directoryStream) {
                File f = path.toFile();
                if (f.isFile()) {
                    fileNames.add(f);
                }
            }
        } catch (IOException ex) {
        }
        return fileNames;
    }

    public void downloadAudioFiles() {
        System.out.println("Downloading audio files for " + lang.name());
        words.forEach(Word::downloadOgg);
        System.out.println("Audio files downloaded for " + lang.name());
    }

    public List<Word> getFourRandomWords() throws IOException {
        File jsonFile = Request.request(lang);
        List<Word> list = JsonParser.parseWords(jsonFile);
        jsonFile.delete();
        return list;
    }

    public void downloadAllPropertyFiles() {
        if (Constants.VERBOSE) {
            System.out.println("Downloading properties for " + lang.name());
        }
        try {
            int find = 0;
            while (find < Constants.MAX_ATTEMPTS) {
                List<Word> list = getFourRandomWords();
                boolean newWords = false;
                for (Word w : list) {
                    if (!words.contains(w)) {
                        words.add(w);
                        w.saveProperties();
                        newWords = true;
                    }
                }
                if (newWords) {
                    System.out.println(find);
                    find = 0;
                } else {
                    find++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BankWord.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Constants.VERBOSE) {
            System.out.println("Download complete. Word count : " + words.size());
        }
    }

    public void copyAudio() {
        File sourceAudio = new File(Constants.DATA_DIR
                + "/"
                + lang.name()
                + "/"
                + Constants.OGG_FOLDER
        );
        File destAudio = new File(Constants.DATA_DIR
                + "/"
                + lang.name()
                + "/"
                + Constants.ANKI_FOLDER
                + "/"
                + Constants.MEDIA_FOLDER
        );
        try {
            FileUtils.copyDirectory(sourceAudio, destAudio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copySvg() {
        File source = new File(Constants.DATA_DIR
                + "/"
                + lang.name()
                + "/"
                + Constants.SVG_FOLDER
        );
        File dest = new File(Constants.DATA_DIR
                + "/"
                + lang.name()
                + "/"
                + Constants.ANKI_FOLDER
                + "/"
                + Constants.MEDIA_FOLDER
        );
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
