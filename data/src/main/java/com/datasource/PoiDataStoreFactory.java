package com.datasource;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

@Singleton
public class PoiDataStoreFactory {
    private final Context context;

    public PoiDataStoreFactory(@NotNull Context context) {
        this.context = context.getApplicationContext();
    }
//    public PoiDataStore create(int poiId)

//    /**
//     * Create {@link UserDataStore} from a user id.
//     */
//    public UserDataStore create(int userId) {
//        UserDataStore userDataStore;
//
//        if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
//            userDataStore = new DiskUserDataStore(this.userCache);
//        } else {
//            userDataStore = createCloudDataStore();
//        }
//
//        return userDataStore;
//    }
//
//    /**
//     * Create {@link UserDataStore} to retrieve data from the Cloud.
//     */
//    public UserDataStore createCloudDataStore() {
//        final UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
//        final RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);
//
//        return new CloudUserDataStore(restApi, this.userCache);
//    }
////}
}
