package com.roix.mapchat.ui.common.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.roix.mapchat.R
import com.roix.mapchat.application.CommonApplication
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.reflect.ParameterizedType

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
abstract class BaseLifecycleActivity<ViewModel : BaseLifecycleViewModel> : AppCompatActivity() {

    abstract fun getLayoutId(): Int

    protected lateinit var viewModel: ViewModel

    //TODO: using global progressDialog design pattern is depricated
    private lateinit var progressDialog: ProgressDialog

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        viewModel = bindViewModel(getViewModelJavaClass())
        setupUi()
    }

    protected fun <T : BaseLifecycleViewModel> bindViewModel(clazz: Class<T>): T {
        val viewModel = ViewModelProviders.of(this).get(clazz)
        viewModel.loadingLiveData.sub { b -> handleProgress(b) }
        viewModel.showMessageDialogLiveData.sub { s -> this.showMessageDialog(s) }
        viewModel.errorLiveData.sub { t -> handleError(t) }
        viewModel.onBindView(application as CommonApplication)
        return viewModel
    }

    protected open fun setupUi() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.text_dialog_progress))
        progressDialog.setCancelable(false)
    }

    @CallSuper
    protected open fun handleProgress(isProgress: Boolean) {
        if (isProgress) {
            progressDialog.show()
        } else {
            progressDialog.hide()
        }
    }

    @CallSuper
    protected open fun showMessageDialog(message: String) {
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    protected open fun handleError(throwable: Throwable) {
        //Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }


    protected fun <T> LiveData<T>.sub(func: (T) -> Unit) {
        observe(this@BaseLifecycleActivity, Observer { T -> if (T != null) func.invoke(T) })
    }

    protected fun <T> Observable<T>.sub(func: (T) -> Unit) {
        viewModel.toLiveDataFun(this).sub(func)
    }

    protected fun <T> Single<T>.sub(func: (T) -> Unit) {
        viewModel.toLiveDataFun(this.toObservable()).sub(func)
    }

    protected fun Completable.sub(func: (Boolean) -> Unit) {
        viewModel.toLiveDataFun(this).sub(func)
    }

    protected fun <T> Flowable<T>.sub(func: (T) -> Unit) {
        viewModel.toLiveDataFun(this.toObservable()).sub(func)
    }

    protected fun <T> LiveData<T>.subNoHistory(func: (T?) -> Unit) {
        var isUsed = false
        observe(this@BaseLifecycleActivity, Observer { T ->
            if (!isUsed) {
                func.invoke(T)
                isUsed = true
            }
        })
    }


    protected fun <T> Observable<T>.subNoHistory(func: (T?) -> Unit) {
        viewModel.toLiveDataFun(this).subNoHistory(func)
    }

    protected fun <T> Single<T>.subNoHistory(func: (T?) -> Unit) {
        viewModel.toLiveDataFun(this.toObservable()).subNoHistory(func)
    }

    protected fun Completable.subNoHistory(func: (Boolean?) -> Unit) {
        viewModel.toLiveDataFun(this).subNoHistory(func)
    }

    protected fun <T> Flowable<T>.subNoHistory(func: (T?) -> Unit) {
        viewModel.toLiveDataFun(this.toObservable()).subNoHistory(func)
    }

    fun <T> MutableLiveData<T>.setValueNoHistory(t: T) {
        value = (t)
        value = (null)
    }


    private fun getViewModelJavaClass(): Class<ViewModel> {
        return (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<ViewModel>
    }

}
