package com.example.cavfar.Interfaz;

import com.example.cavfar.FuenteModelos;
import com.example.cavfar.Users.loginResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("register")
    Call<loginResponse> createUser(
            @Field("name") String name,
            @Field("user") String user,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @POST("login")
    @Headers("Accept: application/json")
    Call<loginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    //Obtener modelos por marcas --> brand_id = 0 (todas)
    @GET("models/{brand_id}")
    @Headers("Accept: application/json")
    Call<ArrayList<FuenteModelos>> showModels(
            @Header("Authorization") String token,
            @Path("brand_id") int brand_id
    );

    @GET("models")
    @Headers("Accept: application/json")
    Call<List<FuenteModelos>> showAllModels(
            @Header("Authorization") String token
    );

    //Añadir a favoritos
    @FormUrlEncoded
    @POST("mark-as-favorite")
    @Headers("Accept: application/json")
    Call<ResponseBody> addFav(
            @Header("Authorization") String token,
            @Field("model_id") String model_id
    );

    @GET("favorites")
    @Headers("Accept: application/json")
    Call<List<FuenteModelos>> getFavorites(
            @Header("Authorization") String token
    );

    //Editar usuario
    @FormUrlEncoded
    @POST("edit-user")
    @Headers("Accept: application/json")
    Call<loginResponse> editUser(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );
}