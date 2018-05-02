package com.tianyalei.bank.tuple;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public class TupleTwo<A, B> {

    public final A first;

    public final B second;

    public TupleTwo(A a, B b) {
        first = a;
        second = b;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

}