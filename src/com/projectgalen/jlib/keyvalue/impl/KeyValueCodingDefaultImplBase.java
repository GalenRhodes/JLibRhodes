package com.projectgalen.jlib.keyvalue.impl;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: KeyValueCodingDefaultImplBase.java
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
import com.projectgalen.jlib.keyvalue.KeyValueCodingException;
import com.projectgalen.jlib.keyvalue.spi.KeyValueCodingDriver;
import com.projectgalen.jlib.reflect.PrimitivesEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("HardCodedStringLiteral")
public class KeyValueCodingDefaultImplBase {

    private static final boolean[] EXACTNESS    = {true, false};
    private static final String[]  GETTER_NAMES = {"get%!s", "%s", "_get%!s", "_%s", "get%!s_", "%s_", "_get%!s_", "_%s_"};
    private static final String[]  SETTER_NAMES = {"set%!s", "%s", "_set%!s", "_%s", "set%!s_", "%s_", "_set%!s_", "_%s_"};
    private static final String[]  FIELD_NAMES  = {"%s", "_%s", "%s_", "_%s_"};

    private static final Map<Class<?>, ? super KeyValueCodingDriver> KVC_DRIVERS = new LinkedHashMap<>();

    protected final Object   object;
    protected final Class<?> objClass;

    public KeyValueCodingDefaultImplBase(Object object) {
        super();
        this.object = object;
        this.objClass = ((Q.Z(object)) ? null : object.getClass());
    }

    @SuppressWarnings("unchecked")
    protected <T> T callMethod(Method method, Object... args) {
        try { return (T)method.invoke(object, args); } catch(Exception e) { throw new KeyValueCodingException(e); }
    }

    protected Field findField(String key, Class<?> vt, boolean willSet) {
        return findField(objClass, key, vt, willSet);
    }

    protected Method findGetter(String key) {
        return findGetter(objClass, key);
    }

    protected Method findSetter(String key, Class<?> cls) {
        return findSetter(objClass, key, cls);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getFieldValue(Field field) {
        try { return (T)field.get(object); } catch(Exception e) { throw new KeyValueCodingException(e); }
    }

    protected void setFieldValue(Field field, Object value) {
        try { field.set(object, value); } catch(Exception e) { throw new KeyValueCodingException(e); }
    }

    protected String validateKey(String key) { return validateKeyValue(R.getStr(C.WORD_KEY), key); }

    protected String[] validateKeyPath(String keyPath) {
        int i = (keyPath = validateKeyValue(R.getStr(C.WORD_KEYPATH), keyPath)).indexOf('.');
        return ((i < 0) ? new String[] {keyPath} : new String[] {keyPath.substring(0, i), keyPath.substring(i + 1)});
    }

    public static void addDriver(KeyValueCodingDriver driver) {
        if(driver != null) for(Class<?> cls : driver.handledClasses()) KVC_DRIVERS.put(cls, driver);
    }

    protected static KeyValueCodingDriver driverForClass(Class<?> cls) {
        return (KeyValueCodingDriver)KVC_DRIVERS.get(cls);
    }

    private static Field findField(Class<?> objectClass, String key, Class<?> valueClass, boolean willSet) {
        do {
            for(boolean exact : EXACTNESS) {
                for(String fmt : FIELD_NAMES) {
                    Field fld = getField(Q.format(fmt, key), objectClass, valueClass, exact, willSet);
                    if(fld != null) return fld;
                }
            }
        }
        while((objectClass = objectClass.getSuperclass()) != null);

        return null;
    }

    private static Method findGetter(Class<?> cls, String key) {
        do {
            for(String fmt : GETTER_NAMES) {
                Method method = getGetter(cls, Q.format(fmt, key));
                if(method != null) return method;
            }
        }
        while((cls = cls.getSuperclass()) != null);

        return null;
    }

    private static Method findSetter(Class<?> cls, String key, Class<?> type) {
        do {
            for(boolean exact : EXACTNESS) {
                for(String fmt : SETTER_NAMES) {
                    Method m = getSetter(cls, Q.format(fmt, key), type, exact);
                    if(m != null) return m;
                }
            }
        }
        while((cls = cls.getSuperclass()) != null);
        return null;
    }

    private static Field getField(String fieldName, Class<?> objectClass, Class<?> valueClass, boolean exact, boolean willSet) {
        for(Field fld : objectClass.getDeclaredFields()) if(fieldName.equals(fld.getName()) && isFieldTypeMatch(fld.getType(), valueClass, exact, willSet)) return fld;
        return null;
    }

    private static Method getGetter(Class<?> cls, String name) {
        for(Method m : cls.getDeclaredMethods()) if(name.equals(m.getName()) && Q.Z(m.getParameterCount()) && (m.getReturnType() != Void.TYPE)) return m;
        return null;
    }

    private static Method getSetter(Class<?> cls, String name, Class<?> type, boolean exact) {
        for(Method m : cls.getDeclaredMethods()) {
            Class<?>[] pTypes = m.getParameterTypes();
            if((m.getReturnType() == Void.TYPE) && (pTypes.length == 1) && name.equals(m.getName()) && isParamTypeMatch(pTypes[0], type, exact)) return m;
        }

        return null;
    }

    private static boolean isAssignable(Class<?> clsTarget, Class<?> clsSource) {
        return (PrimitivesEnum.isAssignable(clsTarget, clsSource) || clsTarget.isAssignableFrom(clsSource));
    }

    private static boolean isFieldArrayAssignable(Class<?> ft, Class<?> vt, boolean willSet) {
        return (ft.isArray() && vt.isArray() && isFieldTypeMatch(ft.getComponentType(), vt.getComponentType(), true, willSet));
    }

    private static boolean isFieldAssignable(Class<?> ft, Class<?> vt, boolean willSet) {
        return willSet ? isAssignable(ft, vt) : isAssignable(vt, ft);
    }

    private static boolean isFieldTypeMatch(Class<?> ft, Class<?> vt, boolean exact, boolean willSet) {
        return ((ft.isArray() || vt.isArray()) ? isFieldArrayAssignable(ft, vt, willSet) : ((ft == vt) || (!exact && isFieldAssignable(ft, vt, willSet))));
    }

    private static boolean isParamArrayAssignable(Class<?> pType, Class<?> type) {
        return (type.isArray() && pType.isArray() && isParamTypeMatch(pType.getComponentType(), type.getComponentType(), true));
    }

    private static boolean isParamTypeMatch(Class<?> pType, Class<?> type, boolean exact) {
        return ((pType.isArray() || type.isArray()) ? isParamArrayAssignable(pType, type) : ((pType == type) || (!exact && isAssignable(pType, type))));
    }

    private static String validateKeyValue(String name, String value) {
        if(Q.Z(value)) throw new NullPointerException(R.format(C.ERR_MSG_IS_NULL, name));
        if(Q.Z((value = value.trim()).length())) throw new IllegalArgumentException(R.format(C.ERR_MSG_IS_EMPTY, name));
        return value;
    }

}
