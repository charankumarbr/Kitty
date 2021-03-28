package `in`.charan.kitty.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Charan on March 28, 2021
 */
abstract class AbstractViewHolder<T>(view: View): RecyclerView.ViewHolder(view) {

    abstract fun bind(data: T, itemViewType: Int)
}