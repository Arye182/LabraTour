package com.data.net;

public class RestApiService {
}
//@Singleton
//class BandsApiService
//        @Inject constructor(retrofit: Retrofit) : BandsApi {
//private val musicApi by lazy { retrofit.create(BandsApi::class.java) }
//        override fun bands(): Single<List<BandEntity>> =
//        musicApi
//        .bands()
//        .onErrorResumeNext { error: Throwable -> Single.error(translate(error)) }
//        override fun bandDetails(bandId: Int): Single<BandDetailsEntity> =
//        musicApi
//        .bandDetails(bandId)
//        .onErrorResumeNext { error: Throwable -> Single.error(translate(error)) }
//private fun translate(throwable: Throwable): Throwable =
//        when(throwable) {
//        is JsonMappingException -> ServerErrorException(throwable)
//        is HttpException -> ServerErrorException(throwable)
//        else -> throwable
//        }
//        }