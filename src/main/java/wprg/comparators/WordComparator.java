/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.comparators;

import wprg.babadumextractor.entities.Word;
import java.util.Comparator;

/**
 *
 * @author WPRG
 */
public class WordComparator implements Comparator<Word> {
    
    @Override
    public int compare(Word a, Word b) {
        return Integer.parseInt(a.getColorKey()) - Integer.parseInt(b.getColorKey());
    }
    
}
