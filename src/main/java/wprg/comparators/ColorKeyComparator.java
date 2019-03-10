/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.comparators;

import wprg.babadumextractor.entities.ColorKey;
import java.util.Comparator;

/**
 *
 * @author WPRG
 */
public class ColorKeyComparator implements Comparator<ColorKey> {

    @Override
    public int compare(ColorKey o1, ColorKey o2) {
        if (o1.getColorKey() == null || o2.getColorKey() == null) {
            return 0;
        }
        return Integer.parseInt(o1.getColorKey()) - (Integer.parseInt(o2.getColorKey()));
    }
}
