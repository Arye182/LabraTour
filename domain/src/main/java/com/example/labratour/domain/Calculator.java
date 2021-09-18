package com.example.labratour.domain;

import java.util.Vector;

public interface Calculator {
    public Vector<Integer> calculateNew(Vector<Integer> oldAtributesVector, Vector<Integer> newRate);
    public Vector<Integer> calculateRatedAtributesByUserRate(int userRate, Vector<Integer> poiAtributes);
}
