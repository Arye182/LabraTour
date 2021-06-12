package repositories.datasource;

//@Singleton
////public class RatedPoiDataStoreFactory {
////    private final Context context;
////    private final UserRatingsCache userRatingsCache;
////    @Inject
////    RatedPoiDataStoreFactory(@NonNull Context context, @NonNull UserRatingCache userRatingsCache) {
////        this.context = context.getApplicationContext();
////        this.userRatingsCache = userRatingsCache;
////    }
////
////    /**
////     * Create {@link UserDataStore} from a user id.
////     */
////    public UserRatingsDiskDataStore create(String placeId) {
////        UserRatingsDiskDataStore userDataStore;
////
////    if (!this.userRatingsCache.isExpired() && this.userRatingsCache.isCached(placeId)) {
////      UserRatingsDiskDataStore userRatingsDiskDataStore =
////          new UserRatingsDiskDataStore(this.userRatingsCache);
////            }
////    else {
////        return  createCloudDataStore()
////    }
////    }
////
////    public UserRatingsCloudDataStore createCloudDataStore() {
////        final SavedRatedPoiEntityJsonMapper savedRatedPoiEntityJsonMapper = new SavedRatedPoiEntityJsonMapper();
////        final RestApi restApi = new SavedDataRestApi(this.context, savedRatedPoiEntityJsonMapper) ;
////
////        return new UserRatingsCloudDataStore(restApi, this.userRatingsCache);
////    }
////}
////
