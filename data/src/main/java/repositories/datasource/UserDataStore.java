package repositories.datasource;

import com.data.net.RestApi;

import java.util.List;

import Entity.UserEntity;
import io.reactivex.Observable;

public class UserDataStore {
    private final RestApi restApi;

    public UserDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    Observable<List<UserEntity>> userEntityList(){
        return this.restApi.userEntityList();
    }
    /*
             * @param userId The id to retrieve user data.
   */
    Observable<UserEntity> userEntityDetails(final int userId)
    {
        return this.restApi.userEntityById(userId);
    }
}
