package com.example.labratour.domain.repositories;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.labratour.domain.AtributesCalculator;
import com.example.labratour.domain.BuisnesPostExecutionThread;
import com.example.labratour.domain.UserProfileManager;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;

import java.util.Vector;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
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

    @Override
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
        });}}

















