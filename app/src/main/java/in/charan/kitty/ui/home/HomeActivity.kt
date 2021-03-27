package `in`.charan.kitty.ui.home

import `in`.charan.kitty.R
import `in`.charan.kitty.databinding.ActivityHomeBinding
import `in`.charan.kitty.model.Result
import `in`.charan.kitty.util.gone
import `in`.charan.kitty.util.visible
import `in`.charan.kitty.viewmodel.HomeViewModel
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
                    Log.d("home",  " ${it.data.breeds.size} items")
                }

                is Result.Error -> {
                    activityHomeBinding.ahPbLoading.gone()
                }
            }
        })
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
            R.id.menu_search -> {
                true
            }

            R.id.menu_about -> {
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}