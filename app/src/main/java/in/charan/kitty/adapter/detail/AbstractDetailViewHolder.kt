package `in`.charan.kitty.adapter.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Charan on March 29, 2021
 */
abstract class AbstractDetailViewHolder<T>(view: View): RecyclerView.ViewHolder(view) {

    abstract fun bind(data: T)
}