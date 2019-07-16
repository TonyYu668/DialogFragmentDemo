package com.tony.kotlin.widgt.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.tony.kotlin.widgt.R
import kotlinx.android.synthetic.main.layout_dialog_horizontal.*
import kotlinx.android.synthetic.main.layout_dialog_input.*
import kotlinx.android.synthetic.main.layout_dialog_list.*
import kotlinx.android.synthetic.main.layout_dialog_prompt.*
import kotlinx.android.synthetic.main.layout_dialog_vertical.*

/**
 * @author tony
 */
class YTDialog @JvmOverloads constructor() : AppCompatDialogFragment(), YTDialogRecyclerAdapter.OnItemClickListener {

    class Builder() : Parcelable {
        internal var ytDialogType: YTDialogType? = null
        internal var ytDialogTitle: String? = null
        internal var ytDialogContent: String? = null
        internal var ytDialogPositive: String? = null
        internal var ytDialogNegative: String? = null
        internal var ytDialogHitText: String? = null
        internal var clickCallBack: ClickCallBack? = null
        internal var mutableList: MutableList<String>? = null
        internal var listClickCallBack: ListClickCallBack? = null

        constructor(parcel: Parcel) : this() {
            ytDialogTitle = parcel.readString()
            ytDialogContent = parcel.readString()
            ytDialogPositive = parcel.readString()
            ytDialogNegative = parcel.readString()
            ytDialogHitText = parcel.readString()
        }


        fun setType(type: YTDialogType): Builder {
            ytDialogType = type
            return this
        }

        fun setTitle(title: String): Builder {
            ytDialogTitle = title
            return this
        }

        fun setContent(content: String): Builder {
            ytDialogContent = content
            return this
        }

        fun setPositiveButtonText(text: String): Builder {
            ytDialogPositive = text
            return this
        }

        fun setNegativeButtonText(text: String): Builder {
            ytDialogNegative = text
            return this
        }

        fun setClickCallBack(callBack: ClickCallBack): Builder {
            clickCallBack = callBack
            return this
        }

        fun setListClickCallBack(callBack: ListClickCallBack): Builder {
            listClickCallBack = callBack
            return this
        }

        fun setDataList(dataList: MutableList<String>): Builder {
            mutableList = dataList
            return this
        }

        fun setHintText(text: String): Builder {
            ytDialogHitText = text
            return this
        }

        fun show(context: AppCompatActivity, tag: String): YTDialog {
            if (ytDialogType == null)
                throw RuntimeException("setType don't invoke!")

            var daoDialog = YTDialog()
            daoDialog.setBuilder(this)
            daoDialog.show(context.supportFragmentManager.beginTransaction(), tag)
            return daoDialog
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(ytDialogTitle)
            parcel.writeString(ytDialogContent)
            parcel.writeString(ytDialogPositive)
            parcel.writeString(ytDialogNegative)
            parcel.writeString(ytDialogHitText)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Builder> {
            override fun createFromParcel(parcel: Parcel): Builder {
                return Builder(parcel)
            }

            override fun newArray(size: Int): Array<Builder?> {
                return arrayOfNulls(size)
            }
        }


    }


    public interface ClickCallBack {
        fun onPositiveCallBack(string: String?)
        fun onNegativeCallBack()
    }

    public interface ListClickCallBack {
        fun onItemCallBack(string: String)
    }


    private lateinit var builder: Builder


    private fun setBuilder(builder: Builder) {
        this.builder = builder
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            builder = savedInstanceState.getParcelable("builder") as Builder
//        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View?
        when (builder.ytDialogType) {
            YTDialogType.PROMPT -> {
                view = inflater.inflate(R.layout.layout_dialog_prompt, container, false)
                create4PromptView(view)
            }
            YTDialogType.INPUT -> {
                view = inflater.inflate(R.layout.layout_dialog_input, container, false)
                create4InputView(view)
            }
            YTDialogType.LIST -> {
                view = inflater.inflate(R.layout.layout_dialog_list, container, false)
                create4ListView(view)
            }
            YTDialogType.HORIZONTAL_BUTTON -> {
                view = inflater.inflate(R.layout.layout_dialog_horizontal, container, false)
                create4HorizontalView(view)
            }
            YTDialogType.VERTICAL_BUTTON -> {
                view = inflater.inflate(R.layout.layout_dialog_vertical, container, false)
                create4VerticalView(view)
            }
            else -> view = null
        }

        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setOnKeyListener(DialogInterface.OnKeyListener { _, p1, _ ->
            if (p1 == KeyEvent.KEYCODE_BACK) {
                return@OnKeyListener true
            }
            false
        })

        return view
    }

    private fun create4ListView(view: View) {
        layout_dialog_list_title.text = builder.ytDialogTitle ?: layout_dialog_list_title.text
        if (builder.mutableList.isNullOrEmpty())
            throw java.lang.RuntimeException("no use the setDataList()")

        layout_dialog_list.layoutManager = LinearLayoutManager(context)
        val daoDialogRecyclerAdapter = YTDialogRecyclerAdapter(context!!, builder.mutableList!!)
        daoDialogRecyclerAdapter.setOnItemClickListener(this@YTDialog)
        layout_dialog_list.adapter = daoDialogRecyclerAdapter
        layout_dialog_list.itemAnimator = DefaultItemAnimator()
    }

    private fun create4VerticalView(view: View) {
        layout_dialog_vertical_title.text = builder.ytDialogTitle ?: layout_dialog_vertical_title.text
        layout_dialog_vertical_content.text = builder.ytDialogContent ?: layout_dialog_vertical_content.text

        layout_dialog_vertical_create.text = builder.ytDialogPositive ?: layout_dialog_vertical_create.text
        layout_dialog_vertical_cancel.text = builder.ytDialogNegative ?: layout_dialog_vertical_cancel.text

        layout_dialog_vertical_create.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onPositiveCallBack(null)
            dismiss()
        }
        layout_dialog_vertical_cancel.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onNegativeCallBack()
            dismiss()
        }
    }

    private fun create4HorizontalView(view: View) {
        layout_dialog_horizontal_title.text = builder.ytDialogTitle
            ?: layout_dialog_horizontal_title.text
        layout_dialog_horizontal_content.text = builder.ytDialogContent
            ?: layout_dialog_horizontal_content.text

        layout_dialog_horizontal_create.text = builder.ytDialogPositive ?: layout_dialog_horizontal_create.text
        layout_dialog_horizontal_cancel.text = builder.ytDialogNegative ?: layout_dialog_horizontal_cancel.text

        layout_dialog_horizontal_create.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onPositiveCallBack(null)
            dismiss()
        }
        layout_dialog_horizontal_cancel.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onNegativeCallBack()
            dismiss()
        }
    }

    private fun create4InputView(view: View) {
        layout_dialog_input_title.text = builder.ytDialogTitle ?: layout_dialog_input_title.text
        layout_dialog_input_cancel.text = builder.ytDialogNegative ?: layout_dialog_input_cancel.text

        layout_dialog_input_create.text = builder.ytDialogPositive ?: layout_dialog_input_create.text

        layout_dialog_input_et.hint = builder.ytDialogHitText ?: layout_dialog_input_et.hint

        layout_dialog_input_create.text = builder.ytDialogPositive ?: layout_dialog_input_create.text
        layout_dialog_input_cancel.text = builder.ytDialogNegative ?: layout_dialog_input_cancel.text

        layout_dialog_input_create.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onPositiveCallBack(layout_dialog_input_et.text.toString())
            dismiss()
        }
        layout_dialog_input_cancel.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onNegativeCallBack()
            dismiss()
        }
    }

    private fun create4PromptView(view: View) {
        layout_dialog_prompt_content.text = builder.ytDialogContent ?: layout_dialog_prompt_content.text
        layout_dialog_prompt_create.text = builder.ytDialogPositive ?: layout_dialog_prompt_create.text
        layout_dialog_prompt_cancel.text = builder.ytDialogNegative ?: layout_dialog_prompt_cancel.text

        layout_dialog_prompt_create.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onPositiveCallBack(null)
            dismiss()
        }

        layout_dialog_prompt_cancel.setOnClickListener {
            if (builder.clickCallBack != null)
                builder.clickCallBack!!.onNegativeCallBack()
            dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("builder", builder)
    }


    override fun onItemClick(string: String, position: Int) {
        if (builder.listClickCallBack != null)
            builder.listClickCallBack!!.onItemCallBack(string)
        dismiss()
    }

    fun isShowing(): Boolean {
        return dialog!!.isShowing
    }
}