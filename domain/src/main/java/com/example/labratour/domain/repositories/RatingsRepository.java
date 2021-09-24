package com.example.labratour.domain.repositories;


import io.reactivex.Observable;

public interface RatingsRepository {
    public Observable<Void> updateUserProfileByRate(String userId, String placeId, int rate);
}
