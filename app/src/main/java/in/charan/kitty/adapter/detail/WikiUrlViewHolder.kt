package `in`.charan.kitty.adapter.detail

import `in`.charan.kitty.R
import `in`.charan.kitty.model.BreedEntryItem
import android.util.Log
import android.view.View
import android.widget.Button

/**
 * Created by Charan on March 29, 2021
 */
class WikiUrlViewHolder<T>(private val view: View,
                           private val onBtnClicked: ((Int) -> Unit)?
)
    : AbstractDetailViewHolder<BreedEntryItem<String>>(view) {

    private val btnReadMore: Button? = view.findViewById(R.id.leuBtnReadMore)
    private val TAG = "WikiUrlViewHolder"

    init {
        btnReadMore?.setOnClickListener {
            Log.d(TAG, "read more clicked")
            onBtnClicked?.let {
                it(adapterPosition)
            }
        }
    }

    override fun bind(data: BreedEntryItem<String>) {
        btnReadMore?.text = data.headerText
    }

}