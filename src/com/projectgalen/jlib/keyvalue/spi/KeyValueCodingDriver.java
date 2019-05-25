package com.projectgalen.jlib.keyvalue.spi;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: KeyValueCodingDriver.java
 *     PACKAGE: com.projectgalen.jlib.keyvalue.spi
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 05/18/2019
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public interface KeyValueCodingDriver {

    public Method getterForKey(String key);

    public Method setterForKey(String key, Class<?> type);

    public Field fieldForKey(String key, Class<?> type);

    public List<Class<?>> handledClasses();

}
