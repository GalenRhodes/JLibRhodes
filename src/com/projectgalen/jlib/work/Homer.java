package com.projectgalen.jlib.work;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: Homer.java
 *     PACKAGE: com.projectgalen.jlib.work
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
import com.projectgalen.jlib.strings.regex.MatchGroup;
import com.projectgalen.jlib.strings.regex.ReplacementHandler;

import java.util.ArrayList;
import java.util.List;

public class Homer implements ReplacementHandler {

    private static final char STRING_CONVERTER = 's';
    private static final char FORMATTER_START  = '%';
    private static final char CAP_FLAG         = '!';
    private static final char PREV_ARG_FLAG    = '<';
    private static final char NEWLINE_FLAG     = 'n';

    private final Object[]     oargs;
    private final List<Object> nargs = new ArrayList<>();
    private       int          oidx  = 0;

    public Homer(Object... args) {
        this.oargs = args;
    }

    public Object[] getNewArgs() {
        return nargs.toArray(new Object[nargs.size()]);
    }

    @Override
    public String getReplacement(String string, MatchGroup... g) {
        return ((g[3].length > 0) ? getReplacement(g[3].group, g[1], g[2], g[4], g[6]) : g[0].group);
    }

    private String getReplacement(String s, MatchGroup g1, MatchGroup g2, MatchGroup g3, MatchGroup g4) {
        return FORMATTER_START + (!((s.charAt(0) == NEWLINE_FLAG) || (s.charAt(0) == FORMATTER_START)) ? captureArgument(s, oargs[getArgIdx(g1, g2)], g3, g4) : s);
    }

    private String captureArgument(String fmtr, Object arg, MatchGroup g1, MatchGroup g2) {
        if(willCap(g2, g1)) {
            fmtr = Q.remove(fmtr, CAP_FLAG);
            if(arg != null) arg = Q.cap(arg.toString());
        }

        nargs.add(arg);
        return fmtr;
    }

    private int getArgIdx(MatchGroup g1, MatchGroup g2) {
        return ((g1.exists && (g1.group.charAt(0) == PREV_ARG_FLAG)) ? (oidx - 1) : (g2.exists ? (oidx = (Integer.parseInt(g2.group) - 1)) : oidx++));
    }

    private boolean willCap(MatchGroup g1, MatchGroup g2) {
        return ((g1.length > 0) && (g2.length > 0) && (g1.group.charAt(0) == STRING_CONVERTER) && (g2.group.indexOf(CAP_FLAG) >= 0));
    }

}
