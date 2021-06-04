package repositories;

import android.database.Observable;

import com.example.Entity.User;


//@param userId The user id used to retrieve user data.
public interface UserRepository {
    Observable<User> user(final int userId);
}
