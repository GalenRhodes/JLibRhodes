package com.projectgalen.jlib.reflect;

import com.projectgalen.jlib.Q;

import java.util.Arrays;

public enum PrimitivesEnum {
    // @f:0
    BOOLEAN(0, boolean.class, Boolean.class  ,     false, boolean.class                                                                        ),
    CHAR(   1, char.class,    Character.class,   (char)0, char.class                                                                           ),
    BYTE(   2, byte.class,    Byte.class     ,   (byte)0, byte.class                                                                           ),
    SHORT(  3, short.class,   Short.class    ,  (short)0, byte.class, short.class                                                              ),
    INT(    4, int.class,     Integer.class  ,         0, char.class, byte.class, short.class, int.class                                       ),
    LONG(   5, long.class,    Long.class     ,   (long)0, char.class, byte.class, short.class, int.class, long.class                           ),
    FLOAT(  6, float.class,   Float.class    ,  (float)0, char.class, byte.class, short.class, int.class, float.class                          ),
    DOUBLE( 7, double.class,  Double.class   , (double)0, char.class, byte.class, short.class, int.class, long.class, float.class, double.class);
    // @f:1

    private final int        index;
    private final Class<?>   primitiveClass;
    private final Class<?>   objectClass;
    private final Class<?>[] assignableFrom;
    private final Object     nullValue;

    /**
     * Creates a new instance of {@link PrimitivesEnum}. Each instance represents one of the eight Java primitive data types - boolean, char, byte, short, int, long, float, and
     * double. The instance maps the primitive data type to it's first-class object that it would be boxed to and un-boxed from by the compiler.  It also maps it to the other
     * primitive data types that this instance can be assigned from without having to type cast the assignment.  For example, a {@code byte} value can be assigned to a variable of
     * type {@code int} but not the other way around without type casting it like so:
     *
     * <pre>
     *     int  i = 100;
     *     byte b = (byte)i; // <--- The type cast is required or the compiler will issue an error.
     * </pre>
     *
     * @param i              The index number for the primitive type represented by this {@link Enum}.
     * @param pcls           The {@link Class} that represents the primitive type. For example: {@code int.class}.
     * @param ocls           The {@link Class} of the first-class object that the primitive type is boxed to and un-boxed from. For example: {@code Integer.class}.
     * @param nullValue      The value to use is a {@code null} is assigned to this primitive type.
     * @param assignableFrom One or more {@link Class}es that the primitive type can accept assignments from without explicit type-casting.
     */
    private PrimitivesEnum(int i, Class<?> pcls, Class<?> ocls, Object nullValue, Class<?>... assignableFrom) {
        this.index = i;
        this.primitiveClass = pcls;
        this.objectClass = ocls;
        this.nullValue = nullValue;
        this.assignableFrom = assignableFrom;
    }

    public PrimitivesEnum fromIndex(int i) {
        for(PrimitivesEnum e : values()) if(i == e.index) return e;
        return null;
    }

    public final int getIndex()               { return index; }

    public final Object getNullValue()        { return nullValue; }

    public final Class<?> getObjectClass()    { return objectClass; }

    public final Class<?> getPrimitiveClass() { return primitiveClass; }

    public final boolean isAssignableFrom(Class<?> cls) {
        if(Q.Z(cls)) return false;
        Class<?> csrc = getPrimitiveForClass(cls);
        if(Q.Z(csrc)) return false;
        for(Class<?> c : assignableFrom) if(csrc == c) return true;
        return false;
    }

    public static PrimitivesEnum fromClass(Class<?> cls) {
        for(PrimitivesEnum e : values()) if((cls == e.getPrimitiveClass()) || (cls == e.getObjectClass())) return e;
        return null;
    }

    public static int getIndexForClass(Class<?> cls) {
        PrimitivesEnum e = fromClass(cls);
        return ((Q.Z(e)) ? getUnknownIndex() : e.index);
    }

    public static Class<?> getPrimitiveForClass(Class<?> cls) {
        PrimitivesEnum e = fromClass(cls);
        return ((Q.Z(e)) ? cls : e.primitiveClass);
    }

    public static int getUnknownIndex() { return values().length; }

    public static boolean isAssignable(Class<?> clsTarget, Class<?> clsSource) {
        PrimitivesEnum eTarget = fromClass(clsTarget);
        return ((eTarget != null) && eTarget.isAssignableFrom(clsSource));
    }

}
