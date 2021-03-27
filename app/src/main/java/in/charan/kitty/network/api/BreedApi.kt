package `in`.charan.kitty.network.api

import `in`.charan.kitty.model.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Charan on March 27, 2021
 */
interface BreedApi {

    @GET("breeds")
    suspend fun getListOfBreeds(@Query("page") page: Int,
                                @Query("limit") limit: Int): Response<List<Breed>>

}