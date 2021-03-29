package `in`.charan.kitty.adapter.detail

import `in`.charan.kitty.R
import `in`.charan.kitty.model.BreedEntryItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

/**
 * Created by Charan on March 29, 2021
 */
class SubHeaderViewHolder<T>(private val view: View): AbstractDetailViewHolder<BreedEntryItem<String>>(view) {

    private val tvHeaderLabel: MaterialTextView? = view.findViewById(R.id.leshTvHeaderLabel)
    private val tvHeader: MaterialTextView? = view.findViewById(R.id.leshTvHeader)

    override fun bind(data: BreedEntryItem<String>) {
        tvHeaderLabel?.text = data.headerText
        tvHeader?.text = data.data
    }

}