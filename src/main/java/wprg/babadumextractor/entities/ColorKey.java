/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.babadumextractor.entities;

/**
 *
 * @author WPRG
 */
public class ColorKey {
    
    String colorKey;
    String wordId;
    String stroke;
    String fill;

    public String getColorKey() {
        return colorKey;
    }

    public void setColorKey(String colorKey) {
        this.colorKey = colorKey;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    @Override
    public String toString() {
        return "ColorKey{" + "colorKey=" + colorKey + ", wordId=" + wordId + ", stroke=" + stroke + ", fill=" + fill + '}';
    }
    
    
}
