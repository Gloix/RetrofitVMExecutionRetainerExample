package com.example

import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class TodoItem(val userId: Int, val id: Int, val title: String, val completed: Boolean)

interface SampleApi {

    @GET("todos/{id}")
    suspend fun getTodo(@Path("id") table: String) : TodoItem

    @GET("todos/{id}")
    fun getTodoCall(@Path("id") table: String) : Call<TodoItem>
}

object SnowClientFactory {

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun build() = retrofit().create(SampleApi::class.java)
}

fun main() {
    val api = SnowClientFactory.build()

    runBlocking {
        val response = api.getTodo("1")
        println(response)
    }

    // The following call does execute and let the VM exit cleanly instead
    //val x = api.getTodoCall("1").execute()
    //println(x)
}