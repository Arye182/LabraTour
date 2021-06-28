package com.example.labratour.domain.useCases.LowLevelUsecases;

import com.example.labratour.domain.Entity.User;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;
import com.example.labratour.domain.useCases.UseCase;

import io.reactivex.Observable;

public class SaveNewAtributesToUserUseCase extends UseCase<Void , User> {

    private UserRepository userRepository;
    public  SaveNewAtributesToUserUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    public Observable buildUseCaseObservable(User requestInput) {
        return this.userRepository.updateUser(requestInput, "Eror saving raye");
    }


}
