package com.projectgalen.jlib.reflect;

/* =================================================================================================================================
 *     PROJECT: JLibRhodes
 *    FILENAME: ReflectTools.java
 *     PACKAGE: com.projectgalen.jlib.reflect
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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public final class ReflectTools {

    private ReflectTools() {}

    public static boolean areMatchingTypes(Class<?> lValueClass, Class<?> rValueClass, boolean exactMatch) {
        return (exactMatch ? (lValueClass == rValueClass) : up(lValueClass).isAssignableFrom(up(rValueClass)));
    }

    public static Field findField(Class<?> cls, String fieldName, Class<?> type, boolean willSet, boolean exactMatch) {
        if(Q.Z(cls)) return null;

        for(Field field : cls.getDeclaredFields()) {
            if(fieldName.equals(field.getName())) {
                Class<?> ftype = field.getType();
                if((Q.Z(type)) || (willSet ? areMatchingTypes(ftype, type, exactMatch) : areMatchingTypes(type, ftype, exactMatch))) return field;
            }
        }

        return findField(cls.getSuperclass(), fieldName, type, willSet, exactMatch);
    }

    public static Method findMethod(Class<?> cls, String methodName, boolean exactMatch, Class<?> returnType, Class<?>... parameterTypes) {
        if(Q.Z(cls)) return null;

        boolean  returnTypeNotVoid = (Q.Z(returnType));
        Method[] methods           = cls.getDeclaredMethods();

        for(Method method : methods) {
            if(returnTypeNotVoid ? (method.getReturnType() != Void.TYPE) : areMatchingTypes(method.getReturnType(), returnType, exactMatch)) {
                if(checkParameters(method.getParameterTypes(), parameterTypes, exactMatch) && methodName.equals(method.getName())) return method;
            }
        }

        return findMethod(cls.getSuperclass(), methodName, exactMatch, returnType, parameterTypes);
    }

    public static boolean implementsInterface(Object o, Class<?> i) {
        if(Q.Z(o)) return false;
        for(Class<?> ii : o.getClass().getInterfaces()) if(ii == i) return true;
        return false;
    }

    public static boolean isNotAccessable(Member member) {
        return (VisibilityEnum.getVisibility(member) != VisibilityEnum.PUBLIC);
    }

    public static <T extends AccessibleObject> T makeAccessible(T accessibleObject) {
        if((accessibleObject instanceof Member) && isNotAccessable((Member)accessibleObject)) accessibleObject.setAccessible(true);
        return accessibleObject;
    }

    private static boolean checkParameters(Class<?>[] methodParameters, Class<?>[] searchParameters, boolean exactMatch) {
        if(methodParameters.length != searchParameters.length) return false;
        for(int i = 0; i < methodParameters.length; i++) if(!areMatchingTypes(methodParameters[i], searchParameters[i], exactMatch)) return false;
        return true;
    }

    private static Class<?> up(Class<?> c) {
        PrimitivesEnum e = PrimitivesEnum.fromClass(c);
        return ((Q.Z(e)) ? c : e.getObjectClass());
    }

}
