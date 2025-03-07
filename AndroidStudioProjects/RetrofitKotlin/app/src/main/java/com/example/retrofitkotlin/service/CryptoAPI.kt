package com.example.retrofitkotlin.service

import android.telecom.Call
import com.example.retrofitkotlin.model.CryptoModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import retrofit2.http.GET


interface CryptoAPI {
    //GET,POST,UPDATE,DELETE
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    //https://api.nomics.com/v1/prices?key=2187154b76945f2373394aa34f7dc98a
    //2187154b76945f2373394aa34f7dc98a

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
  // fun getData(): retrofit2.Call<List<CryptoModel>>
    fun getData(): Observable<List<CryptoModel>>

}