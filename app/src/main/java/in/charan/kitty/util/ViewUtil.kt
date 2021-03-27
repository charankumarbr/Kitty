package `in`.charan.kitty.util

import android.view.View

/**
 * Created by Charan on March 27, 2021
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}