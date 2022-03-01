package com.erdemtsynduev.rotate360degree.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.erdemtsynduev.rotate360degree.R

abstract class BaseFragment<VB : ViewBinding> : Fragment(), ConfigureToolbar {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateLayout(inflater)
        return binding.root
    }

    protected abstract fun getClassName(): String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    @CallSuper
    protected open fun initViews() = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initToolbar() {
        getToolbar()?.let { toolbar ->
            setUpToolbar(toolbar)
            (activity as? AppCompatActivity)?.let {
                it.setSupportActionBar(toolbar)
                it.supportActionBar?.setDisplayShowTitleEnabled(false)
                it.supportActionBar?.let(::setUpActionBar)
            }
        }
    }

    /**
     * if we need any specific background color or back arrow icon in toolbar we can change it here
     */
    override fun setUpToolbar(toolbar: Toolbar) = Unit

    /**
     * override this method if fragment has different toolbar then custom_toolbar.xml
     */
    override fun getToolbar(): Toolbar? {
        return view?.findViewById(R.id.toolbar)
    }

    /**
     *if fragment contains actionbar and required to update actionbar/toolbar then update here
     */
    override fun setUpActionBar(actionBar: ActionBar) = Unit

    override fun setToolbarTitle(title: String) {
        view?.findViewById<TextView>(R.id.tvTitle)?.let {
            it.text = title
        }
    }
}