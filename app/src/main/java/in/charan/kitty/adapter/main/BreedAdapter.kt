package `in`.charan.kitty.adapter.main

import `in`.charan.kitty.adapter.OnItemClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Created by Charan on March 28, 2021
 */
class BreedAdapter private constructor(private val builder: Builder)
    : RecyclerView.Adapter<AbstractViewHolder<Any>>() {

    private val contentMetaData = builder.contentMetaData
    private val loadingMetaData = builder.loadingMetaData
    private var isPaginationEnabled = builder.loadingMetaData != null
    private var isPaginationInProgress = false
    private val onListItemClickListener = builder.onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Any> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == contentMetaData.itemViewType) {
            val kClass = contentMetaData.viewHolderClass
            val isItemClickReqd = onListItemClickListener != null
            val view = inflater.inflate(contentMetaData.layoutId, parent, false)
            val constructor = kClass?.primaryConstructor
            constructor?.call(view, onContentItemClick, isItemClickReqd) as AbstractViewHolder<Any>

        } else {
            val kClass = loadingMetaData?.viewHolderClass
            val view = inflater.inflate(loadingMetaData!!.layoutId, parent, false)
            val constructor = kClass?.primaryConstructor
            constructor?.call(view) as AbstractViewHolder<Any>
        }
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Any>, position: Int) {
        if (contentMetaData.viewHolderClass.isInstance(holder)) {
            holder.bind(contentMetaData.content[position], holder.itemViewType)
            if (isPaginationEnabled && doLoadNextPage(position)) {
                isPaginationInProgress = true
                loadingMetaData?.onPageListener?.onNextPage(contentMetaData.content.size / contentMetaData.pageSize)
            }

        } else if (loadingMetaData?.viewHolderClass?.isInstance(holder) == true) {
            holder.bind(Unit, holder.itemViewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < contentMetaData.content.size) {
            contentMetaData.itemViewType
        } else {
            if (isPaginationEnabled) {
                loadingMetaData!!.itemViewType
            } else {
                throw RuntimeException("Loading is not enabled, but something has terribly gone wrong!")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isPaginationEnabled) {
            contentMetaData.content.size + 1
        } else {
            contentMetaData.content.size
        }
    }

    private val onContentItemClick: (Int) -> Unit = { position ->
        onListItemClickListener?.let {
            it.onItemClick(contentMetaData.content[position], position)
        }
    }

    fun addNextPageData(nextPageData: List<Any>) {
        isPaginationInProgress = false
        if (contentMetaData.addNextPageData(nextPageData)) {
            val isLoadingComplete = loadingMetaData?.let {
                it.loadingEndBlock(contentMetaData.content.size, contentMetaData.totalItems)
            } ?: true
            isPaginationEnabled = !isLoadingComplete

            notifyDataSetChanged()
        }
    }

    private fun doLoadNextPage(position: Int): Boolean {
        return isPaginationEnabled && !isPaginationInProgress &&
                contentMetaData.content.size - position == loadingMetaData?.loadingOffset ?: -1
    }

    class Builder {

        lateinit var contentMetaData: ContentMetaData
        var loadingMetaData: LoadingMetaData? = null

        var onItemClickListener: OnItemClickListener<Any>? = null

        fun setContentMetaData(contentMetaData: ContentMetaData): Builder {
            this.contentMetaData = contentMetaData
            return this
        }

        fun setLoadingMetaData(loadingMetaData: LoadingMetaData?): Builder {
            this.loadingMetaData = loadingMetaData
            return this
        }

        fun setContentItemClickListener(onListItemClickListener: OnItemClickListener<Any>): Builder {
            this.onItemClickListener = onListItemClickListener
            return this
        }

        fun build(): BreedAdapter {
            if (!this::contentMetaData.isInitialized) {
                throw IllegalArgumentException("Builder needs ContentMetaData to build a basic adapter.")
            }
            return BreedAdapter(this)
        }
    }

    abstract class ListItemMetaData constructor(val itemViewType: Int,
                                                @LayoutRes val layoutId: Int,
                                                val viewHolderClass: KClass<out AbstractViewHolder<*>>)

    class ContentMetaData(
        val content: MutableList<Any>,
        val pageSize: Int,
        val totalItems: Int,
        itemViewType: Int,
        @LayoutRes layoutId: Int,
        viewHolderClass: KClass<out AbstractViewHolder<*>>
    )
        : ListItemMetaData(itemViewType, layoutId, viewHolderClass) {

            fun addNextPageData(nextPageData: List<Any>): Boolean {
                return if (nextPageData.isNotEmpty()) {
                    content.addAll(nextPageData)
                    true
                } else {
                    false
                }
            }
        }

    class LoadingMetaData(val loadingEndBlock: (Int, Int) -> Boolean,
                          val loadingOffset: Int,
                          val onPageListener: OnListPageListener?,
                          itemViewType: Int,
                          @LayoutRes layoutId: Int,
                          viewHolderClass: KClass<out AbstractViewHolder<*>>)
        : ListItemMetaData(itemViewType, layoutId, viewHolderClass)
}