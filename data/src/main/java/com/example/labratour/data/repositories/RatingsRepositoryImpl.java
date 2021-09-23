package com.example.labratour.data.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.AtributesCalculator;
import com.example.labratour.domain.BuisnesPostExecutionThread;
import com.example.labratour.domain.UserProfileManager;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.PlacesRepository;
import com.example.labratour.domain.repositories.PoiDetailesDomain;
import com.example.labratour.domain.repositories.RatingsRepository;
import com.example.labratour.domain.repositories.UserRepository;

import java.lang.reflect.Field;
import java.util.Vector;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RatingsRepositoryImpl implements RatingsRepository {
    private UserRepository userRepository;
    private PlacesRepository placesRepository;
    private UserProfileManager userProfileManager;
    private PoiDetailesDomain poiDetailesDomain = null;
    private ExecutionThread executionThread;
    MutableLiveData<Boolean> getUserAtributesVectorTaskStatus = new MutableLiveData<Boolean>();
    MutableLiveData<Boolean>  getPoiAtributesVectorTaskStatus = new MutableLiveData<Boolean>();
    private LifecycleOwner UserProfileLifecycleOwner;
    private PostExecutionThread postExecutionThread;
     Vector<Integer> userNewWheightsOfPoiAtributes;
     Vector<Integer> userProfileCurrentAtributes;

    public RatingsRepositoryImpl(UserRepository userRepository, PlacesRepository placesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        this.userRepository = userRepository;
        this.placesRepository = placesRepository;
        this.userProfileManager = new UserProfileManager(new AtributesCalculator());
        this.executionThread = executionThread;
        //new scheduler that queuse work on current thread
        this.postExecutionThread = new BuisnesPostExecutionThread();
    }


    public  io.reactivex.Observable updateRating(String userId, int rate, String placeId) {

        return io.reactivex.Observable.create(
                new ObservableOnSubscribe<Void>() {
                    @Override
                    //happens when an observer subscribe(for example the observer in execute)
                    public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                        if (!emitter.isDisposed()){
                        Single poiAtributesObservable = placesRepository.getPoiById(placeId).subscribeOn(Schedulers.from(executionThread))
                                .observeOn(postExecutionThread.getScheduler());
                        //todo maybe add to disposables
                        poiAtributesObservable.subscribeWith(new DisposableSingleObserver<Vector<Integer>>() {

                            @Override
                            public void onSuccess(Vector<Integer> value) {
                                if(value!=null){
                                    userNewWheightsOfPoiAtributes = userProfileManager.userNewWheightsOfAtributes(value, rate);
                                    getPoiAtributesVectorTaskStatus.postValue(true);
                                    }
                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onError(e);
                            }



                        });






                        Single<Vector<Integer>> userDomainSingle = userRepository.getUserAtributes(userId).subscribeOn(Schedulers.from(executionThread))
                            .observeOn(postExecutionThread.getScheduler());
                        userDomainSingle.subscribeWith(new DisposableSingleObserver<Vector<Integer>>() {


                            @Override
                            public void onSuccess(Vector<Integer> value) {
                                if(value!=null){
                                    userProfileCurrentAtributes = value;
                                    getUserAtributesVectorTaskStatus.postValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                        //todo add to disposables


    }
        }
        });}
    public Single<Atributes> buildPoiAtributesSingle(String poiId){
        return Single.create(new SingleOnSubscribe<Atributes>() {
            @Override
            public void subscribe(SingleEmitter<Atributes> e) throws Exception {
                Atributes atributes = new Atributes();
                atributes.setAlwaysOpen(false);
                atributes.setAmusement_park(false);
                atributes.setAquarium(false);
                atributes.setCafe(true);
                atributes.setArt_gallery(true);
                atributes.setPrice_level(3/5);
                atributes.setUsersAggragateRating(4/5);

//                atributes.add(1,0);
//                atributes.add(2,0);
//                atributes.add(3,1);
//                atributes.add(4,1);
//                atributes.add(5,3/5);
//                atributes.add(6,4/5);
                e.onSuccess(atributes);
            }
        });
        }

    public Single<Atributes> buildUserAtributesSingle(String userId){
        return Single.create(new SingleOnSubscribe<Atributes>() {
            @Override
            public void subscribe(SingleEmitter<Atributes> e) throws Exception {
                Atributes atributes = new Atributes();
                atributes.setAlwaysOpen(false);
                atributes.setAmusement_park(false);
                atributes.setAquarium(false);
                atributes.setCafe(true);
                atributes.setArt_gallery(true);
                atributes.setPrice_level(3/5);
                atributes.setUsersAggragateRating(4/5);

//                atributes.add(1,0);
//                atributes.add(2,0);
//                atributes.add(3,1);
//                atributes.add(4,1);
//                atributes.add(5,3/5);
//                atributes.add(6,4/5);
                e.onSuccess(atributes);
            }
        });
       // return Single.just(new Vector<Double>(8, 1));

    }
    public Single<Atributes> newAtributesForUser(String userId, int rate, String placeId) {
        Single<Atributes> o1 = buildPoiAtributesSingle("2");
        Single<Atributes> o2 = buildUserAtributesSingle("3");
        return Single.zip(o1, o2, new BiFunction<Atributes, Atributes, Atributes>() {
            @Override
            public Atributes apply(Atributes atributes, Atributes atributes2) throws Exception {
                return calculateNewAtributesForUser(atributes, atributes2, 5,  4);
            }
        });
    }

    @Override
    public Observable<Void> updateUserProfileByRate(String userId, String placeId, int rate) {
        return null;
    }
    private Atributes calculateNewAtributesForUser (Atributes poiAtributes, Atributes userAtributes, int userRatesCount, int rate)throws NoSuchFieldException{
        Field[] atr= Atributes.class.getDeclaredFields();

        for (int i = 0; i< Atributes.class.getFields().length; i++) {
            try{
                Double poiA = poiAtributes.getClass().getDeclaredField(atr[i].getName()).getDouble(poiAtributes);
                Double userA = poiAtributes.getClass().getDeclaredField(atr[i].getName()).getDouble(userAtributes);

                Double newAtribute = ((poiA*rate/5)+(userA*userRatesCount))/(userRatesCount+1);

            }catch (NoSuchFieldException e){

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }}
        //todo tramsform back to Atributes
        return new Atributes();



    }


  public static Vector<Double> fromAtributesToVector(Atributes atributes) {
    return new Vector<>();
    // TODO: 21/09/2021 a
  }
}

















