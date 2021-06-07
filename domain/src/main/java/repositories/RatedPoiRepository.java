package repositories;

public interface RatedPoiRepository {

            /**
             * Get an {@link Observable} which will emit a List of {@link User}.
             */
            Observable<List<RatedPoi>> users();

            /**
             * Get an {@link Observable} which will emit a {@link User}.
             *
             * @param userId The user id used to retrieve user data.
             */
            Observable<RatedPoi> user(final int userId);

}
