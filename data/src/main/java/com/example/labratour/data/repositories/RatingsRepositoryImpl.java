package com.example.labratour.data.repositories;

import android.os.Looper;
import android.util.Log;

import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.repositories.PlacesRepository;
import com.example.labratour.domain.PoiDetailesDomain;
import com.example.labratour.domain.repositories.RatingsRepository;

import java.net.MalformedURLException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class RatingsRepositoryImpl implements RatingsRepository {
   // private UserRepository userRepository;
    private PlacesRepository placesRepository;
    private AtributesRepositoryImpl atributesRepository;
    private PoiDetailesDomain poiDetailesDomain = null;
    //private ExecutionThread executionThread;

    public RatingsRepositoryImpl( PlacesRepository placesRepository, AtributesRepositoryImpl atributesRepository
            ) {
        this.placesRepository = placesRepository;
        this.atributesRepository = atributesRepository;

        //new scheduler that queuse work on current thread

    }



    public Single<Atributes> buildPoiAtributesSingle(String poiId) throws MalformedURLException {
        Log.e("UpdateUseCase","inside buildPoi befor calling getPoiById run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

        return placesRepository.getPoiById(poiId);
    }
    public Single<UserAtributes> buildUserAtributesSingle(String userId) {
        return atributesRepository.getUserAtributes(userId);
   }
   @Override
   public Observable<String> updateUserProfileByRate(String userId, String placeId, int rate, ExecutionThread executionThread){
        //to the usecase
        return Observable.create(new ObservableOnSubscribe<String>() {
            //emitter is the observer from the usecase
            //this method will be called in the line "subscribeWith" in usecase
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                Single<Atributes> o1 = buildPoiAtributesSingle(placeId);
                Single<UserAtributes> o2 = buildUserAtributesSingle(userId);
                Single.zip(o1, o2, new BiFunction<Atributes, UserAtributes, UserAtributes>() {
                    @Override
                    public UserAtributes apply(Atributes atributes, UserAtributes userAtributes) throws Exception {
                        return calculateNewAtributesForUser(atributes, userAtributes, userAtributes.getRatesCounter(), rate);
                    }
                    //subscribe the zip to the observer argument
        }).map(new Function<UserAtributes, HashMap<String, Object>>() {

                    @Override
                    public HashMap<String, Object> apply(UserAtributes userAtributes) throws Exception {

                        return  UserDataMapper.transform(userAtributes)
                                ;

                    }
                })
                        //.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
                    .subscribe(new SingleObserver<HashMap<String, Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(HashMap<String, Object> value) {
                        //this subscribe the Single from user repository with the observer argument
                        atributesRepository.updateNewAtributes(value, userId).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String value) {
                                if (!emitter.isDisposed()){
                                    emitter.onNext(value);

                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if(!emitter.isDisposed()){
                                    emitter.onError(e.getCause());
                                }
                            }
                        });}

                    //will be called when the zip will finish

                        //will be called in case of error in zip
                    @Override
                    public void onError(Throwable e) {
                        if(!emitter.isDisposed()){
                            emitter.onError(e.getCause());
                        }
                    }
                });}catch (Exception e){
                if (!emitter.isDisposed()){
                emitter.onError(e.getCause());
                }
                }
                }
            });
        }







    public UserAtributes calculateNewAtributesForUser (Atributes poiAtributes, UserAtributes userAtributes, int userRatesCount, int rate)
    {
        Log.e("UpdateUseCase","inside calculateNewAtributesForUser called from zip bifunction run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

        HashMap<String,Object> resultAtributes = new HashMap<>();
        HashMap<String, Object> userAtributesMap = UserDataMapper.transform(userAtributes);
        HashMap<String, Object> poiAtributesMap = PlaceDetailesDataMapper.transform(poiAtributes);

        for (String key : poiAtributesMap.keySet()) {
      double poiValue = 0;
      double userValue = 0;
      double resultValue = 0;

      if (poiAtributesMap.get(key).getClass().isPrimitive()&&userAtributesMap.get(key).getClass().isPrimitive()) {
        poiValue = ((double) poiAtributesMap.get(key));
        userValue = ((double) userAtributesMap.get(key));
        resultValue = ((poiValue * rate / 5) + (userValue * userRatesCount)) / (userRatesCount + 1);
        resultAtributes.put(key, resultValue);
      }
    }
        UserAtributes userAtributes1 = UserDataMapper.transform(resultAtributes);
        userAtributes1.setRatesCounter(userRatesCount+1);
        return userAtributes1;





    }


}
























