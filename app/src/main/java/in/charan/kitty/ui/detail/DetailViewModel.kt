package `in`.charan.kitty.ui.detail

import `in`.charan.kitty.model.Breed
import `in`.charan.kitty.model.BreedEntryItem
import `in`.charan.kitty.model.Range
import `in`.charan.kitty.model.Result
import `in`.charan.kitty.util.endWithPeriod
import `in`.charan.kitty.util.formatWeight
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.zip.DataFormatException

/**
 * Created by Charan on March 29, 2021
 */
class DetailViewModel: ViewModel() {

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _breedData: MutableLiveData<Result<List<BreedEntryItem<out Any>>>> by lazy {
        MutableLiveData<Result<List<BreedEntryItem<out Any>>>>()
    }
    fun observeBreedData(): LiveData<Result<List<BreedEntryItem<out Any>>>> = _breedData
    fun prepareDataToDisplay(breed: Breed) {
        disposable.add(doPrepareData(breed)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _breedData.postValue(
                    if (it.isEmpty())
                        Result.Error(DataFormatException("Something went wrong!"),"Something went wrong!")
                    else
                        Result.Success(it)
                )
            }, {
                _breedData.postValue(Result.Error(it, "Seems like we lost the data."))
            })
        )
    }

    private fun doPrepareData(breed: Breed): Single<List<BreedEntryItem<out Any>>> {
        return Single.create {
            val breedEntryItemList = mutableListOf<BreedEntryItem<out Any>>()
            addBreedItem(breedEntryItemList, "Image", BreedEntryItem.VIEW_IMAGE, breed.image?.url)

            addBreedItem(breedEntryItemList, "Name", BreedEntryItem.VIEW_TEXT_HEADER, breed.name)
            addBreedItem(breedEntryItemList, "Origin:", BreedEntryItem.VIEW_TEXT_SUB_HEADER, breed.origin)
            addBreedItem(breedEntryItemList, "Description", BreedEntryItem.VIEW_TEXT_DESC, breed.description)


            addBreedItem(breedEntryItemList, "Observations", BreedEntryItem.VIEW_TEXT_HEADER, "Observations")
            //-- TODO: temperament can be shown as tags to gain user attention --//
            addBreedItem(breedEntryItemList, "Temperament:", BreedEntryItem.VIEW_TEXT_SUB_HEADER, breed.temperament?.endWithPeriod())
            addBreedItem(breedEntryItemList, "Affection", BreedEntryItem.VIEW_RANGE, getRange(current = breed.affection_level))
            addBreedItem(breedEntryItemList, "Child Friendly", BreedEntryItem.VIEW_RANGE, getRange(current = breed.child_friendly))
            addBreedItem(breedEntryItemList, "Dog Friendly", BreedEntryItem.VIEW_RANGE, getRange(current = breed.dog_friendly))
            addBreedItem(breedEntryItemList, "Grooming", BreedEntryItem.VIEW_RANGE, getRange(current = breed.grooming))
            addBreedItem(breedEntryItemList, "Intelligence", BreedEntryItem.VIEW_RANGE, getRange(current = breed.intelligence))
            addBreedItem(breedEntryItemList, "Shedding", BreedEntryItem.VIEW_RANGE, getRange(current = breed.shedding_level))
            addBreedItem(breedEntryItemList, "Social", BreedEntryItem.VIEW_RANGE, getRange(current = breed.social_needs))
            addBreedItem(breedEntryItemList, "Stranger Friendly", BreedEntryItem.VIEW_RANGE, getRange(current = breed.stranger_friendly))
            addBreedItem(breedEntryItemList, "Vocal", BreedEntryItem.VIEW_RANGE, getRange(current = breed.vocalisation))

            addBreedItem(breedEntryItemList, "Weight:", BreedEntryItem.VIEW_TEXT_SUB_HEADER, breed.weight?.metric?.formatWeight())

            addBreedItem(breedEntryItemList, "Read More", BreedEntryItem.VIEW_URL, breed.wikipedia_url)

            if (!it.isDisposed) {
                if (breedEntryItemList.isEmpty()) {
                    it.onError(IllegalArgumentException("Something wrong with the data."))
                } else {
                    it.onSuccess(breedEntryItemList)
                }
            }
        }
    }

    private fun getRange(min: Int = 0, max: Int = 5, current: Int?): Range? {
        return current?.let {
            Range(min, max, current = it)
        }
    }

    private fun <T> addBreedItem(breedEntryItemList: MutableList<BreedEntryItem<out Any>>,
                                 header: String, viewType: Int, data: T?) {
        data?.let {
            breedEntryItemList.add(BreedEntryItem(viewType, header, data))
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}