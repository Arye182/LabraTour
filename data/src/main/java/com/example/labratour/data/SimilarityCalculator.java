package com.example.labratour.data;

public abstract class SimilarityCalculator<T> implements Calculator{
    protected T objectOne;
    protected T objectTwo;
    protected double result;
    public SimilarityCalculator(T objectOne, T objextTwo) {
        this.objectOne = objectOne;
        this.objectTwo = objextTwo;

    }

    public  void calc() throws Exception {

        prepareInput();
        try{
        result = calcSim();}
        catch(Exception exception){
    throw exception;
        }


    }

    public abstract double calcSim() throws Exception;

    public abstract void prepareInput();

}
