/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractor;

import wprg.babadumextractor.BankWord;
import wprg.babadumextractor.entities.Language;
import wprg.babadumextractor.entities.Word;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author WPRG
 */
public class DownloadingTests {
    
    @Test
    public void downloadFourWords() {
        BankWord bw = new BankWord(Language.FRENCH);
        try {
            List<Word> list = bw.getFourRandomWords();
            for (Word w:list) {
                //System.out.println(w);
            }
        } catch (IOException ex) {
            Logger.getLogger(DownloadingTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void saveFourWords() {
        BankWord bw = new BankWord(Language.JAPANESE);
        try {
            List<Word> list = bw.getFourRandomWords();
            for (Word w:list) {
                w.saveProperties();
            }
        } catch (IOException ex) {
            Logger.getLogger(DownloadingTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
