package wprg.babadumextractor;

import wprg.babadumextractor.entities.ColorKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author WPRG
 */
public class BankColorKey {
    
    public static Map<String,ColorKey> colors = new HashMap<>();
    
    public static void loadColors() throws IOException {
        InputStream in = BankColorKey.class.getResourceAsStream("/"+Constants.COLOR_FILE);
        InputStreamReader streamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(streamReader);
        String line = br.readLine();
        while (line!=null) {
            String[] val = line.split(";");
            ColorKey c = new ColorKey();
            c.setWordId(val[0].trim());
            c.setStroke(val[1].trim());
            c.setFill(val[2].trim());
            c.setColorKey(val[3].trim());
            colors.put(c.getColorKey(),c);
//            System.out.println("word="+val[0]+" stroke="+val[1]+" fill="+val[2]);
            line = br.readLine();
        }
    }
    
    public static String getStrokeColor(String colorKey) {
        return colors.get(colorKey).getStroke();
    }
    
    public static String getFillColor(String colorKey) {
        return colors.get(colorKey).getFill();
    }
}
