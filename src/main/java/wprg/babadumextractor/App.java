package wprg.babadumextractor;

import wprg.babadumextractor.entities.Language;
import wprg.comparators.WordComparatorLevel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WPRG
 */
public class App {

    public static void main(String[] args) {
        
        BankWord bw = new BankWord(Language.PORTUGUESE);
        boolean downloads = true;// false to skip downloading files.
        
        try {
            bw.loadWords();
            
            if (downloads) {
                bw.downloadAllPropertyFiles();
                bw.downloadAudioFiles();
            }
            
            bw.saveSvgs();
            bw.generateAnkiFile();
            bw.copySvg();
            bw.copyAudio();
            
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
