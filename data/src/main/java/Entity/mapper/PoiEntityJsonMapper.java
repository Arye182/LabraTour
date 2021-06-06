package Entity.mapper;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import Entity.PoiEntity;

public class PoiEntityJsonMapper extends EntityJsonMapper<PoiEntity> {
    @Override
    public PoiEntity transformEntity(String jsonResponse) throws JsonSyntaxException {
        final Type poiEntityType = new TypeToken<PoiEntity>(){}.getType();
        return this.gson.fromJson(jsonResponse, poiEntityType);
    }

    @Override
    public List<PoiEntity> transformEntityCollection(String listJsonResponse) throws JsonSyntaxException {
        final Type listOfPoiEntityType = new TypeToken<List<PoiEntity>>(){}.getType();
        return this.gson.fromJson(listJsonResponse, listOfPoiEntityType);
    }
}
//package com.fernandocejas.android10.sample.data.entity.mapper;
//
//        import com.fernandocejas.android10.sample.data.entity.UserEntity;
//        import com.google.gson.Gson;
//        import com.google.gson.JsonSyntaxException;
//        import com.google.gson.reflect.TypeToken;
//        import java.lang.reflect.Type;
//        import java.util.List;
//        import javax.inject.Inject;
//
///**
// * Class used to transform from Strings representing json to valid objects.
// */
//public class UserEntityJsonMapper {
//
//    private final Gson gson;
//
//    @Inject
//    public UserEntityJsonMapper() {
//        this.gson = new Gson();
//    }
//
//    /**
//     * Transform from valid json string to {@link UserEntity}.
//     *
//     * @param userJsonResponse A json representing a user profile.
//     * @return {@link UserEntity}.
//     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
//     */
//    public UserEntity transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
//        final Type userEntityType = new TypeToken<UserEntity>() {}.getType();
//        return this.gson.fromJson(userJsonResponse, userEntityType);
//    }
//
//    /**
//     * Transform from valid json string to List of {@link UserEntity}.
//     *
//     * @param userListJsonResponse A json representing a collection of users.
//     * @return List of {@link UserEntity}.
//     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
//     */
//    public List<UserEntity> transformUserEntityCollection(String userListJsonResponse)
//            throws JsonSyntaxException {
//        final Type listOfUserEntityType = new TypeToken<List<UserEntity>>() {}.getType();
//        return this.gson.fromJson(userListJsonResponse, listOfUserEntityType);
//    }
//}