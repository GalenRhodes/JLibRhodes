package com.projectgalen.jlib.test;

import com.projectgalen.jlib.Q;
import com.projectgalen.jlib.R;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static final String BAR = "==================================================================================================";
    public static final String DSH = "--------------------------------------------------------------------------------------------------";

    public static final String center(String s, int length) {
        char[] field = new char[length];
        Arrays.fill(field, ' ');
        int i = ((length - s.length()) / 2);
        System.arraycopy(s.toCharArray(), 0, field, i, s.length());
        return String.valueOf(field);
    }

    public static void main(String[] args) {
        String s = Q.format(R.getStr("test.1"),
                            "time",
                            100,
                            "men",
                            "country",
                            0,
                            "a",
                            "b",
                            "c",
                            Calendar.getInstance());
        System.out.printf("\nResults: \"%s\"\n\n", s);
    }

    protected static String quote(String s) {
        return ((Q.Z(s)) ? "<NULL>" : String.format("[%s]", s));
    }

    protected static void test2() {
        Pattern      p  = Pattern.compile("(\\w+)");
        StringBuffer sb = new StringBuffer();
        Matcher      m  = p.matcher("galen sherard rhodes");

        while(m.find()) {
            String s = m.group(1);
            m.appendReplacement(sb, s.length() > 1 ? s.substring(0, 1).toUpperCase() + s.substring(1) : s.toUpperCase());
        }

        m.appendTail(sb);
        System.out.printf("\n\n%s\n\n", sb);
    }

    private static String getIndent(int indent) {
        char[] _indent = new char[indent];
        Arrays.fill(_indent, ' ');
        return String.valueOf(_indent);
    }

    private static void print(String key, Object value) {
        System.out.printf("\nValue for key \"%s\" of type %s: %s\n", key, ((Q.Z(value)) ? "-N/A-" : value.getClass().getName()), value);
    }

    private static void test4() {
        String[] strings = {",,,bob,,sarah,,,", ",,,,,,,", ", , , bob, , sarah, , , ", ", , , , , , , ", "Galen was here today and, god willing, he will be here tomorrow."};
        String[] regexs  = {",", "\\,", "\\s*,\\s*", "\\s+", ":"};

        for(int i = 0; i < strings.length; i++) {
            String string = strings[i];

            for(int j = 0; j < regexs.length; j++) {
                String regex = regexs[j];

                for(int k = -1; k < 1; k++) {
                    String[] array = Q.split(regex, string, k);

                    System.out.printf("%s\n     REGEX: /%s/\n     LIMIT: %d\n     INPUT: \"%s\"\n     COUNT: %d\n%s\n", BAR, regex, k, string, array.length, DSH);

                    for(int l = 0; l < array.length; l++) {
                        System.out.printf("    String #%-2d> \"%s\"\n", l + 1, array[l]);
                    }
                }
            }
        }
    }

//    protected static void test3() {
//        Pattern p = Pattern.compile(KeyValueCollectionImpl.GET_PKEY_REGEX);
//        String[] str = {
//                "average",
//                "min",
//                "max",
//                "join:",
//                "join:''",
//                "join:\"\"",
//                "join:, ",
//                "join:', '",
//                "join:\"'\"",
//                "join:'bob'",
//                "join:\"bob\"",
//                "join:\"bob'\"",
//                "join:\"'bob\"",
//                "join:'bob\"bob'",
//                "join:\"bob's girl\"",
//                "join:'\"'",
//                "sub:",
//                "sub:1-3",
//                "sub:1",
//                "sub:-3",
//                "sub:-3-1",
//                "sub:1-3-2",
//                "sub:1>2"
//        };
//
//        for(String s : str) {
//            Matcher m = p.matcher("@" + s);
//            System.out.printf("\n%s\nPATTERN: \"%s\"\n%s\n", BAR, s, DSH);
//            if(m.matches()) {
//                StringBuilder sb = new StringBuilder();
//                for(int i = 1; i <= m.groupCount(); i++) sb.append(String.format("Group %d: ", i)).append(quote(m.group(i))).append('\n');
//                System.out.printf("\n%s", sb.toString());
//            }
//            else {
//                System.out.print("No Match!\n");
//            }
//        }
//
//        System.out.println(BAR);
//    }

    private static void test5() {
        String[] whoBeatsWho = {
                /* 0       1         2           3          4          5            6            7           8        9        10           11             12            13   */
                "Byte", "Short", "Integer", "AtomicInt", "Long", "AtomicLong", "LongAccum", "LongAdder", "BigInt", "Float", "Double", "DoubleAccum", "DoubleAdder", "BigDecimal"
        };

        int[] weights = {8, 8, 8, 8, 8, 8, 8, 8, 8, 13, 13, 13, 13, 13};

        int fieldlen = 0;
        for(int i = 0; i < whoBeatsWho.length; i++) {
            int slen = whoBeatsWho[i].length();
            if(slen > fieldlen) fieldlen = slen;
        }

        System.out.print("/*");
        for(int i = 0; i < (7 + fieldlen); i++) System.out.print(' ');
        System.out.printf("%s ", center(whoBeatsWho[0], fieldlen));

        for(int i = 1; i < whoBeatsWho.length; i++) {
            System.out.printf("| %s ", center(whoBeatsWho[i], fieldlen));
        }
        System.out.println("*/");
        for(int i = 0; i < whoBeatsWho.length; i++) {
            System.out.printf("/* %" + fieldlen + "s */ { %s ", whoBeatsWho[i], center(String.valueOf(Math.max(weights[i], weights[0])), fieldlen));
            for(int j = 1; j < whoBeatsWho.length; j++) {
                System.out.printf(", %s ", center(String.valueOf(Math.max(weights[i], weights[j])), fieldlen));
            }
            System.out.println(" },");
        }
    }

//    private static void test() {
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        TestSub2         obj = new TestSub2();
//        String           key = "superField3";
//        Calendar         c   = KeyValueDefaultImpl.valueForKey(obj, key);
//        String           s   = fmt.format(c.getTime());
//
//        print(key, s);
//
//        Number n = KeyValueDefaultImpl.valueForKey(obj, "sub2Field1");
//        print(key, n);
//        KeyValueDefaultImpl.setValueForKey(obj, "sub2Field1", 1234.03);
//        n = KeyValueDefaultImpl.valueForKey(obj, "sub2Field1");
//        print(key, n);
//
//        KeyValueDefaultImpl.setValueForKey(obj, "sub2Field1", null);
//        n = KeyValueDefaultImpl.valueForKey(obj, "sub2Field1");
//        print(key, n);
//
//        System.out.println();
//    }

    private static void test6() {
        String[] strings = {"galen rhodes", "g☯️len rhodes", "☯️alen rhodes", "☯️", "😈alen rhodes", "😈", "g", "ga", ""};
        for(String s : strings) System.out.printf("Result: \"%s\"\n", Q.cap(s));
    }

    private static void zoo(Class<?> testClass, int indentAmount) {
        if(testClass != null) {
            String                             indent     = getIndent(indentAmount);
            Class<?>[]                         ints       = testClass.getInterfaces();
            Type[]                             types      = testClass.getGenericInterfaces();
            TypeVariable<? extends Class<?>>[] typeParams = testClass.getTypeParameters();

            System.out.printf("\n%sName: %s\n%1$sType: %s", indent, testClass.getName(), testClass.getTypeName());

            if(typeParams.length > 0) {
                System.out.printf("<%s", typeParams[0].getName());
                for(int i = 1; i < typeParams.length; i++) {
                    System.out.printf(", %s", typeParams[i].getName());
                }
                System.out.print('>');
            }
            System.out.print("\n\n");

            if(ints.length > 0) {
                for(int i = 0; i < ints.length; i++) {
                    System.out.printf("%sInterface %2d> %s\n", indent, i, ints[i].getName());
                }

                System.out.println();
            }

            if(types.length > 0) {
                for(int i = 0; i < types.length; i++) {
                    System.out.printf("%sGeneric Interface %2d> %s\n", indent, i, types[i].getTypeName());
                }
                System.out.println();
            }

            zoo(testClass.getSuperclass(), indentAmount + 4);
        }
    }

}
