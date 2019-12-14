package com.yowayowa.santa_emp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class API_Interface{

    interface API_getall {
        @GET(".")
        fun API(): Call<List<childLocationClass>>
    }


    interface API_GetRazpi {
        @GET("get_data")
        fun API(): Call<childLocationClass?/*型自由*/>
    }
}