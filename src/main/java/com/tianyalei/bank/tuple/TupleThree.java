package com.tianyalei.bank.tuple;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public class TupleThree<A, B, C> extends TupleTwo<A, B> {

    public final C third;

    public TupleThree(A a, B b, C c) {
        super(a, b);
        third = c;
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + "," + third + ")";
    }

}
