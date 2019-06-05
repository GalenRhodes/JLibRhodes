package com.projectgalen.jlib.utils;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: Comparison.java
 *     PACKAGE: com.projectgalen.jlib.utils
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 06/05/2019
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

import java.util.Comparator;

public enum Comparison {
    LESS_THAN(-1), EQUAL(0), GREATER_THAN(1);

    private int intValue;

    Comparison(int cc) {
        intValue = cc;
    }

    public final int intValue() {
        return intValue;
    }

    public static final Comparison fromIntValue(int cc) {
        return ((cc == 0) ? Comparison.EQUAL : ((cc < 0) ? Comparison.LESS_THAN : Comparison.GREATER_THAN));
    }

    public static final <T extends Comparable<T>> Comparison compare(T o1, T o2) {
        return fromIntValue((o1 == o2) ? 0 : ((o1 == null) ? -1 : ((o2 == null) ? 1 : o1.compareTo(o2))));
    }

    public static final <T> Comparison compare(T o1, T o2, Comparator<T> comparator) {
        return fromIntValue((o1 == o2) ? 0 : ((o1 == null) ? -1 : ((o2 == null) ? 1 : comparator.compare(o1, o2))));
    }

    public static final Comparison compare(boolean b1, boolean b2) {
        return ((b1 == b2) ? Comparison.EQUAL : (b1 ? Comparison.GREATER_THAN : Comparison.LESS_THAN));
    }

    public static final Comparison compare(byte n1, byte n2) {
        return ((n1 == n2) ? Comparison.EQUAL : ((n1 < n2) ? Comparison.LESS_THAN : Comparison.GREATER_THAN));
    }

    public static final Comparison compare(char n1, byte n2) {
        return ((n1 == n2) ? Comparison.EQUAL : ((n1 < n2) ? Comparison.LESS_THAN : Comparison.GREATER_THAN));
    }

    public static final Comparison compare(short n1, short n2) {
        return ((n1 == n2) ? Comparison.EQUAL : ((n1 < n2) ? Comparison.LESS_THAN : Comparison.GREATER_THAN));
    }

    public static final Comparison compare(int n1, int n2) {
        return ((n1 == n2) ? Comparison.EQUAL : ((n1 < n2) ? Comparison.LESS_THAN : Comparison.GREATER_THAN));
    }

    public static final Comparison compare(long n1, long n2) {
        return ((n1 == n2) ? Comparison.EQUAL : ((n1 < n2) ? Comparison.LESS_THAN : Comparison.GREATER_THAN));
    }

    public static final Comparison compare(float n1, float n2) {
        return fromIntValue(Float.compare(n1, n2));
    }

    public static final Comparison compare(double n1, double n2) {
        return fromIntValue(Double.compare(n1, n2));
    }

}
