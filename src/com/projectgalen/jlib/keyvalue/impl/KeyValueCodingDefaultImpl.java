package com.projectgalen.jlib.keyvalue.impl;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: KeyValueCodingDefaultImpl.java
 *     PACKAGE: com.projectgalen.jlib.keyvalue.impl
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

import com.projectgalen.jlib.C;
import com.projectgalen.jlib.Q;
import com.projectgalen.jlib.R;
import com.projectgalen.jlib.keyvalue.KeyValueCoding;
import com.projectgalen.jlib.keyvalue.KeyValueCodingException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class KeyValueCodingDefaultImpl extends KeyValueCodingDefaultImplBase implements KeyValueCoding {

    public KeyValueCodingDefaultImpl(Object object) {
        super(object);
    }

    @Override
    public void setNullForKey(String key, Class<?> type) {
        if(object != null) {
            key = validateKey(key);
            Method method = findSetter(key, type);
            if(method != null) callMethod(method, (Object)null);
            else {
                Field field = findField(key, type, true);
                if(field != null) setFieldValue(field, null);
                else throw new KeyValueCodingException(R.getStr(C.ERR_MSG_NOT_FOUND), R.getStr(C.WORD_KEY), key);
            }
        }
    }

    @Override
    public void setNullForKeyPath(String keyPath, Class<?> type) {
        if(object != null) {
            String[] kpath = validateKeyPath(keyPath);
            if(kpath.length == 1) setNullForKey(kpath[0], type);
            else new KeyValueCodingDefaultImpl(valueForKey(kpath[0])).setNullForKeyPath(kpath[1], type);
        }
    }

    @Override
    public void setValueForKey(String key, Object value) {
        if(Q.Z(value)) setNullForKey(key, null);
        else if(object != null) {
            key = validateKey(key);
            Method method = findSetter(key, value.getClass());
            if(method != null) callMethod(method, value);
            else {
                Field field = findField(key, value.getClass(), true);
                if(field != null) setFieldValue(field, value);
                else throw new KeyValueCodingException(R.getStr(C.ERR_MSG_NOT_FOUND), R.getStr(C.WORD_KEY), key);
            }
        }
    }

    @Override
    public void setValueForKeyPath(String keyPath, Object value) {
        if(object != null) {
            String[] kpath = validateKeyPath(keyPath);
            if(kpath.length == 1) setValueForKey(kpath[0], value);
            else new KeyValueCodingDefaultImpl(valueForKey(kpath[0])).setValueForKeyPath(kpath[1], value);
        }
    }

    @Override
    public <T> T valueForKey(String key) {
        if(Q.Z(object)) return null;
        key = validateKey(key);
        Method method = findGetter(key);
        if(method != null) return callMethod(method);
        Field field = findField(key, null, false);
        if(field != null) return getFieldValue(field);
        throw new KeyValueCodingException(R.getStr(C.ERR_MSG_NOT_FOUND), R.getStr(C.WORD_KEY), key);
    }

    @Override
    public <T> T valueForKeyPath(String keyPath) {
        if(Q.Z(object)) return null;
        String[] kpath = validateKeyPath(keyPath);
        return ((kpath.length == 1) ? valueForKey(kpath[0]) : new KeyValueCodingDefaultImpl(valueForKey(kpath[0])).valueForKeyPath(kpath[1]));
    }

}
