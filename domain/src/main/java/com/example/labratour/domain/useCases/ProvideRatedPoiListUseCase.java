package com.example.labratour.domain.useCases;

import com.example.labratour.domain.NewRateDomain;
import com.example.labratour.domain.UserProfileManager;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.AtributedPoiRepository;
import com.example.labratour.domain.useCases.LowLevelUsecases.SaveNewAtributesToUserUseCase;

import java.util.List;

import io.reactivex.Observable;

public class ProvideRatedPoiListUseCase extends UseCase<String, List<NewRateDomain>> {
    private final AtributedPoiRepository atributedPoiRepository;
    private final UserProfileManager userProfileManager;
    private  final SaveNewAtributesToUserUseCase saveNewAtributesToUserUseCase;
    public ProvideRatedPoiListUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, AtributedPoiRepository atributedPoiRepository, UserProfileManager userProfileManager, SaveNewAtributesToUserUseCase saveNewAtributesToUserUseCase) {
        super(executionThread, postExecutionThread);
        this.atributedPoiRepository = atributedPoiRepository;
        this.userProfileManager = userProfileManager;

        this.saveNewAtributesToUserUseCase = saveNewAtributesToUserUseCase;
    }

    @Override
    public Observable<String> buildUseCaseObservable(List<NewRateDomain> requestInput) {
        return null;
    }

//    @Override
//    public Observable buildUseCaseObservable(List<NewRateDomain> requestInput) {
//        return atributedPoiRepository.getListByNewRateIdList(requestInput).map(new Function<List<AtributedPoi>, User>() {
//            @Override
//            public User apply(List<AtributedPoi> authResult) throws Exception {
//                return userProfileManager.updateByListOfPois(authResult);
//            }
//        })










    }






