package com.projectgalen.jlib.test;

public class TestSub2 extends TestSub1 {

    private Number sub2Field1;

    public TestSub2() {
        sub2Field1 = 1024;
    }

    protected Number sub2Field1() {
        return sub2Field1;
    }

    protected void sub2Field1(Number sub2Field1) {
        this.sub2Field1 = sub2Field1;
    }

}
