package com.example.labratour.data.repositories;

import java.util.HashMap;

import io.reactivex.Single;

public interface AtributesRepository {
    Single<Void> updateNewAtributes(HashMap<String, Double> userAtributes, String userId);
    Single<HashMap<String, Double>> getUserAtributes(String userId);

}
