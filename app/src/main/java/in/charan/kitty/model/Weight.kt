package `in`.charan.kitty.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Weight(
    val imperial: String?,
    val metric: String?
) : Parcelable