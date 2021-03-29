package `in`.charan.kitty.adapter.main

import `in`.charan.kitty.R
import `in`.charan.kitty.model.Breed
import android.view.View
import android.widget.ImageView
import com.google.android.material.textview.MaterialTextView

/**
 * Created by Charan on March 28, 2021
 */
class BreedListItemViewHolder<T>(private val view: View,
                                 private val onBreedItemClick: (Int) -> Unit?,
                                 private val isItemClickReqd: Boolean)
    : AbstractViewHolder<Breed>(view) {

    private val tvName: MaterialTextView? = view.findViewById(R.id.lbliTvName)
    private val tvDesc: MaterialTextView? = view.findViewById(R.id.lbliTvDesc)
    private val ivImage: ImageView? = view.findViewById(R.id.lbliIvImage)

    init {
        if (isItemClickReqd) {
            view.setOnClickListener {
                onBreedItemClick(adapterPosition)
            }
        }
    }

    override fun bind(data: Breed, itemViewType: Int) {
        tvName?.text = data.name
        if (itemViewType == ITEM_VIEW_TYPE_CARD) {
            tvDesc?.text = data.description
        }
    }

    companion object {
        val ITEM_VIEW_TYPE_CARD = 1
        val ITEM_VIEW_TYPE_MINI = 2
    }

}