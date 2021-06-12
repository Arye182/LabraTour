package com.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import com.Entity.RatedPoiEntity;
import Entity.mapper.PoiEntityJsonMapper;
import io.reactivex.Observable;

public class RestApiService implements RestApi{
    private final PoiEntityJsonMapper poiEntityJsonMapper;
    private final Context context;



    public RestApiService(PoiEntityJsonMapper poiEntityJsonMapper, Context context) {
        if (context == null || poiEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters canot be null");
        }
        this.poiEntityJsonMapper = poiEntityJsonMapper;
        this.context = context.getApplicationContext();
    }

    @Override
    public Observable<List<RatedPoiEntity>> poiTrainSet(string userHomeDistrict) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responseUserEntities = getTrainSetOfPoiEntitiesFromApi();
                    if (responseUserEntities != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntityCollection(
                                responseUserEntities));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<RatedPoiEntity> userEntityById(int userId) {
        return null;
    }


    private String getTrainSetOfPoiEntitiesFromApi(string userHomeDistrict) throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_USER_LIST + userHomeDistrict).requestSyncCall();
    }

    private String getUserDetailsFromApi(int userId) throws MalformedURLException {
        String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
//@Singleton
//class BandsApiService
//        @Inject constructor(retrofit: Retrofit) : BandsApi {
//private val musicApi by lazy { retrofit.create(BandsApi::class.java) }
//        override fun bands(): Single<List<BandEntity>> =
//        musicApi
//        .bands()
//        .onErrorResumeNext { error: Throwable -> Single.error(translate(error)) }
//        override fun bandDetails(bandId: Int): Single<BandDetailsEntity> =
//        musicApi
//        .bandDetails(bandId)
//        .onErrorResumeNext { error: Throwable -> Single.error(translate(error)) }
//private fun translate(throwable: Throwable): Throwable =
//        when(throwable) {
//        is JsonMappingException -> ServerErrorException(throwable)
//        is HttpException -> ServerErrorException(throwable)
//        else -> throwable
//        }
//        }