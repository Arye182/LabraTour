package com.example.labratour.data;

import java.util.Vector;

public  class VectorSimilarityCalculator  extends SimilarityCalculator <Vector<Double>> implements Calculator {
    private int vectorOneEuclideanNorm;
    private int vectortwoEuclideanNorm;



    public VectorSimilarityCalculator(Vector<Double> vectorOne, Vector<Double> vectorTwo) {
        super(vectorOne, vectorTwo);
    }

    public double calcSim() throws Exception{
        try{
            result = cosineSim(this.objectOne, this.objectTwo,vectorOneEuclideanNorm, vectortwoEuclideanNorm);
            if (result==-1){
                throw new Exception("error in calculation");
            }
            return result;
        }catch (Exception exception){
            throw exception;
    }
}
    @Override
    public void prepareInput() {
        this.vectorOneEuclideanNorm = euclideanNorm((Vector<Double>)this.objectOne);
        this.vectortwoEuclideanNorm = euclideanNorm((Vector<Double>)this.objectTwo);
    }

  private int euclideanNorm(Vector<Double> vector) {
    // TODO: 20/09/2021 a
      return 1;
  }

  public static Double dotProduct(Vector<Double> v1, Vector<Double> v2) {
      double sum = 0;
    if (verifySize(v1, v2)) {


      for (int i = 0; i < v1.size(); i++) {
        sum += (v1.get(i) * v2.get(i));
      }

    }
    return sum;
    }



    public static boolean verifySize(Vector<Double> v1, Vector<Double> v2) {
        if (v1.size()==v2.size()){
            return true;
        }
        return false;
    }
    public static double cosineSim(Vector<Double> v1, Vector<Double> v2, double euclideanNorm1, double euclideanNorm2){
        if ((euclideanNorm1!=0)&&(euclideanNorm2!=0)){
            return (dotProduct(v1, v2) / (euclideanNorm1 * euclideanNorm2));


    }
        return -1;



}
}



