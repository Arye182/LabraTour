package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

public class UserHashMapper extends EntityJsonMapper<UserEntity>{
    public Map<String, Object> toMap(UserEntity userEntity) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", userEntity.getUserId());
        result.put("email", userEntity.getEmail());
        result.put("last_name", userEntity.getLast_name());
        result.put("first_name", userEntity.getFirst_name());
        result.put("phone", userEntity.getPhone());
        result.put("count_rate", userEntity.getCountRate());
        result.put("address", userEntity.getAddress());
        result.put("atributes", userEntity.getAtributes());


        return result;
    }

    @Override
    public String transformToJson(UserEntity entity) {
        return this.gson.toJson(entity);
    }
}
