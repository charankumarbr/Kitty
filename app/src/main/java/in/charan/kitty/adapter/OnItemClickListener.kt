package `in`.charan.kitty.adapter

/**
 * Created by Charan on March 28, 2021
 */
interface OnItemClickListener<T: Any> {

    fun onItemClick(dataAtPosition: T, clickedItemPosition: Int)
}