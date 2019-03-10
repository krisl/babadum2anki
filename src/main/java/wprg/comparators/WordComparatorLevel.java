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
public class WordComparatorLevel implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return o1.getLevel().compareTo(o2.getLevel());
    }
    
    
}
