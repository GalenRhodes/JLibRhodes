package com.projectgalen.jlib.parameters;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: ObjectParam.java
 *     PACKAGE: com.projectgalen.jlib.parameters
 *         IDE: AppCode
 *      AUTHOR: Galen Rhodes
 *        DATE: 04/25/2019
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

public class ObjectParam<T> extends Params {

    private T value;

    public ObjectParam(T v) {
        value = v;
    }

    public ObjectParam() {
        value = null;
    }

    public final T getValue()       { lockForRead(); try { return value; } finally { unlockForRead(); }}

    public final void setValue(T v) { lockForWrite(); try { if(v != value) { value = v; flagValueChanged(); } } finally { unlockForWrite(); }}

    public boolean isNull()         { lockForRead(); try { return (Q.Z(value)); } finally { unlockForRead(); }}

    public String toString()        { lockForRead(); try { return Objects.toString(value, super.toString()); } finally { unlockForRead(); }}

}
