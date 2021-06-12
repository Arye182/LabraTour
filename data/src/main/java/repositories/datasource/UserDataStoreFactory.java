package repositories.datasource;

//public class UserDataStoreFactory {
//
//    private final Context context;
//    private final UserCache userCache;
//
//    @Inject
//    UserDataStoreFactory(@NonNull Context context, @NonNull UserCache userCache) {
//        this.context = context.getApplicationContext();
//        this.userCache = userCache;
//    }
//
//    /**
//     * Create {@link UserDataStore} from a user id.
//     */
//    public CloudUserDataSource create(int userId) {
//        CloudUserDataSource cloudUserDataSource;
//
//        if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
//            cloudUserDataSource = new CloudUserDataSource(this.userCache);
//        } else {
//            cloudUserDataSource = createCloudDataStore();
//        }
//
//        return cloudUserDataSource;
//    }
//
//    /**
//     * Create {@link UserDataStore} to retrieve data from the Cloud.
//     */
//    public UserDataStore createCloudDataStore() {
//        final UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
//        final FirebaseDatabase firebaseDatabase = new FirebaseDatabase(this.context, userEntityJsonMapper);
//
//        return new CloudUserDataStore(FirebaseFirestore.getInstance());
//    }
//}
