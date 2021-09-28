package com.example.labratour.data.repositories;

import com.example.labratour.domain.UserAtributes;

import java.util.HashMap;

import io.reactivex.Single;

public interface AtributesRepository {
    Single<String> updateNewAtributes(HashMap<String, Object> userAtributes, String userId);
    Single<UserAtributes> getUserAtributes(String userId);

}
