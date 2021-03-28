package `in`.charan.kitty.viewmodel

import `in`.charan.kitty.model.BreedList
import `in`.charan.kitty.model.Result
import `in`.charan.kitty.repo.BreedRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * Created by Charan on March 27, 2021
 */
class HomeViewModel: ViewModel() {

    private val TAG = "HomeViewModel"
    private val breedRepository = BreedRepository()

    private val _breedList: MutableLiveData<Result<BreedList>> by lazy {
        MutableLiveData<Result<BreedList>>()
    }
    val observeBreedList: LiveData<Result<BreedList>> by lazy {
        _breedList
    }
    fun getListOfBreeds(page: Int) {
        viewModelScope.launch {
            Log.d(TAG, "Thread a: ${Thread.currentThread().name}")

            val breedResult = try {
                Log.d(TAG, "Thread b: ${Thread.currentThread().name}")
                breedRepository.fetchBreeds(page)

            } catch (exception: Exception) {
                exception.printStackTrace()
                Result.Error(exception, "ex in VM")
            }

            if (isActive) {
                //-- update UI --//
                _breedList.value = breedResult
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}