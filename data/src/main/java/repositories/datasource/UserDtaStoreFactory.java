package repositories.datasource;

import javax.inject.Singleton;

@Singleton
public class UserDataStoreFactory {

    private final Context context;
    private final UserCache userCache;

    @Inject
    UserDataStoreFactory(@NonNull Context context, @NonNull UserCache userCache) {
        this.context = context.getApplicationContext();
        this.userCache = userCache;
    }

    /**
     * Create {@link UserDataStore} from a user id.
     */
    public UserDataStore create(int userId) {
        UserDataStore userDataStore = createUserDataStore();
        return userDataStore;
    }

    /**
     * Create {@link UserDataStore} to retrieve data from the Cloud.
     */
    public UserDataStore createUserDataStore() {
        final UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
        final RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);

        return new UserDataStore(restApi, this.userCache);
    }
}
