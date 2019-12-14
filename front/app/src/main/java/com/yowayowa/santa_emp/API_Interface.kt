package com.yowayowa.santa_emp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class API_Interface{

    interface API_getall {
        @GET("get_json")
        fun API(): Call<List<childLocationClass>>
    }

    //example
    interface API_GetRazpi {
        @GET("get_data")
        fun API(): Call<childLocationClass?/*型自由*/>
    }

    interface API_valus{
        @GET("valus")
        fun API(): Call<waitorGoClass>
    }

}