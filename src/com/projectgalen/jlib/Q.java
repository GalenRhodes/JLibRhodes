package com.projectgalen.jlib;

import com.projectgalen.jlib.strings.regex.RX;
import com.projectgalen.jlib.strings.regex.RegexHelper;
import com.projectgalen.jlib.work.Homer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;

@SuppressWarnings("HardCodedStringLiteral")
public final class Q {

    public static final  String FORMAT_REGEX                 = "\\%(?:((\\d+)\\$|<)?(([ #+0,(!-]*)(\\d*(?:\\.\\d+)?)([abcdefghosxABCEHSX]|([tT])([abcdehjklmprsyzABCDFHILMNQRSTYZ])))|[n%])";
    public static final  String Z                            = ".$|()[{^?*+\\";

    private Q()                                 {}

    public static boolean NZ(int expression)    { return (expression != 0); }

    public static boolean NZ(double expression) { return (expression != 0.0); }

    public static boolean NZ(Double expression) { return ((expression != null) && (expression != 0.0)); }

    public static boolean NZ(Float expression)  { return ((expression != null) && (expression != 0.0f)); }

    public static boolean NZ(Number expression) { return ((expression != null) && (expression.intValue() != 0)); }

    public static boolean NZ(Object expression) { return (expression != null); }

    public static boolean Z(int expression)     { return (expression == 0); }

    public static boolean Z(double expression)  { return (expression == 0.0); }

    public static boolean Z(Double expression)  { return ((expression == null) || (expression == 0.0)); }

    public static boolean Z(Float expression)   { return ((expression == null) || (expression == 0.0f)); }

    public static boolean Z(Number expression)  { return ((expression == null) || (expression.intValue() == 0)); }

    public static boolean Z(Object expression)  { return (expression == null); }

    public static String cap(String s) {
        return ((Z(s)) ? null : _cap(s.trim()));
    }

    /**
     * My own version of {@link String#format(String, Object...)} that includes some enhancements.
     *
     * One enhancement is the '!' flag for strings. Including it for string will capitalize the replacement string. So, for example:
     *
     * String s = Q.format("My name is %!s.", "galen");
     *
     * Would result in the string "My name is Galen."
     *
     * @param format The format string. See {@link String#format(String, Object...)}.
     * @param args   The arguments.  See {@link String#format(String, Object...)}.
     *
     * @return The formatted string.
     */
    public static String format(String format, Object... args) {
        Homer  hdlr = new Homer(args);
        String fmt  = new RegexHelper(FORMAT_REGEX, format).replaceAll(hdlr);
        return String.format(fmt, hdlr.getNewArgs());
    }

    /**
     * Join the string value of the items in the array with the value of the character {@code sep} to form a new string. If the array is {@code null} then a {@code null} string is
     * returned. If the array is empty then an empty string is returned. If the array only has one item in it then the string value of that object is returned.
     *
     * @param items the array of objects. The method {@link Object#toString()} will be called on each item.
     * @param sep   the character to place between the string value of each item in the array.
     *
     * @return A new string that is a concatenation of the string values of the items in the array separated by the given character value {@code sep}.
     */
    public static String join(Object[] items, char sep) {
        return join(items, String.valueOf(sep));
    }

    /**
     * Join the string value of the items in the array with the value of the string {@code sep} to form a new string. If the array is {@code null} then a {@code null} string is
     * returned. If the array is empty then an empty string is returned. If the array only has one item in it then the string value of that object is returned. If any item in the
     * array is null or it's {@link Object#toString()} method returns {@code null} then an empty string will be substituted in it's place.
     *
     * @param items the array of objects. The method {@link Object#toString()} will be called on each item.
     * @param sep   the string to place between the string value of each item in the array.
     *
     * @return A new string that is a concatenation of the string values of the items in the array separated by the given string value {@code sep}.
     */
    public static String join(Object[] items, String sep) {
        return join(items, sep, "");
    }

    /**
     * Join the string value of the items in the array with the value of the string {@code sep} to form a new string. If the array is {@code null} then a {@code null} string is
     * returned. If the array is empty then an empty string is returned. If the array only has one item in it then the string value of that object is returned.
     *
     * @param items        the array of objects. The method {@link Object#toString()} will be called on each item.
     * @param sep          the string to place between the string value of each item in the array.
     * @param defaultValue the default string value to use if any of the items in the array is {@code null}.
     *
     * @return A new string that is a concatenation of the string values of the items in the array separated by the given string value {@code sep}.
     */
    public static String join(Object[] items, String sep, String defaultValue) {
        if(Z(sep)) throw new NullPointerException(R.getStr(C.ERR_MSG_SEP_STR_NULL));
        if(Z(defaultValue)) throw new NullPointerException(R.getStr(C.ERR_MSG_DEF_STR_NULL));
        if(Z(items)) return null;
        if(Q.Z(items.length)) return "";
        if(items.length == 1) return Objects.toString(items[0], defaultValue);
        StringBuilder sb = new StringBuilder().append(Objects.toString(items[0], defaultValue));
        for(int i = 1; i < items.length; i++) sb.append(sep).append(Objects.toString(items[i], defaultValue));
        return sb.toString();
    }

    /**
     * Join the string value of the items in the collection with the value of the string {@code sep} to form a new string. If the collection is {@code null} then a {@code null}
     * string is returned. If the collection is empty then an empty string is returned. If the collection only has one item in it then the string value of that object is returned.
     * If an item is {@code null} then the value of {@code defaultValue} will be substitued in it's place.
     *
     * @param items        the collection of objects. The method {@link Object#toString()} will be called on each item.
     * @param sep          the string to place between the string value of each item in the collection.
     * @param defaultValue the default string value to use if any of the items in the collection is {@code null}.
     *
     * @return A new string that is a concatenation of the string values of the items in the collection separated by the given string value {@code sep}.
     */
    public static String join(Collection<?> items, String sep, String defaultValue) {
        if(Z(sep)) throw new NullPointerException(R.getStr(C.ERR_MSG_SEP_STR_NULL));
        if(Z(defaultValue)) throw new NullPointerException(R.getStr(C.ERR_MSG_DEF_STR_NULL));
        if(Z(items)) return null;

        Iterator<?> it = items.iterator();
        if(!it.hasNext()) return "";

        StringBuilder sb = new StringBuilder().append(Objects.toString(it.next(), defaultValue));
        while(it.hasNext()) sb.append(sep).append(Objects.toString(it.next(), defaultValue));
        return sb.toString();
    }

    /**
     * Join the string value of the items in the collection with the value of the string {@code sep} to form a new string. If the collection is {@code null} then a {@code null}
     * string is returned. If the collection is empty then an empty string is returned. If the collection only has one item in it then the string value of that object is returned.
     * If an item is {@code null} then it will be replaced with an empty string.
     *
     * @param items the collection of objects. The method {@link Object#toString()} will be called on each item.
     * @param sep   the string to place between the string value of each item in the collection.
     *
     * @return A new string that is a concatenation of the string values of the items in the collection separated by the given string value {@code sep}.
     */
    public static String join(Collection<?> items, String sep) {
        return join(items, sep, "");
    }

    public static String join(Collection<?> items, char sep) {
        return join(items, String.valueOf(sep), "");
    }

    public static boolean ni(char ch, String string) {
        return string.indexOf(ch) == -1;
    }

    public static boolean ni(char c, char cl, char ch) {
        return (((c - cl) | (ch - c)) < 0);
    }

    public static int parseInt(String str, int defaultValue) {
        int i = -1;
        try { i = Integer.parseInt(str); } catch(Exception e) { i = defaultValue; }
        return i;
    }

    public static String remove(String str, char ch) {
        StringBuilder sb = new StringBuilder();
        int           i  = str.indexOf(ch);
        int           j  = 0;

        while(i >= 0) {
            if(i > j) sb.append(str, i, j);
            i = str.indexOf(ch, (j = (i + 1)));
        }

        if(j < str.length()) sb.append(str, j, str.length());
        return sb.toString();
    }

    public static String[] split(String pattern, CharSequence charSequence, int limit) {
        if(limit == 1) return new String[] {charSequence.toString()};
        if(charSequence instanceof String) return ssplit(pattern, (String)charSequence, limit);
        return _split(pattern, charSequence, limit);
    }

    public static String[] split(String pattern, CharSequence charSequence) {
        return split(pattern, charSequence, 0);
    }

    private static String _cap(String s, char c1, char c2) {
        return ((Character.isHighSurrogate(c1) && Character.isLowSurrogate(c2)) ? _cap(s, Character.toCodePoint(c1, c2)) : Character.toUpperCase(c1) + s.substring(1));
    }

    private static String _cap(String s, int cp) {
        return (Character.isValidCodePoint(cp) ? String.valueOf(Character.toChars(Character.toUpperCase(cp))) + s.substring(2) : s);
    }

    private static String _cap(String s) {
        return ((s.length() > 1) ? _cap(s, s.charAt(0), s.charAt(1)) : s.toUpperCase());
    }

    private static String[] _split(String pattern, CharSequence charSequence, int limit) {
        Matcher m = RX.getMatcherForRegex(pattern, charSequence);
        return (m.find() ? _split(m, charSequence, (((limit > 0) ? limit : Integer.MAX_VALUE) - 1), (limit == 0)) : new String[] {charSequence.toString()});
    }

    private static String[] _split(Matcher m, CharSequence charSequence, int limit, boolean trim) {
        StringArray array = new StringArray();
        int         offs  = 0;

        do {
            int next = m.start(), end = m.end();
            if(Q.Z(offs) && Q.Z(next) && Q.Z(end)) continue;
            array.add(charSequence, offs, next);
            offs = end;
        }
        while((array.length() < limit) && m.find());

        array.add(charSequence, offs, charSequence.length());
        if(trim) array.trim();
        return array.getArray();
    }

    private static char c(String string, int idx) {
        return string.charAt(idx);
    }

    private static boolean s(char c) {
        return !Character.isSurrogate(c);
    }

    private static String[] ssplit(String regex, String string, int limit) {
        int r = zzTop(regex);
        return ((r < 0) ? _split(regex, string, limit) : ssplit(string, (char)r, limit));
    }

    private static String[] ssplit(String string, char ch, int limit) {
        int next = string.indexOf(ch);
        return ((next < 0) ? new String[] {string} : ssplit(string, ch, next, ((limit > 0) ? limit : Integer.MAX_VALUE), (limit == 0)));
    }

    private static String[] ssplit(String string, char ch, int next, int limit, boolean trim) {
        int         off  = 0;
        StringArray list = new StringArray();

        do {
            list.add(string, off, next);
            next = string.indexOf(ch, (off = (next + 1)));
        }
        while((next >= 0) && (list.length() < (limit - 1)));

        list.add(string, off, string.length());
        if(trim) list.trim();
        return list.getArray();
    }

    private static int zzTop(String rx) {
        boolean l1 = (rx.length() == 1);
        boolean l2 = (rx.length() == 2);
        char    c  = 0;
        return ((((l1 && ni((c = c(rx, 0)), Q.Z)) || (l2 && (c(rx, 0) == '\\') && ni((c = c(rx, 1)), '0', '9') && ni(c, 'a', 'z') && ni(c, 'A', 'Z'))) && s(c)) ? (int)c : -1);
    }

    private static final class StringArray {

        private String[] strArray = new String[10];
        private int      arrayIdx = 0;

        public StringArray() {}

        public void add(String str, int start, int end) {
            add((start == end) ? "" : str.substring(start, end));
        }

        public void add(CharSequence charSequence, int start, int end) {
            add((start == end) ? "" : charSequence.subSequence(start, end).toString());
        }

        public String[] getArray() {
            return ((arrayIdx == strArray.length) ? strArray : Arrays.copyOf(strArray, arrayIdx));
        }

        public int length() {
            return arrayIdx;
        }

        public void trim() {
            while((arrayIdx > 0) && (strArray[arrayIdx - 1].length() == 0)) arrayIdx--;
        }

        private void add(String str) {
            if(arrayIdx == strArray.length) strArray = Arrays.copyOf(strArray, (int)Math.ceil(strArray.length * 1.75));
            strArray[arrayIdx++] = str;
        }

    }

}
