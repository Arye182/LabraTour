package com.example.labratour.domain.repositories;


import com.example.labratour.domain.executors.ExecutionThread;

import io.reactivex.Observable;

public interface RatingsRepository {

    public Observable<String> updateUserProfileByRate(String userId, String placeId, int rate, ExecutionThread executionThread);
}
