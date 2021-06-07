package Entity.mapper;

import com.example.Entity.RatedPoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import Entity.RatedPoiEntity;

@Singleton
public class RatedPoiEntityDataMapper {

    @Inject
    void ratedPoiEntityDataMapper() {}


    public RatedPoi transform(RatedPoiEntity ratedPoiEntity, int userRate, String placeId, String userId) {
        RatedPoi ratedPoi = null;
        if (ratedPoiEntity != null) {
            ratedPoi = new RatedPoi(placeId);
            ratedPoi.setUserRating(userRate);
            ratedPoi.setUserId(userId);
            ratedPoi.setPriceLevel(ratedPoiEntity.getPriceLevel());
            ratedPoi.setAggregatedUsersRatings(ratedPoiEntity.getAggregatedUsersRatings());
            ratedPoi.setTypes(ratedPoiEntity.getTypes());
        }
        return ratedPoi;
    }
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.

