package `in`.charan.kitty.ui.detail

import `in`.charan.kitty.R
import `in`.charan.kitty.adapter.detail.BreedEntryItemAdapter
import `in`.charan.kitty.databinding.ActivityDetailBinding
import `in`.charan.kitty.model.Breed
import `in`.charan.kitty.model.BreedEntryItem
import `in`.charan.kitty.model.Result
import `in`.charan.kitty.util.gone
import `in`.charan.kitty.util.visible
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding

    private lateinit var breed: Breed

    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(viewModelStore, defaultViewModelProviderFactory)
            .get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val breed: Breed? = intent.getBundleExtra(BUNDLE)?.getParcelable(BREED_DATA)
        if (breed == null) {
            finish()
            return
        }
        this.breed = breed
        init(breed)
    }

    private fun init(breed: Breed) {
        supportActionBar?.title = breed.name
        subscribeObservers()
        detailViewModel.prepareDataToDisplay(breed)
    }

    private fun subscribeObservers() {
        detailViewModel.observeBreedData().observe(this, {
            when (it) {
                is Result.Loading -> {
                    activityDetailBinding.adRvDetails?.gone()
                    activityDetailBinding.adPbLoading?.visible()
                }
                is Result.Error -> {
                    activityDetailBinding.adPbLoading.gone()
                    Toast.makeText(
                        this, it.formattedMessage ?: it.exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is Result.Success -> {
                    val adapter = BreedEntryItemAdapter(it.data, onUrlClick)
                    activityDetailBinding.adRvDetails.adapter = adapter
                    activityDetailBinding.adPbLoading.gone()
                    activityDetailBinding.adRvDetails.visible()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private val onUrlClick: (BreedEntryItem<String>) -> Unit = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.data))
        val title = "${it.headerText} ${getString(R.string.about)} ${breed.name}"
        if (intent.resolveActivity(packageManager) != null) {
            val chooser = Intent.createChooser(intent, title)
            startActivity(chooser)

        } else {
            Toast.makeText(
                this, getString(R.string.no_browser_app_found),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val BREED_DATA = "BREED"
        const val BUNDLE = "BUNDLE"

        fun start(context: Context, bundle: Bundle?) {
            val intent = Intent(context, DetailActivity::class.java)
            bundle?.let {
                intent.putExtra(BUNDLE, bundle)
            }
            context.startActivity(intent)
        }

    }
}