//package com.repositories;
//
//
//import com.example.repositories.UserRepository;
//
//public class PoiRepositoryImpl {
//
//  // package com.fernandocejas.android10.sample.data.repository;
//  //
//  //        import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
//  //        import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStore;
//  //        import
//  // com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
//  //        import com.fernandocejas.android10.sample.domain.User;
//  //        import io.reactivex.Observable;
//  //        import java.util.List;
//  //        import javax.inject.Inject;
//  //        import javax.inject.Singleton;
//  //
//  /// **
//  // * {@link UserRepository} for retrieving user data.
//  // */
//  // @Singleton
//  // public class UserDataRepository implements UserRepository {
//  //
//  private final RatedPoiEntityDataMapper ratedPoiEntityDataMapper;
//
//  /**
//   * Constructs a {@link UserRepository}.
//   * @param userEntityDataMapper {@link UserEntityDataMapper}.
//   */
//  @Inject
//  PoiRepositoryImpl(RatedPoiEntityDataMapper ratedPoiEntityDataMapper) {
//    this.ratedPoiEntityDataMapper = ratedPoiEntityDataMapper;
//
//  }
//  //
//      @Override public Observable<List<User>> users() {
//          //we always get all users from the cloud
//          final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
//          //userlist from api
//          return userDataStore.userEntityList().map(this.userEntityDataMapper::transform);
//      }
//  //
//  //    @Override public Observable<User> user(int userId) {
//  //        final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
//  //        return
//  // userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transform);
//  //    }
//}
