/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.babadumextractor.http;

import wprg.babadumextractor.App;
import wprg.babadumextractor.entities.Language;
import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yoann
 */
public class Request {
    
    static String url = "https://babadum.com/get.php";
    static Properties parameters = new Properties();
    
    static HttpApacheTest hcw = new HttpApacheTest();
    
    static {
        parameters.put("type", "getWord");
        parameters.put("onReceiveLocation", "Words");
        parameters.put("game", "chooseImage");
        parameters.put("random", "1549802497255");
        parameters.put("connectId", "8");
        parameters.put("category", "words");
    }
    
    public static File request(Language lang) {
        parameters.put("lang", lang.key+"");
        try {
            System.out.print(".");
            return hcw.post(url, parameters);
        } catch (ProtocolException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
