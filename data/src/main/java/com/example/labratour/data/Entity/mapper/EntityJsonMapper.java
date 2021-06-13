package com.example.labratour.data.Entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

public abstract class EntityJsonMapper<T> {
    public final Gson gson;
    public EntityJsonMapper() {
        this.gson = new Gson();
    }

    public T transformEntity(String jsonRespomse) throws JsonSyntaxException {
        return null;
    }

    public List<T> transformEntityCollection(String listJsonResponse) throws JsonSyntaxException {
        return null;
    }


}
