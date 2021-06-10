package useCases;


import com.fernandocejas.arrow.checks.Preconditions;

import executors.ExecutionThread;
import executors.PostExecutionThread;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import repositories.UserRepository;

public class LogInUseCase extends UseCase<Void, LogInUseCase.RequestInput> {
     private UserRepository userRepository;

    public LogInUseCase(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void execute(DisposableObserver<Void> observer, String password, String email) {
        Preconditions.checkNotNull(observer);
        final Observable<Void> observable =
                this.buildUseCaseObservable(new RequestInput(password, email))
                        .subscribeOn(Schedulers.from(executionThread))
                        .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }
    public Observable buildUseCaseObservable(RequestInput requestInput) {
        return userRepository.login(requestInput.password, requestInput.email);
    }

    @Override
    protected void addDisposable(Observer<? super Void> subscribeWith) {

    }

    public static final class RequestInput {

        private final String password;
        private final String email;

        private RequestInput(String password, String email){
            this.password = password;
            this.email = email;
        }

        public static RequestInput forUser(String password, String email) {
            return new RequestInput(password, email);
        }
}
}
