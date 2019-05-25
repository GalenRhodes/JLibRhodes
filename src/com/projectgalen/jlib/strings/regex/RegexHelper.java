package com.projectgalen.jlib.strings.regex;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: RegexHelper.java
 *     PACKAGE: com.projectgalen.jlib.strings
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

import com.projectgalen.jlib.C;
import com.projectgalen.jlib.Q;
import com.projectgalen.jlib.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    private final Matcher matcher;
    private final String  string;

    public RegexHelper(String pattern, int flags, CharSequence input) {
        this(RX.getMatcherForRegex(pattern, flags, input), input);
    }

    public RegexHelper(String pattern, CharSequence input) {
        this(pattern, 0, input);
    }

    public RegexHelper(Pattern pattern, CharSequence input) {
        this(RX.getMatcherForPattern(pattern, input), input);
    }

    public RegexHelper(Matcher m, CharSequence input) {
        super();
        if(Q.Z(m)) throw new NullPointerException(R.format(C.ERR_MSG_IS_NULL, R.getStr(C.WORD_MATCHER)));
        if(Q.Z(input)) throw new NullPointerException(R.format(C.ERR_MSG_IS_NULL, R.getStr(C.WORD_INPUT)));
        this.matcher = m;
        this.string = input.toString();
    }

    public String replaceAll(ReplacementHandler handler) {
        StringBuilder sb    = new StringBuilder();
        int           index = 0;

        synchronized(matcher) {
            matcher.reset();

            while(matcher.find()) {
                String replacement = handler.getReplacement(string, getMatchGroups());
                sb.append(string, index, matcher.start()).append((Q.Z(replacement)) ? matcher.group() : replacement);
                index = matcher.end();
            }

            if(index < string.length()) sb.append(string.substring(index));
        }

        return sb.toString();
    }

    private MatchGroup[] getMatchGroups() {
        List<MatchGroup> list = new ArrayList<>();
        for(int x = 0; x < matcher.groupCount(); x++) list.add(new MatchGroup(matcher, x));
        return list.toArray(new MatchGroup[list.size()]);
    }

}
