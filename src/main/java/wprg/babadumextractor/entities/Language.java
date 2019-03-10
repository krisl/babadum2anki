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
public enum Language {

    
    ENGLISH(1,"us"),
    POLISH(2, "pl"),
    GERMAN(3, "de"),
    SPANISH(4, "es"),
    FRENCH(5, "fr"),
    ITALIAN(6, "it"),
    RUSSIAN(8, "ru"),
    JAPANESE(9, "jp"),
    SWEDISH(10, "se"),
    GREEK(11, "gr"),
    DANISH(12, "da"),
    CHINESE(13, "cn"),
    CZECH(14, "cz"),
    LITHUANIAN(15, "lt"),
    PORTUGUESE(16, "pt"),
    ROMANIAN(18, "ro"),
    DUTCH(19, "nl"),
    AFRIKAANS(20, "af"),
    NORWEGIAN(22, "no"),
    HUNGARIAN(23, "hu"),
    ESPERANTO(24, "eo"),
    TURKISH(25, "tr"),
    UKRANIAN(26, "uk"),
    FINNISH(27, "fi"),
    SERBIAN(28, "sr");
    
    public int key;
    public String code;
    
    Language(int k, String code) {
        key = k;
        this.code = code;
    }
    
    public static Language from(int key) {
        for (Language l:values()) if (l.key==key) return l;
        return null;
    }
    
    public static Language from(String key) {
        return from(Integer.parseInt(key));
    }
}
