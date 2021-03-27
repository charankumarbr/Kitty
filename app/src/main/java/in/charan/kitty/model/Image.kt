package `in`.charan.kitty.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Image(
    val height: Int?,
    val id: String?,
    val url: String?,
    val width: Int?
) : Parcelable