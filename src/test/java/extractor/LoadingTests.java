/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractor;

import wprg.babadumextractor.BankWord;
import wprg.babadumextractor.Constants;
import wprg.babadumextractor.entities.Language;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 *
 * @author WPRG
 */
public class LoadingTests {
    
    public LoadingTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void loadColors() throws IOException {
        InputStream in = getClass().getResourceAsStream("/"+Constants.COLOR_FILE);
        InputStreamReader streamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(streamReader);
        String line = null;
        int count = 32;
        while ((line = br.readLine())!=null) {
            count--;
        }
        Assert.assertEquals(0, count);
    }
    
    @Test
    public void listResources() throws IOException {
        Reflections reflections = new Reflections("properties", new ResourcesScanner());
        Set<String> resources = reflections.getResources(Pattern.compile(".*\\.txt"));
    }
    
    @Test
    public void loadWords() throws IOException {
        BankWord bw = new BankWord(Language.GERMAN);
        bw.loadWords();
    }

}
