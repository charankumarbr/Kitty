package `in`.charan.kitty.adapter.detail

import `in`.charan.kitty.R
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

/**
 * Created by Charan on March 29, 2021
 */
class DescViewHolder(private val view: View): AbstractDetailViewHolder<String>(view) {

    private val tvDesc: MaterialTextView? = view.findViewById(R.id.ledTvDesc)

    override fun bind(data: String) {
        tvDesc?.text = data
    }

}