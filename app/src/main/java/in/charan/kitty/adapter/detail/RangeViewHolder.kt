package `in`.charan.kitty.adapter.detail

import `in`.charan.kitty.R
import `in`.charan.kitty.model.BreedEntryItem
import `in`.charan.kitty.model.Range
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView

/**
 * Created by Charan on March 29, 2021
 */
class RangeViewHolder<T>(private val view: View): AbstractDetailViewHolder<BreedEntryItem<Range>>(view) {

    private val tvHeader: MaterialTextView? = view.findViewById(R.id.lerTvHeader)

    private val tvMin: MaterialTextView? = view.findViewById(R.id.lerTvRangeMin)
    private val tvMax: MaterialTextView? = view.findViewById(R.id.lerTvRangeMax)
    private val range: Slider? = view.findViewById(R.id.lerRange)

    override fun bind(data: BreedEntryItem<Range>) {
        tvHeader?.text = data.headerText

        tvMin?.text = "${data.data.min}"
        tvMax?.text = "${data.data.current}/${data.data.max}"
        tvMin?.text = "${data.data.min}"

        range?.let {
            with (it) {
                valueFrom = data.data.min.toFloat()
                valueTo = data.data.max.toFloat()
                value = data.data.current.toFloat()
                stepSize = 1F
                isEnabled = false
            }
        }
    }

}