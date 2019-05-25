package com.projectgalen.jlib;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: R.java
 *     PACKAGE: com.projectgalen.jlib
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 05/20/2019
 * DESCRIPTION:
 *
 * Copyright © 2019 Project Galen. All rights reserved.
 *
 * "It can hardly be a coincidence that no language on Earth has ever produced the expression 'As pretty as an airport.' Airports
 * are ugly. Some are very ugly. Some attain a degree of ugliness that can only be the result of special effort."
 * - Douglas Adams from "The Long Dark Tea-Time of the Soul"
 *
 * Permission to use, copy, modify, and distribute this software for any purpose with or without fee is hereby granted, provided
 * that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * ============================================================================================================================== */

import com.projectgalen.jlib.work.XMLResourceBundleControl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class R {

    private static final ResourceBundle bundle      = ResourceBundle.getBundle("com/projectgalen/jlib/Localization", new XMLResourceBundleControl());
    private static final Pattern        MACRO_REGEX = Pattern.compile("\\$\\{(\\w+(?:\\.\\w+))*}");
    private static final int            STACK_DEPTH = 30; // The stack depth avoids runaway recursion from a circular macro reference.

    public static String format(String format, Object... args) {
        String _format = getStr(format);
        return (Q.Z(_format) ? null : String.format(_format, args));
    }

    public static String formatr(String format, Object... args) {
        if(Q.Z(args.length)) return String.format(format, args);
        Object[] _args = new Object[args.length];
        for(int i = 0; i < args.length; i++) {
            Object o = args[i];
            _args[i] = ((o instanceof Literal) ? ((Literal)o).lit : (Q.Z(o) ? null : getStr(o.toString(), STACK_DEPTH)));
        }
        return format(format, _args);
    }

    public static boolean getBool(String key, boolean defaultValue) { // @f:0
        switch(getStr(key, String.valueOf(defaultValue))) { case "true": return true; case "false": return false; default: return defaultValue; } // @f:1
    }

    public static boolean getBool(String key) {
        return getBool(key, false);
    }

    public static int getInt(String key, int defaultValue) {
        try { return Integer.parseInt(getStr(key, String.valueOf(defaultValue))); } catch(Exception e) { return defaultValue; }
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static String getStr(String key) {
        return getStr(key, STACK_DEPTH);
    }

    public static String getStr(String key, String defaultValue) {
        return Objects.toString(getStr(key, STACK_DEPTH), defaultValue);
    }

    public static Object lit(String str) {
        return (Q.Z(str) ? null : new Literal(str));
    }

    private static Matcher getMatcher(String str) {
        synchronized(MACRO_REGEX) { return MACRO_REGEX.matcher(str); }
    }

    private static String getStr(String key, int stackDepth) {
        try { return ((stackDepth > 0) ? lookForMacros(bundle.getString(key), stackDepth) : bundle.getString(key)); } catch(Exception e) { return null; }
    }

    private static String lookForMacros(String str, int stackDepth) {
        Matcher m = getMatcher(str);
        if(m.find()) {
            StringBuffer sb = new StringBuffer();
            do { m.appendReplacement(sb, Matcher.quoteReplacement(Objects.toString(getStr(m.group(1), (stackDepth - 1)), "null"))); } while(m.find());
            return m.appendTail(sb).toString();
        }
        return str;
    }

    private static final class Literal implements Comparable<Literal> {

        private final String lit;

        private Literal(String str) { lit = str; }

        @Override
        public int compareTo(Literal o) {
            //noinspection StringEquality
            return ((o == null) ? 1 : ((lit == o.lit) ? 0 : ((lit == null) ? -1 : ((o.lit == null) ? 1 : lit.compareTo(o.lit)))));
        }

        @Override
        public int hashCode() { return ((lit == null) ? 0 : lit.hashCode()); }

        @Override
        public boolean equals(Object obj) { return ((obj != null) && ((this == obj) || ((obj instanceof Literal) && (Objects.equals(lit, ((Literal)obj).lit))))); }

        public String toString() { return lit; }

    }

}
