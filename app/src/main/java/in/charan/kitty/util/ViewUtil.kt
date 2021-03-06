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

fun String.formatWeight(): String {
    if (this.contains(" - ")) {
        var changed = this.replace(" - ", " Kgs - ")
        changed = changed.plus(" Kgs")
        changed = changed.endWithPeriod()
        return changed
    }
    return this
}

fun String.endWithPeriod(): String {
    val period = "."
    if (!this.endsWith(period)) {
        return this.plus(period)
    }
    return this
}