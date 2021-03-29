package `in`.charan.kitty.ui.home

import `in`.charan.kitty.R
import `in`.charan.kitty.adapter.*
import `in`.charan.kitty.adapter.main.BreedAdapter
import `in`.charan.kitty.adapter.main.BreedListItemViewHolder
import `in`.charan.kitty.adapter.main.LoadingViewHolder
import `in`.charan.kitty.adapter.main.OnListPageListener
import `in`.charan.kitty.databinding.ActivityHomeBinding
import `in`.charan.kitty.model.Breed
import `in`.charan.kitty.model.Result
import `in`.charan.kitty.ui.detail.DetailActivity
import `in`.charan.kitty.util.gone
import `in`.charan.kitty.util.visible
import `in`.charan.kitty.viewmodel.HomeViewModel
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class HomeActivity : AppCompatActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(viewModelStore, defaultViewModelProviderFactory)
            .get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        init()
    }

    private fun init() {
        subscribeObservers()
        getBreeds(0)
    }

    private fun subscribeObservers() {
        homeViewModel.observeBreedList.observe(this, {
            when (it) {
                is Result.Loading -> {
                    activityHomeBinding.ahPbLoading.visible()
                }

                is Result.Success -> {
                    activityHomeBinding.ahPbLoading.gone()
                    Log.d("home", " ${it.data.breeds.size} items")
                    if (activityHomeBinding.ahRvItems.adapter == null) {
                        val mutableDataList: MutableList<Any> = it.data.breeds.toMutableList()
                        val adapter: BreedAdapter = BreedAdapter.Builder()
                            .setContentMetaData(
                                BreedAdapter.ContentMetaData(mutableDataList,
                                    10, it.data.totalBreeds, BreedListItemViewHolder.ITEM_VIEW_TYPE_CARD,
                                    R.layout.layout_breed_card_list_item, BreedListItemViewHolder::class
                                )
                            )
                            .setContentItemClickListener(onListItemClick)
                            .setLoadingMetaData(
                                BreedAdapter.LoadingMetaData(
                                    loadingEndCondition, 3,
                                    onListPageListener, 99,
                                    R.layout.layout_loading_list_item, LoadingViewHolder::class
                                )
                            )
                            .build()
                        activityHomeBinding.ahRvItems.adapter = adapter
                        activityHomeBinding.ahRvItems.visible()

                    } else {
                        val adapter = activityHomeBinding.ahRvItems.adapter
                        if (adapter is BreedAdapter) {
                            adapter.addNextPageData(it.data.breeds)
                        }
                    }
                }

                is Result.Error -> {
                    activityHomeBinding.ahPbLoading.gone()
                }
            }
        })
    }

    private val onListItemClick = object: OnItemClickListener<Any>{
        override fun onItemClick(dataAtPosition: Any,
                                 clickedItemPosition: Int) {
            when (dataAtPosition) {
                is Breed -> {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailActivity.BREED_DATA, dataAtPosition)
                    DetailActivity.start(this@HomeActivity, bundle)
                }
            }
        }
    }

    private val onListPageListener = object: OnListPageListener {
        override fun onNextPage(nextPageIndex: Int) {
            homeViewModel.getListOfBreeds(nextPageIndex)
        }
    }

    private val loadingEndCondition: (Int, Int) -> Boolean = { size: Int, totalItems: Int ->
        size == totalItems
    }

    private fun getBreeds(page: Int) {
        homeViewModel.getListOfBreeds(page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about -> {
                displayAboutDialog()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun displayAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(R.string.about_app_message)
            .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setCancelable(true)
            .create()
            .show()
    }
}