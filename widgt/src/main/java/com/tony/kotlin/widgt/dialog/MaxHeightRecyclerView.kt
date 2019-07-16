package com.tony.kotlin.widgt.dialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.tony.kotlin.widgt.R

/**
 * description: max height
 *
 * @author tony
 */
class MaxHeightRecyclerView : RecyclerView {

    private var maxHeight: Int = -1

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxRVHeight)
            maxHeight = typedArray.getDimensionPixelSize(R.styleable.MaxRVHeight_maxHeight, -1)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        if (maxHeight > 0) {
            super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST))
            return
        }
        super.onMeasure(widthSpec, heightSpec)


    }

}