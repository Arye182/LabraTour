package com.example.labratour.presentation.model.repositories

import android.util.Log
import com.example.labratour.presentation.model.api.CurrencyApi
import com.example.labratour.presentation.model.data.currency.CurrencyResponse
import com.example.labratour.presentation.utils.Resource
import java.lang.Exception

class CurrencyRepositoryImpl(private val api: CurrencyApi) : CurrencyRepository {
    override suspend fun getRates(base: String, key: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base, key)
            Log.i("Places", response.code().toString())
            Log.i("Places", response.isSuccessful.toString())
            Log.i("Places", response.toString())
            val result = response.body()
            if (response.isSuccessful && result != null) {
                if (response.code().toString() == "403") {
                    Log.i("Places", "403")
                    Resource.Error(response.message())
                } else {
                    Log.i("Places", "Currency Repository " + response.message())
                    Resource.Success(result)
                }
            } else {
                Log.i("Places", "Currency Repository Request Not Success " + response.message().toString())
                Resource.Error("Access Restricted - Your current Subscription Plan does not support HTTPS Encryption.")
            }
        } catch (e: Exception) {
            Log.i("Places", "Currency Repository " + e.message)
            Resource.Error(e.message ?: "An Error Currency Api Occured")
        }
    }
}
