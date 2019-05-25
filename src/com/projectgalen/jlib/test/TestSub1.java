package com.projectgalen.jlib.test;

public class TestSub1 extends TestSuper implements Comparable<TestSub1> {

    private Double sub1Field1 = 0.0;
    private Boolean _sub1Field2 = true;

    public TestSub1() {

    }

    @Override
    public int compareTo(TestSub1 o) {
        return 0;
    }

    protected Double getSub1Field1() {
        return sub1Field1;
    }

    protected void setSub1Field1(Double sub1Field1) {
        this.sub1Field1 = sub1Field1;
    }

    protected Boolean getSub1Field2() {
        return _sub1Field2;
    }

}
