package com.projectgalen.jlib.strings.regex;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: RX.java
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

import com.projectgalen.jlib.C;
import com.projectgalen.jlib.Q;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RX {

    /** Holds persistent instances of commonly used patterns */
    private static final Map<String, Pattern> _REGEX_MAP = new TreeMap<>();

    private RX() {}

    public static String getGroup(Matcher matcher, int index) {
        return getGroup(matcher, index, "");
    }

    public static String getGroup(Matcher matcher, int index, String nullDefault) {
        return Objects.toString(matcher.group(index), nullDefault);
    }

    public static Matcher getMatcherForRegex(String regex, CharSequence input) {
        return getMatcherForRegex(regex, 0, input);
    }

    public static Matcher getMatcherForRegex(String regex, int flags, CharSequence input) {
        synchronized(_REGEX_MAP) {
            return getCachedPattern(regex, flags).matcher(input);
        }
    }

    static Matcher getMatcherForPattern(Pattern pattern, CharSequence input) {
        synchronized(_REGEX_MAP) {
            String mapkey = getRegexMapKey(pattern.pattern(), pattern.flags());
            if(!_REGEX_MAP.containsKey(mapkey)) _REGEX_MAP.put(mapkey, pattern);
            return pattern.matcher(input);
        }
    }

    private static Pattern getCachedPattern(String regex, int flags) {
        String  mapkey   = getRegexMapKey(regex, flags);
        Pattern pattern = _REGEX_MAP.get(mapkey);
        if(Q.Z(pattern)) _REGEX_MAP.put(mapkey, (pattern = Pattern.compile(regex, flags)));
        return pattern;
    }

    private static String getRegexMapKey(String regex, int flags) {
        return (regex + C.SEP + flags);
    }

}
