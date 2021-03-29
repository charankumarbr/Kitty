package `in`.charan.kitty.adapter.detail

import `in`.charan.kitty.R
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Charan on March 29, 2021
 */
class ImageViewHolder(private val view: View): AbstractDetailViewHolder<String>(view) {

    private val ivImage: ImageView? = view.findViewById(R.id.leiIvImage)

    override fun bind(data: String) {

    }

}