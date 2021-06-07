package repositories.datasource;

import com.data.net.RestApi;

import java.util.List;

import Entity.RatedPoiEntity;
import io.reactivex.Observable;

public class PoiDataStore {
    private final RestApi restApi;

    public PoiDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    Observable<List<RatedPoiEntity>> userEntityList(){
        return this.restApi.userEntityList();
    }
    /*
             * @param userId The id to retrieve user data.
   */
    Observable<RatedPoiEntity> userEntityDetails(final int userId)
    {
        return this.restApi.userEntityById(userId);
    }
}
