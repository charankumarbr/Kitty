package `in`.charan.kitty.adapter.detail

import `in`.charan.kitty.R
import `in`.charan.kitty.model.BreedEntryItem
import `in`.charan.kitty.model.Range
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Charan on March 29, 2021
 */
class BreedEntryItemAdapter (private val entries: List<BreedEntryItem<out Any>>,
                             private val onBreedEntryUrlClick: ((BreedEntryItem<String>) -> Unit)?)
    : RecyclerView.Adapter<AbstractDetailViewHolder<out Any>>() {

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun getItemViewType(position: Int): Int {
        return entries[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractDetailViewHolder<out Any> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BreedEntryItem.VIEW_IMAGE -> {
                val view = inflater.inflate(R.layout.layout_entry_image, parent, false)
                ImageViewHolder(view)
            }

            BreedEntryItem.VIEW_TEXT_HEADER -> {
                val view = inflater.inflate(R.layout.layout_entry_header, parent, false)
                HeaderTitleViewHolder(view)
            }
            BreedEntryItem.VIEW_TEXT_SUB_HEADER -> {
                val view = inflater.inflate(R.layout.layout_entry_sub_header, parent, false)
                SubHeaderViewHolder<BreedEntryItem<String>>(view)
            }
            BreedEntryItem.VIEW_TEXT_DESC -> {
                val view = inflater.inflate(R.layout.layout_entry_desc, parent, false)
                DescViewHolder(view)
            }
            BreedEntryItem.VIEW_RANGE -> {
                val view = inflater.inflate(R.layout.layout_entry_range, parent, false)
                RangeViewHolder<BreedEntryItem<Range>>(view)
            }

            BreedEntryItem.VIEW_URL -> {
                val view = inflater.inflate(R.layout.layout_entry_url, parent, false)
                WikiUrlViewHolder<BreedEntryItem<String>>(view, onUrlClick)
            }
            else -> {
                val view = inflater.inflate(R.layout.layout_space, parent, false)
                SpaceViewHolder<Unit>(view)
            }
        }
    }

    override fun onBindViewHolder(holder: AbstractDetailViewHolder<out Any>, position: Int) {
        val item = entries[position]
        when(holder) {
            is ImageViewHolder -> {
                holder.bind(item.data as String)
            }

            is HeaderTitleViewHolder -> {
                holder.bind(item.data as String)
            }

            is SubHeaderViewHolder<*> -> {
                holder.bind(item as BreedEntryItem<String>)
            }

            is DescViewHolder -> {
                holder.bind(item.data as String)
            }

            is RangeViewHolder<*> -> {
                holder.bind(item as BreedEntryItem<Range>)
            }

            is WikiUrlViewHolder<*> -> {
                holder.bind(item as BreedEntryItem<String>)
            }
        }
    }

    private val onUrlClick: (Int) -> Unit = {
        if (it > RecyclerView.NO_POSITION && onBreedEntryUrlClick != null) {
            onBreedEntryUrlClick?.invoke(entries[it] as BreedEntryItem<String>)
        }
    }
}