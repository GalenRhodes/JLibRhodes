package com.projectgalen.jlib.strings;

import com.projectgalen.jlib.C;
import com.projectgalen.jlib.Q;
import com.projectgalen.jlib.R;

import java.util.Arrays;

public class StringScanner {

    public static final String IOOB_MSG = "%d >= %d";

    private final char[] strChars;
    private       int    nextIdx;
    private       int    currIdx;

    public StringScanner(String s) {
        strChars = ((Q.Z(s)) ? new char[0] : s.toCharArray());
        nextIdx = 0;
        currIdx = -1;
    }

    public String beforeCurrent() {
        return ((currIdx <= 0) ? "" : String.valueOf(Arrays.copyOfRange(strChars, 0, currIdx)));
    }

    public String beforeNext() {
        return ((nextIdx == 0) ? "" : String.valueOf(Arrays.copyOfRange(strChars, 0, nextIdx)));
    }

    public String currentAndBeyond() {
        return String.valueOf((currIdx <= 0) ? strChars : Arrays.copyOfRange(strChars, currIdx, (strChars.length - currIdx)));
    }

    public int getCurrentIndex() {
        return currIdx;
    }

    public int getNextIndex() {
        return nextIdx;
    }

    public boolean hasNext() {
        return (nextIdx < length());
    }

    public int length() {
        return strChars.length;
    }

    public int next() {
        if(!hasNext()) throw new IndexOutOfBoundsException(String.format(IOOB_MSG, getNextIndex(), length()));
        currIdx = nextIdx;
        return getNext(getNextCharacter());
    }

    public String nextAndBeyond() {
        return String.valueOf((nextIdx == 0) ? strChars : Arrays.copyOfRange(strChars, nextIdx, (strChars.length - nextIdx)));
    }

    public String toString() {
        return String.valueOf(strChars);
    }

    private int getCodePoint(char ch1, char ch2) {
        if(!Character.isLowSurrogate(ch2)) throw unmatchedSurrogateException(getCurrentIndex());
        int codePoint = Character.toCodePoint(ch1, ch2);
        if(Character.isValidCodePoint(codePoint)) return codePoint;
        throw invalidCodePointException(getCurrentIndex());
    }

    private int getNext(char ch) {
        if(Character.isLowSurrogate(ch)) throw unmatchedSurrogateException(getCurrentIndex());
        if(!Character.isHighSurrogate(ch)) return ch;
        if(hasNext()) return getCodePoint(ch, getNextCharacter());
        throw unmatchedSurrogateException(getCurrentIndex());
    }

    private char getNextCharacter() {
        return strChars[nextIdx++];
    }

    private IllegalStateException illegalCharacterException(int idx, String reason) {
        return new IllegalStateException(R.format(C.ERR_MSG_ILLEGAL_CHAR_AT_IDX, idx, reason));
    }

    private IllegalStateException invalidCodePointException(int idx) {
        return illegalCharacterException(idx, R.getStr(C.ERR_MSG_INVALID_CODE_POINT));
    }

    private IllegalStateException unmatchedSurrogateException(int idx) {
        return illegalCharacterException(idx, R.getStr(C.ERR_MSG_UNMATCHED_SURROGATE));
    }

}
