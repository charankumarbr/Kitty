package `in`.charan.kitty.model

/**
 * Created by Charan on March 29, 2021
 */
class BreedEntryItem<T> (val itemType: Int,
                         val headerText: String,
                         val data: T) {

    companion object {
        const val VIEW_IMAGE = 1

        const val VIEW_TEXT_HEADER = 11
        const val VIEW_TEXT_SUB_HEADER = 12
        const val VIEW_TEXT_DESC = 13

        const val VIEW_RANGE = 21

        const val VIEW_URL = 31
    }
}