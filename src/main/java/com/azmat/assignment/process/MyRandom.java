package com.azmat.assignment.process;

import java.util.Random;

public class MyRandom extends Random {

    private static final long serialVersionUID = 1L;

    public MyRandom() {
        super();
    }

    @Override
    public int nextInt() {
        return this.nextInt(15-1) + 1;
    }

}