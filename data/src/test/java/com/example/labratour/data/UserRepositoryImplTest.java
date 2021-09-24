package com.example.labratour.data;

import com.example.labratour.domain.Atributes;

import org.junit.Test;

import java.lang.reflect.Field;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


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
        return new Atributes();



        }
    @Test
    public void addition_isCorrect() {
         newAtributesForUser("2", 4, "2").subscribeWith(new DisposableSingleObserver<Atributes>() {
             @Override
             public void onSuccess(Atributes value) {
                 System.out.println(value.toString());

             }

             @Override
             public void onError(Throwable e) {

             }
         });
    }
}