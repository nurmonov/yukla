package com.example.yukla.config;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Transliterator {

    // Lotin → Kirill
    private static final Map<String, String> LATIN_TO_CYRILLIC = Map.ofEntries(
            Map.entry("a", "а"), Map.entry("b", "б"), Map.entry("d", "д"),
            Map.entry("e", "е"), Map.entry("f", "ф"), Map.entry("g", "г"),
            Map.entry("h", "ҳ"), Map.entry("i", "и"), Map.entry("j", "ж"),
            Map.entry("k", "к"), Map.entry("l", "л"), Map.entry("m", "м"),
            Map.entry("n", "н"), Map.entry("o", "о"), Map.entry("p", "п"),
            Map.entry("q", "қ"), Map.entry("r", "р"), Map.entry("s", "с"),
            Map.entry("t", "т"), Map.entry("u", "у"), Map.entry("v", "в"),
            Map.entry("x", "х"), Map.entry("y", "й"), Map.entry("z", "з"),
            Map.entry("o'", "ў"), Map.entry("g'", "ғ"), Map.entry("sh", "ш"),
            Map.entry("ch", "ч"), Map.entry("ng", "нг"), Map.entry("'", "ъ"),
            Map.entry("A", "А"), Map.entry("B", "Б"), Map.entry("D", "Д"),
            Map.entry("E", "Е"), Map.entry("F", "Ф"), Map.entry("G", "Г"),
            Map.entry("H", "Ҳ"), Map.entry("I", "И"), Map.entry("J", "Ж"),
            Map.entry("K", "К"), Map.entry("L", "Л"), Map.entry("M", "М"),
            Map.entry("N", "Н"), Map.entry("O", "О"), Map.entry("P", "П"),
            Map.entry("Q", "Қ"), Map.entry("R", "Р"), Map.entry("S", "С"),
            Map.entry("T", "Т"), Map.entry("U", "У"), Map.entry("V", "В"),
            Map.entry("X", "Х"), Map.entry("Y", "Й"), Map.entry("Z", "З"),
            Map.entry("O'", "Ў"), Map.entry("G'", "Ғ"), Map.entry("Sh", "Ш"),
            Map.entry("Ch", "Ч"), Map.entry("Ng", "НГ")
    );

    // Kirill → Lotin (oddiyroq)
    private static final Map<String, String> CYRILLIC_TO_LATIN = Map.ofEntries(
            Map.entry("а", "a"), Map.entry("б", "b"), Map.entry("в", "v"),
            Map.entry("г", "g"), Map.entry("д", "d"), Map.entry("е", "e"),
            Map.entry("ё", "yo"), Map.entry("ж", "j"), Map.entry("з", "z"),
            Map.entry("и", "i"), Map.entry("й", "y"), Map.entry("к", "k"),
            Map.entry("л", "l"), Map.entry("м", "m"), Map.entry("н", "n"),
            Map.entry("о", "o"), Map.entry("п", "p"), Map.entry("р", "r"),
            Map.entry("с", "s"), Map.entry("т", "t"), Map.entry("у", "u"),
            Map.entry("ф", "f"), Map.entry("х", "x"), Map.entry("ц", "ts"),
            Map.entry("ч", "ch"), Map.entry("ш", "sh"), Map.entry("ъ", "'"),
            Map.entry("ы", "y"), Map.entry("ь", ""), Map.entry("э", "e"),
            Map.entry("ю", "yu"), Map.entry("я", "ya"),
            Map.entry("қ", "q"), Map.entry("ғ", "g'"), Map.entry("ҳ", "h"),
            Map.entry("ў", "o'"), Map.entry("Ў", "O'"), Map.entry("Ғ", "G'"),
            Map.entry("Қ", "Q"), Map.entry("Ҳ", "H")
    );

    /**
     * Lotin → Kirill
     */
    public String toCyrillic(String text) {
        if (text == null || text.isBlank()) return text;

        String result = text.toLowerCase();
        for (Map.Entry<String, String> entry : LATIN_TO_CYRILLIC.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }

        // Birinchi harfni katta qilish
        if (!result.isEmpty()) {
            result = result.substring(0, 1).toUpperCase() + result.substring(1);
        }
        return result;
    }

    /**
     * Kirill → Lotin
     */
    public String toLatin(String text) {
        if (text == null || text.isBlank()) return text;

        String result = text.toLowerCase();
        for (Map.Entry<String, String> entry : CYRILLIC_TO_LATIN.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }

        if (!result.isEmpty()) {
            result = result.substring(0, 1).toUpperCase() + result.substring(1);
        }
        return result;
    }

    /**
     * Avtomatik aniqlash va o‘tkazish
     */
    public String autoTransliterate(String text) {
        if (text == null || text.isBlank()) return text;

        // Agar matnda kirill harflari bo‘lsa
        if (text.matches(".*[а-яА-ЯёЁўЎғҒҳҲқҚ].*")) {
            return toLatin(text);
        } else {
            return toCyrillic(text);
        }
    }
}
