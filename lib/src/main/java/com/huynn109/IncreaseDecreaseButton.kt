package com.huynn109

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlin.properties.Delegates


class IncreaseDecreaseButton @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private var iconSize: Float = 0.0f
    private var textSizeNumber: Float = 0.0f
    private var minNumber: Int = 0
    private var maxNumber: Int = 0
    private var initNumber: Int = 0
    private var activeColor by Delegates.notNull<Int>()
    private var inactiveColor by Delegates.notNull<Int>()
    private var increaseButton: MaterialButton? = null
    private var decreaseButton: MaterialButton? = null
    private var textViewNumber: MaterialTextView? = null
    private var currentNumber = 0
    private var onChangeListener: ((number: Int, isIncrease: Boolean) -> Unit)? = null

    init {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.increase_decrease_button_layout, this)
        initAttr()
        initViewId()
        bindViewRes()
        initEvent()
    }

    private fun initEvent() {
        increaseButton?.setOnClickListener {
            handleOnNumber(++currentNumber, isIncrease = true)
        }
        decreaseButton?.setOnClickListener {
            handleOnNumber(--currentNumber, isIncrease = false)
        }
    }

    fun onChangeListener(onChangeListener: ((number: Int, isIncrease: Boolean) -> Unit)?) {
        this.onChangeListener = onChangeListener
    }

    fun getCurrentNumber() = currentNumber

    fun bindViewRes() {
        currentNumber = initNumber
        handleCurrentNumber(initNumber)
        handleViewIncreaseButton()
        handleViewDecreaseButton()
    }

    private fun handleOnNumber(currentNumber: Int, isIncrease: Boolean) {
        handleViewIncreaseButton()
        handleViewDecreaseButton()
        handleCurrentNumber(currentNumber)
        onChangeListener?.invoke(currentNumber, isIncrease)
    }

    private fun handleCurrentNumber(number: Int) {
        textViewNumber?.text = "$number"
    }

    private fun handleViewDecreaseButton() {
        if (currentNumber == minNumber) {
            disableButton(decreaseButton)
        } else {
            enableButton(decreaseButton)
        }
    }

    private fun handleViewIncreaseButton() {
        if (currentNumber == maxNumber) {
            disableButton(increaseButton)
        } else {
            enableButton(increaseButton)
        }
    }

    fun setMaxNumber(maxNumber: Int) {
        if (maxNumber <= minNumber)
            this.maxNumber = minNumber
        else this.maxNumber = maxNumber
        handleButtonView()
    }

    private fun handleButtonView() {
        handleViewIncreaseButton()
        handleViewDecreaseButton()
    }

    fun disableDecrease() {
        disableButton(decreaseButton)
    }

    fun enableDecrease() {
        enableButton(decreaseButton)
    }

    fun disableIncrease() {
        disableButton(increaseButton)
    }

    fun enableIncrease() {
        enableButton(increaseButton)
    }

    fun setMinNumber(minNumber: Int) {
        if (maxNumber <= minNumber)
            this.minNumber = maxNumber
        else this.minNumber = minNumber
        handleButtonView()
    }

    fun initNumber(initNumber: Int) {
        this.initNumber = initNumber
        bindViewRes()
    }

    private fun initViewId() {
        increaseButton = findViewById(R.id.increaseButton)
        decreaseButton = findViewById(R.id.decreaseButton)
        textViewNumber = findViewById(R.id.tvNumber)
    }

    private fun initAttr() {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.IncreaseDecreaseButton, 0, 0)
        val res = resources
        val defaultActiveColor = res.getColor(R.color.increase_decrease_lib_active)
        val defaultInActiveColor = res.getColor(R.color.increase_decrease_lib_inactive)
        val defaultTextSizeNumber = 18f
        val defaultIconSize = 24f
        try {
            initNumber = typedArray.getInt(R.styleable.IncreaseDecreaseButton_initNumber, 0)
            minNumber = typedArray.getInt(R.styleable.IncreaseDecreaseButton_minNumber, 0)
            maxNumber = typedArray.getInt(R.styleable.IncreaseDecreaseButton_maxNumber, 0)
            activeColor = typedArray.getColor(
                R.styleable.IncreaseDecreaseButton_activeIconColor,
                defaultActiveColor
            )
            inactiveColor = typedArray.getColor(
                R.styleable.IncreaseDecreaseButton_inactiveIconColor,
                defaultInActiveColor
            )
            textSizeNumber = typedArray.getDimension(
                R.styleable.IncreaseDecreaseButton_textSizeNumber,
                defaultTextSizeNumber
            )
            iconSize = typedArray.getDimension(
                R.styleable.IncreaseDecreaseButton_iconSize,
                defaultIconSize
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
        typedArray.recycle()
    }

    private fun colorStateListOf(@ColorInt color: Int): ColorStateList {
        return ColorStateList.valueOf(color)
    }

    private fun disableButton(button: MaterialButton?) {
        button?.apply {
            iconTint = colorStateListOf(inactiveColor)
            isEnabled = false
        }
    }

    private fun enableButton(button: MaterialButton?) {
        button?.apply {
            iconTint = colorStateListOf(activeColor)
            isEnabled = true
        }
    }
}
