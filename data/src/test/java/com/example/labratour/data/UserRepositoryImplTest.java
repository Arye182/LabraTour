////
//
//import com.example.labratour.data.Entity.UserEntity;
//import com.example.labratour.data.datasource.CloudUserDataSource;
//import com.example.labratour.data.repositories.UserRepositoryImpl;
//import com.example.labratour.domain.Entity.User;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import io.reactivex.Observable;
//
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserRepositoryImplTest {
//    private static final String FAKE_PASSWORD = "123";
//    private static final  String FAKE_EMAIL= "arye.amsalem@gmail.com";
//
//    private UserRepositoryImpl userRepository;
//    @Mock
//    private FirebaseAuth mockFirebase;
//   // @Mock private UserDataMapper mockUserDataMapper;
//   @Mock  private  CloudUserDataSource mockCloudUserDataSource;
//   @Mock private AuthResult mockAuthResult;
//
//
//    @Before
//    public void setUp() {
//        userRepository = new UserRepositoryImpl(mockFirebase);
//       // given(mockUserDataSourceFactory.createCloudDataSource()).willReturn(mockCloudUserDataSource);
//
//
//    }
//
//    @Test
//    public void testLoginHappyCase() {
//        Observable<User> userObservable =  userRepository.login(FAKE_EMAIL, FAKE_PASSWORD);
//
//        userRepository.login(FAKE_EMAIL, FAKE_PASSWORD);
//        verify(mockCloudUserDataSource).login(new UserEntity(FAKE_EMAIL, FAKE_PASSWORD));
//
//    }
//
////    @Test
////    public void testGetUserHappyCase() {
////        UserEntity userEntity = new UserEntity();
////        given(mockUserDataStore.userEntityDetails(FAKE_USER_ID)).willReturn(Observable.just(mockAuthResult));
////        userDataRepository.user(FAKE_USER_ID);
////
////        verify(mockUserDataStoreFactory).create(FAKE_USER_ID);
////        verify(mockUserDataStore).userEntityDetails(FAKE_USER_ID);
////    }
//}
