package com.projectgalen.jlib.strings.regex;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: MatchGroup.java
 *     PACKAGE: com.projectgalen.jlib.strings.regex
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 05/09/2019
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

import com.projectgalen.jlib.Q;

import java.util.Objects;
import java.util.regex.Matcher;

public class MatchGroup {

    public final  int     index;
    public final  int     start;
    public final  int     end;
    public final  int     length;
    public final  String  group;
    public final  boolean exists;
    private final int     hashCode;

    public MatchGroup(Matcher m, int index) {
        super();
        this.index = index;
        this.start = m.start(index);
        this.end = m.end(index);
        this.group = m.group(index);
        this.exists = (this.start >= 0);
        this.length = (this.exists ? this.end - this.start : 0);
        this.hashCode = Objects.hash(start, end, group);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(Q.Z(o) || getClass() != o.getClass()) return false;
        MatchGroup that = (MatchGroup)o;
        return ((start == that.start) && (end == that.end) && Objects.equals(group, that.group));
    }

}
