package com.example.Service

import com.example.Service.Data.Project
import com.example.Service.Data.Ticket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {

    @GET("tickets")
    fun getTickets(@Header("ApiKey") apiKey: String): Call<List<Ticket>>

    @GET("projects")
    fun getProjects(): List<Project>
}
