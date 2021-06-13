package com.example.labratour.domain.useCases;

//import com.example.labratour.domain.Entity.PoiPresentable;
//import com.fernandocejas.arrow.checks.Preconditions;
//
//import java.util.List;
//
//import executors.ExecutionThread;
//import executors.PostExecutionThread;
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import repositories.PoiPresentableRepository;
//
//public class getPoiListForUserRating extends UseCase <List<PoiPresentable>, getPoiListForUserRating.RequestInput>{
//    private final PoiPresentableRepository poiPresentableRepository;
//
//    @Inject
//    getPoiListForUserRating(PoiPresentableRepository poiPresentableRepository, ExecutionThread executionThread,
//                   PostExecutionThread postExecutionThread) {
//        super(executionThread, postExecutionThread);
//        this.poiPresentableRepository = poiPresentableRepository;
//    }
//    @Override
//    Observable<List<PoiPresentable>> buildUseCaseObservable(RequestInput requestInput) {
//        Preconditions.checkNotNull(requestInput);
//        return this.poiPresentableRepository.poiListForUserRating(requestInput.userHomeDistrict);
//    }
//    public static final class RequestInput {
//
//        private final String userHomeDistrict;
//
//        private RequestInput(String  userHomeDistrict){
//            this.userHomeDistrict = userHomeDistrict;
//        }
//
//        public static RequestInput forUser(String userHomeDistrict) {
//            return new RequestInput(userHomeDistrict);
//        }
//    }
//}
//
//    @Override
//    protected void addDisposable(Observer subscribeWith) {
//
//    }
//
//    @Override
//    Observable<List<PoiPresentable>> buildUseCaseObservable(getPoiListForUserRating.RequestInput requestInput) {
//        return null;
//    }
//}
