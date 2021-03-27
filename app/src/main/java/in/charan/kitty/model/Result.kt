package `in`.charan.kitty.model

/**
 * Created by Charan on March 27, 2021
 */
sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val formattedMessage: String?) : Result<Nothing>()
}
