//package com.example.labratour.domain.useCases;
//
//import android.util.Log;
//
//import com.example.labratour.domain.Entity.NearbyPlaceEntity;
//import com.example.labratour.domain.executors.ExecutionThread;
//import com.example.labratour.domain.executors.PostExecutionThread;
//import com.example.labratour.domain.repositories.NearbyPlacesRepository;
//import com.fernandocejas.arrow.checks.Preconditions;
//
//import io.reactivex.Observable;
//import io.reactivex.observers.DisposableObserver;
//import io.reactivex.schedulers.Schedulers;
//
//public class GetNearbyPlacesByTypeUseCase  extends NearbyPlacesUseCase{
//
//
//  public GetNearbyPlacesByTypeUseCase(NearbyPlacesRepository nearbyPlacesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
//    super(nearbyPlacesRepository, executionThread, postExecutionThread);
//  }
//
//  public void execute(DisposableObserver observer, String lat, String lon, String type) {
//      Preconditions.checkNotNull(observer);
//      final Observable<NearbyPlaceEntity
//              > observable =
//              this.buildUseCaseObservable(lat,  lon, type)
//                      .subscribeOn(Schedulers.from(executionThread))
//                      .observeOn(postExecutionThread
//                              .getScheduler());
//      addDisposable(observable.subscribeWith(observer));
//    }
//
//
//    public Observable<NearbyPlaceEntity> buildUseCaseObservable(String lat, String lon, String type) {
//      Log.i("testNearbyTypeUseCase", "lat:"+lat+ "lon:" + lon+ type);
//
//      return repository.get(lat, lon, type);
//    }
//
//
//
//}
//
//
