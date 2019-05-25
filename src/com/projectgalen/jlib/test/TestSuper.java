package com.projectgalen.jlib.test;

import java.util.Calendar;

public class TestSuper {

    private String   superField1;
    private int      superField2;
    private Calendar _superField3;

    public TestSuper() {
        superField1 = "Galen";
        superField2 = 3;
        _superField3 = Calendar.getInstance();
    }

    public int getSuperField2() {
        return superField2;
    }

    protected String superField1() {
        return superField1;
    }

    protected void setSuperField2(int i) {
        superField2 = i;
    }

}
