package com.data.net;

 interface RestApi {
}
///package com.fernandocejas.android10.sample.data.net;
//
//        import com.fernandocejas.android10.sample.data.entity.UserEntity;
//        import io.reactivex.Observable;
//        import java.util.List;
//
///**
// * RestApi for retrieving data from the network.
// */
//public interface RestApi {
//    String API_BASE_URL =
//            "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";
//
//    /** Api url for getting all users */
//    String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
//    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
//    String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";
//
//    /**
//     * Retrieves an {@link Observable} which will emit a List of {@link UserEntity}.
//     */
//    Observable<List<UserEntity>> userEntityList();
//
//    /**
//     * Retrieves an {@link Observable} which will emit a {@link UserEntity}.
//     *
//     * @param userId The user id used to get user data.
//     */
//    Observable<UserEntity> userEntityById(final int userId);
//}