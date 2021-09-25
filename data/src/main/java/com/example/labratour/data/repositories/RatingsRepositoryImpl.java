package com.example.labratour.data.repositories;

import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.BuisnesPostExecutionThread;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.PlacesRepository;
import com.example.labratour.domain.repositories.PoiDetailesDomain;
import com.example.labratour.domain.repositories.RatingsRepository;
import com.example.labratour.domain.repositories.UserRepository;

import java.lang.reflect.Field;
import java.net.MalformedURLException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class RatingsRepositoryImpl implements RatingsRepository {
    private UserRepository userRepository;
    private PlacesRepository placesRepository;
    private AtributesRepository atributesRepository;
    private PoiDetailesDomain poiDetailesDomain = null;
    private ExecutionThread executionThread;
    private PostExecutionThread postExecutionThread;

    public RatingsRepositoryImpl(UserRepository userRepository, PlacesRepository placesRepository,AtributesRepository atributesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        this.userRepository = userRepository;
        this.placesRepository = placesRepository;
        this.atributesRepository = atributesRepository;
        this.executionThread = executionThread;
        //new scheduler that queuse work on current thread
        this.postExecutionThread = new BuisnesPostExecutionThread();
    }



    public Single<Atributes> buildPoiAtributesSingle(String poiId) throws MalformedURLException {
        return placesRepository.getPoiById(poiId);
    }
    public Single<UserAtributes> buildUserAtributesSingle(String userId) {
        return atributesRepository.getUserAtributes(userId);
   }
   @Override
   public Observable<Void> updateUserProfileByRate(String userId, String placeId, int rate){
        //to the usecase
        return Observable.create(new ObservableOnSubscribe<Void>() {
            //emitter is the observer from the usecase
            //this method will be called in the line "subscribeWith" in usecase
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                try{
                Single<Atributes> o1 = buildPoiAtributesSingle(placeId);
                Single<UserAtributes> o2 = buildUserAtributesSingle(userId);
                Single.zip(o1, o2, new BiFunction<Atributes, UserAtributes, UserAtributes>() {
                    @Override
                    public UserAtributes apply(Atributes atributes, UserAtributes userAtributes) throws Exception {
                        return calculateNewAtributesForUser(atributes, userAtributes, userAtributes.getRatesCounter(), rate);

                    }
                    //subscribe the zip to the observer argument
        }).subscribeOn(Schedulers.from(executionThread)).subscribe(new SingleObserver<UserAtributes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    //will be called when the zip will finish
                    @Override
                    public void onSuccess(UserAtributes value) {
                        //this subscribe the Single from user repository with the observer argument
                        atributesRepository.updateNewAtributes(value, userId).subscribe(new SingleObserver<Void>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Void value) {
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







    public UserAtributes calculateNewAtributesForUser (Atributes poiAtributes, UserAtributes userAtributes, int userRatesCount, int rate)throws NoSuchFieldException
    {
        Field[] atr= Atributes.class.getFields();
        UserAtributes  newUserAtributes = new UserAtributes();
        newUserAtributes.setRatesCounter(userRatesCount+1);
        Class userAtributesClass = UserAtributes.class;
        for (int i = 0; i< Atributes.class.getFields().length; i++) {
            try{
                String poiFieldName = (atr[i].getName());

                Double poiA = poiAtributes.getClass().getField(poiFieldName).getDouble(poiAtributes);
                Double userA = userAtributesClass.getField(poiFieldName).getDouble(userAtributes);

                Double newAtribute = ((poiA*rate/5)+(userA*userRatesCount))/(newUserAtributes.getRatesCounter());
                Field field = userAtributesClass.getField(poiFieldName);

            //    Object value = field.get(newUserAtributes);

                field.setDouble(newUserAtributes, newAtribute);

            }catch (NoSuchFieldException e){
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return newUserAtributes;


    }


}
























