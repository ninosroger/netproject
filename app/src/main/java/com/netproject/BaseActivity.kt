package com.netproject

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Ninos
 */
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity() {
    lateinit var context: Context
    lateinit var presenter: P
    private lateinit var dialog: AlertDialog
    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        beforeProvideLayoutId()
        setContentView(provideLayoutId())
        view = window.decorView.rootView
        presenter = createPresenter()
        initThings()
        initListeners()
        initData()
    }

    protected abstract fun provideLayoutId(): Int

    open fun initData() {}

    open fun beforeProvideLayoutId() {}

    abstract fun initListeners()

    protected abstract fun initThings()

    abstract fun createPresenter(): P

    fun showToast(text: CharSequence) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    fun showSoftInput(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    fun hideSoftMethod() {
        var foucusView = currentFocus
        if (foucusView != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as (InputMethodManager)
            imm.hideSoftInputFromWindow(foucusView.windowToken, 0)
        }
    }

    fun finishActivity() = onBackPressed()
}