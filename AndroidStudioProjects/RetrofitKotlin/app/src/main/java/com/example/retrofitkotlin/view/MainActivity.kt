package com.example.retrofitkotlin.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitkotlin.R
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.adapter.RecyclerViewAdapter
import com.example.retrofitkotlin.databinding.ActivityMainBinding
import com.example.retrofitkotlin.databinding.RowLayoutBinding
import com.example.retrofitkotlin.model.CryptoModel
import com.example.retrofitkotlin.service.CryptoAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.Listener{
    private val BASE_URL="https://raw.githubusercontent.com"
    private var cryptoModels:ArrayList<CryptoModel>?=null
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    //Disposable
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(binding.root)
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
        //https://api.nomics.com/v1/prices?key=2187154b76945f2373394aa34f7dc98a
        //2187154b76945f2373394aa34f7dc98a
      compositeDisposable = CompositeDisposable()

        //Recycler view
        val layoutManager: RecyclerView.LayoutManager=LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadData()

    }
    private fun loadData()
    {
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)


       compositeDisposable?.add(retrofit.getData()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(this::handleResponse))

       /* compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe { Log.d("MainActivity", "API çağrısı başlatıldı") }
            .doOnTerminate { Log.d("MainActivity", "API çağrısı tamamlandı") }
            .subscribe(this::handleResponse, { e -> Log.e("MainActivity", "Hata: $e") }))
            */




/*

   val service =retrofit.create(CryptoAPI::class.java)
  val call=service.getData()

        call.enqueue(object:Callback<List<CryptoModel>>{
            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()

        }

          override  fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
              if (response.isSuccessful) {
                  response.body()?.let {
                      cryptoModels = ArrayList(it)
                      cryptoModels?.let {
                          recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
                          binding.recyclerView.adapter = recyclerViewAdapter
                      }
                      for (cryptoModel: CryptoModel in cryptoModels!!) {
                          println(cryptoModel.currency)
                          println(cryptoModel.price)

                      }


                      /*  for(cryptoModel:CryptoModel in cryptoModels!!){
                            println(cryptoModel.currency)
                            println(cryptoModel.price)
                        }*/
                  }
              }
          }})
          */
          }



      private fun handleResponse(cryptoList : List<CryptoModel>){
         cryptoModels = ArrayList(cryptoList)

         cryptoModels?.let {
             recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
             binding.recyclerView.adapter = recyclerViewAdapter
         }
     }
     override fun onItemClick(cryptoModel: CryptoModel) {
         Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG ).show()
     }

     override fun onDestroy() {
         super.onDestroy()

         compositeDisposable?.clear()
     }}



