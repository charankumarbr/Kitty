package `in`.charan.kitty.repo

import `in`.charan.kitty.model.BreedList
import `in`.charan.kitty.model.Result
import `in`.charan.kitty.network.ApiConnector
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Charan on March 27, 2021
 */
class BreedRepository {

    private val TAG = "BreedRepository"

    suspend fun fetchBreeds(page: Int): Result<BreedList> {
        Log.d(TAG, "Thread a: ${Thread.currentThread().name}")
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "Thread b: ${Thread.currentThread().name}")
            val response = ApiConnector.getBreedApi().getListOfBreeds(page, 10)
            if (response.isSuccessful) {
                var totalItems = response.headers()["Pagination-Count"]?.toInt() ?: 10
                Result.Success(BreedList(response.body()!!, totalItems))
            } else {
                //-- TODO: address error --//
                val error = response.errorBody()?.string()
                Result.Error(RuntimeException(""), null)
            }
        }
    }
}