package repositories;

import com.example.Entity.User;

import io.reactivex.Completable;
import io.reactivex.Observable;


//@param userId The user id used to retrieve user data.
interface UserRepository extends Repository {
    public Observable<User> getUser(String userId, boolean fromServer);
    public Completable signin(String password, String  email);

    public void saveUser(User user);
            }
