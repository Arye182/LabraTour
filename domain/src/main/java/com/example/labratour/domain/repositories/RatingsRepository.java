package com.example.labratour.domain.repositories;


import io.reactivex.Observable;

public interface RatingsRepository {
    public Observable updateRating(String userId, int rate, String placeId);
}
