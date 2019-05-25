package com.projectgalen.jlib.parameters;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: FloatParam.java
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

public class FloatParam extends Params {

    private float value;

    public FloatParam(float v) { value = v; }

    public FloatParam()        { value = 0; }

    public final float getValue() {
        lockForRead();
        try { return value; } finally { unlockForRead(); }
    }

    public final void setValue(float v) {
        lockForWrite();
        try {
            if(v != value) {
                value = v;
                flagValueChanged();
            }
        }
        finally { unlockForWrite(); }
    }

    public final float getValuePostAdd(float delta) {
        lockForWrite();
        try {
            if(delta != 0) {
                value += delta;
                flagValueChanged();
            }
            return value;
        }
        finally { unlockForWrite(); }
    }

    public final float getValuePostDec() { return getValuePostAdd((float)-1); }

    public final float getValuePostInc() { return getValuePostAdd((float)1); }

    public final float getValuePreAdd(float delta) {
        lockForWrite();
        try {
            if(Q.Z(delta)) {
                return value;
            }
            else {
                float v = value;
                value += delta;
                flagValueChanged();
                return v;
            }
        }
        finally { unlockForWrite(); }
    }

    public final float getValuePreDec() { return getValuePreAdd((float)-1); }

    public final float getValuePreInc() { return getValuePreAdd((float)1); }

}
