package `in`.charan.kitty.model

/**
 * Created by Charan on March 29, 2021
 */
data class Range(val min: Int = 0, val max: Int = 5, val current: Int) {

    init {
        if (current > max) {
            throw IllegalArgumentException("Range: Current value $current cannot be greater than Max: $max")
        }

        if (current < min) {
            throw IllegalArgumentException("Range: Current value $current cannot be lesser than Min: $min")
        }
    }
}
