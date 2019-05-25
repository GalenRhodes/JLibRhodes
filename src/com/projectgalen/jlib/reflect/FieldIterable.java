package com.projectgalen.jlib.reflect;

import java.lang.reflect.Field;

public interface FieldIterable {

    public Field field(Field field, VisibilityEnum visibility);

    public static Field iterateOverFields(Class<?> cls, boolean declaredFields, boolean includeSuper, FieldIterable iterable) {
        do {
            for(Field f : (declaredFields ? cls.getDeclaredFields() : cls.getFields())) {
                Field _f = iterable.field(f, VisibilityEnum.getVisibility(f));
                if(_f != null) return _f;
            }
        }
        while(includeSuper && ((cls = cls.getSuperclass()) != null));

        return null;
    }

}
