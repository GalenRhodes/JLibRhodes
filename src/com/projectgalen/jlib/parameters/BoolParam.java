package com.projectgalen.jlib.parameters;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: BoolParam.java
 *     PACKAGE: com.projectgalen.jlib.parameters
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

public class BoolParam extends Params {

    private boolean value;

    public BoolParam(boolean b) { value = b; }

    public BoolParam()          { value = false; }

    public boolean getValue() {
        lockForRead();
        try { return value; } finally { unlockForRead(); }
    }

    public void setValue(boolean b) {
        lockForWrite();
        try {
            if(b != value) {
                value = b;
                flagValueChanged();
            }
        }
        finally { unlockForWrite(); }
    }

    public boolean getValuePostNegate() {
        lockForWrite();
        try {
            value = !value;
            flagValueChanged();
            return value;
        }
        finally {
            unlockForWrite();
        }
    }

    public boolean getValuePreNegate() {
        lockForWrite();
        try {
            boolean b = value;
            value = !value;
            flagValueChanged();
            return b;
        }
        finally {
            unlockForWrite();
        }
    }

}
