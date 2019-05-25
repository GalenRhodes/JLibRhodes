package com.projectgalen.jlib.reflect;

import java.lang.reflect.Method;

public interface MethodIterable {

    public Method method(Method method, VisibilityEnum visibility);

    public static Method iterateOverMethods(Class<?> cls, boolean declaredMethods, boolean includeSuper, MethodIterable iterable) {
        do {
            for(Method m : (declaredMethods ? cls.getDeclaredMethods() : cls.getMethods())) {
                Method _m = iterable.method(m, VisibilityEnum.getVisibility(m));
                if(_m != null) return _m;
            }
        }
        while(includeSuper && ((cls = cls.getSuperclass()) != null));

        return null;
    }

}
