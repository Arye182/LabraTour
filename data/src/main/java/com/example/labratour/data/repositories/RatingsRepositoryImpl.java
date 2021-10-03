package com.example.labratour.data.repositories;

import android.os.Looper;
import android.util.Log;

import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.PoiDetailesDomain;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.repositories.PlacesRepository;
import com.example.labratour.domain.repositories.RatingsRepository;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

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



    public Single<HashMap<String, Object>> buildPoiAtributesSingle(String poiId)  {
      Log.i( "rate", "build poi "+ poiId);

        return placesRepository.getPoiById(poiId);
    }
    public Single<HashMap<String , Double>> buildUserAtributesSingle(String userId) {
        return atributesRepository.getUserAtributes(userId);
   }
   @Override
   public Observable<String> updateUserProfileByRate(String userId, String placeId, int rate ){
    return Observable.create(
        new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            try {
              Log.i("UpdateUseCase", "subscribe1");
              Single<HashMap<String, Object>> o1 = buildPoiAtributesSingle(placeId);
              Single<HashMap<String, Double>> o2 = buildUserAtributesSingle(userId);
              Single.zip(o1, o2, new BiFunction<
                      HashMap<String, Object>,
                      HashMap<String, Double>,
                      HashMap<String, Double>>() {
                @Override
                public HashMap<String, Double> apply(
                        HashMap<String, Object> atributes,
                        HashMap<String, Double> userAtributes) {

                  assert atributes != null;
                  assert userAtributes != null;
                  return calculateNewAtributesForUser2(
                          atributes, userAtributes, userAtributes.get("ratesCounter"), rate);
                }

                // subscribe the zip to the observer argument
              })
                  .subscribe(
                      new SingleObserver<HashMap<String, Double>>() {


                        @Override
                        public void onSubscribe(Disposable d) {
                          Log.i("UpdateUseCase", "subscribe2");
                        }

                        @Override
                        public void onSuccess(HashMap<String, Double> value) {
                          if (value != null) {
                            // this subscribe the Single from user repository with the observer
                            // argument
                            atributesRepository
                                .updateNewAtributes(value, userId)
                                .subscribe(
                                    new SingleObserver<Void>() {
                                      @Override
                                      public void onSubscribe(Disposable d) {}

                                      @Override
                                      public void onSuccess(Void value) {
                                        if (!emitter.isDisposed()) {
                                          emitter.onNext("profile had been updated");
                                        }
                                      }

                                      @Override
                                      public void onError(Throwable e) {
                                        if (!emitter.isDisposed()) {
                                          emitter.onError(e);
                                        }
                                      }
                                    });
                          }
                          Log.i("UpdateUseCase", "zip returned null");
                        }

                        // will be called when the zip will finish

                        // will be called in case of error in zip
                        @Override
                        public void onError(Throwable e) {
                          if (!emitter.isDisposed()) {
                            emitter.onError(e);
                          }
                        }
                      });
            } catch (Exception e) {
              if (!emitter.isDisposed()) {
                emitter.onError(e);
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

  public HashMap<String, Double> calculateNewAtributesForUser2 (HashMap<String, Object> poiAtributes, HashMap<String, Double> userAtributes, double userRatesCount, int rate)
  {
    Log.i("UpdateUseCase","inside calculateNewAtributesForUser called from zip bifunction run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

    HashMap<String,Double> resultAtributes = new HashMap<String, Double>();


    for (String key : poiAtributes.keySet()) {
      double poiValue = 0;
      double userValue = 0;
      double resultValue = 0;

    //  if ((Objects.requireNonNull(poiAtributes.get(key)).getClass().isPrimitive())&&(userAtributes.get(key).getClass().isPrimitive())) {
        if(poiAtributes.get(key).equals(true)){ poiValue = 1;}
        userValue = ((double) userAtributes.get(key));
        resultValue = ((poiValue * rate / 5) + (userValue * userRatesCount)) / (userRatesCount + 1);
        resultAtributes.put(key, resultValue);
      }

    //UserAtributes userAtributes1 = UserDataMapper.transform(resultAtributes);
    resultAtributes.put("ratesCounter", (double) (userRatesCount+1));
    return resultAtributes;





  }




}
























