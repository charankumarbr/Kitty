package `in`.charan.kitty.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weight(
    val imperial: String?,
    val metric: String?
) : Parcelable