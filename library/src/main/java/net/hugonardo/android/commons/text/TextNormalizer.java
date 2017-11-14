package net.hugonardo.android.commons.text;

import android.annotation.TargetApi;
import android.os.Build;
import java.text.Normalizer;

public class TextNormalizer {

    public static String removeAccents(String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return removeAccentsWithNormalizer(input);
        } else {
            return removeAccentsCompat(input);
        }
    }

    private static String removeAccentsCompat(String input) {
        input = input.replaceAll("[àáâäã]", "a");
        input = input.replaceAll("[ÀÁÂÄÃ]", "A");

        input = input.replaceAll("[èéêë]", "e");
        input = input.replaceAll("[ÈÉÊË]", "E");

        input = input.replaceAll("[ïîíì]", "i");
        input = input.replaceAll("[ÏÎÍÌ]", "I");

        input = input.replaceAll("[òóôöõ]", "o");
        input = input.replaceAll("[ÒÓÔÖÕ]", "O");

        input = input.replaceAll("[ùúûü]", "u");
        input = input.replaceAll("[ÙÚÛÜ]", "U");

        input = input.replaceAll("[ç]", "c");
        input = input.replaceAll("[Ç]", "C");

        input = input.replaceAll("[ñ]", "n");
        input = input.replaceAll("[Ñ]", "N");

        return input;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static String removeAccentsWithNormalizer(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
