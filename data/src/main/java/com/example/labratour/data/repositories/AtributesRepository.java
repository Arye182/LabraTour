package com.example.labratour.data.repositories;

import com.example.labratour.domain.UserAtributes;

import io.reactivex.Single;

public interface AtributesRepository {
    Single<String> updateNewAtributes(UserAtributes userAtributes, String userId);
    Single<UserAtributes> getUserAtributes(String userId);

}
