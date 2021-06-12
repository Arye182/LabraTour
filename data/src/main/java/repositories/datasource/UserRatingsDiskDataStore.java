//package repositories.datasource;
//
//import java.util.List;
//
//import Entity.RatedPoiEntity;
//import io.reactivex.Observable;
//
//class UserRatingsDiskDataStore  {
//
//    private final UserRatingsCache userRatingsCache;
//
//
//    UserRatingsDiskDataStore(UserRatingsCache userRatingsCache) {
//        this.userRatingsCache = userRatingsCache;
//    }
//
//    public Observable<List<RatedPoiEntity>> ratedPoiEntityList(final String userId) {
//        //TODO: implement simple cache for retrieving ratings of users.
//        throw new UnsupportedOperationException("Operation is not available!!!");
//    }
//
//    public Observable<RatedPoiEntity> ratedPoiEntity(final String placeId) {
//        return this.userRatingsCache.get(placeId);
//    }
//}
