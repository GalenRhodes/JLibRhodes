package com.projectgalen.jlib.reflect;

import com.projectgalen.jlib.Q;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public enum VisibilityEnum {
    PUBLIC(0), PROTECTED(1), PACKAGE(2), PRIVATE(3);

    private static final int VISIBILITY_MASK = (Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED);
    private              int weight;

    private VisibilityEnum(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public VisibilityEnum resolveVisibility(VisibilityEnum other) {
        return (((Q.Z(other)) || (weight >= other.weight)) ? this : other);
    }

    public static VisibilityEnum getVisibility(Member m) {
        VisibilityEnum methodVisibility = _getVisibilityEnum(m.getModifiers());
        Class<?>       declaringClass   = m.getDeclaringClass();
        return ((Q.Z(declaringClass)) ? methodVisibility : methodVisibility.resolveVisibility(getVisibility(declaringClass)));
    }

    public static VisibilityEnum getVisibility(Class<?> c) {
        return _getVisibilityEnum(c.getModifiers());
    }

    private static VisibilityEnum _getVisibilityEnum(int modifiers) {
        switch((modifiers & VISIBILITY_MASK)) { // @f:0
            case Modifier.PUBLIC: return VisibilityEnum.PUBLIC;
            case Modifier.PRIVATE: return VisibilityEnum.PRIVATE;
            case Modifier.PROTECTED: return VisibilityEnum.PROTECTED;
            default: return VisibilityEnum.PACKAGE; // @f:1
        }
    }
}
