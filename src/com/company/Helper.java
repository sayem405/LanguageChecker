package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sayem on 3/29/2017.
 */
public class Helper {
    public static int getFormattingCount(String value, boolean isRtl) {

        String decimalFormatting = isRtl ? "d%|%[0-9]*\\$*d" : "%[0-9]*\\$*d";
        String stringFormatting = isRtl ? "s%|%[0-9]*\\$*s" : "%[0-9]*\\$*s";

        int count = 0;
        Pattern p = Pattern.compile(decimalFormatting);
        Matcher m = p.matcher(value);

        while (m.find()) {
            count += 1;
        }

        p = Pattern.compile(stringFormatting);
        m = p.matcher(value);

        while (m.find()) {
            count += 1;
        }
        return count;
    }
}
