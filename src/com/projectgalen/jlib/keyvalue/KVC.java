package com.projectgalen.jlib.keyvalue;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: KVC.java
 *     PACKAGE: com.projectgalen.jlib.keyvalue
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 05/17/2019
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

public class KVC {

    private KVC() {}

    public static void setNullForKey(Object object, String key, Class<?> type) {}

    public static void setNullForKeyPath(Object object, String keyPath, Class<?> type) {}

    public static void setValueForKey(Object object, String key, Object value) {}

    public static void setValueForKeyPath(Object object, String keyPath, Object value) {}

    public static <T> T valueForKey(Object object, String key) { return null; }

    public static <T> T valueForKeyPath(Object object, String keyPath) { return null; }

}
