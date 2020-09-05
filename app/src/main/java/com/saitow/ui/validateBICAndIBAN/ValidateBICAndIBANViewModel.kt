package com.saitow.ui.validateBICAndIBAN

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saitow.data.model.ValidationResponse
import com.saitow.data.repository.MainRepository
import com.saitow.utils.NetworkHelper
import com.task.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mostafa Anter on 9/5/20.
 */
class ValidateBICAndIBANViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _responseBIC = MutableLiveData<Resource<ValidationResponse>>()
    private val _responseIBAN = MutableLiveData<Resource<ValidationResponse>>()

    val responseBIC: LiveData<Resource<ValidationResponse>>
        get() = _responseBIC
    val responseIBAN: LiveData<Resource<ValidationResponse>>
        get() = _responseIBAN

    fun validateBIC(bicToValidate: String) {
        _responseBIC.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            val compositeDisposable = CompositeDisposable()
            val observer = mainRepository.validateBIC(bicToValidate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
            val disposable = observer.subscribe({
                _responseBIC.postValue(Resource.success(it))
                Log.d("response done", it.data.bic)
            }, {
                _responseBIC.postValue(Resource.error(it.toString(), null))
                Log.d("response failed", it.toString())
            })
            compositeDisposable.add(disposable)

        } else _responseBIC.postValue(Resource.error("No internet connection", null))
    }

    fun validateIBAN(iban: String) {
        _responseIBAN.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            val compositeDisposable = CompositeDisposable()
            val observer = mainRepository.validateIBAN(iban)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
            val disposable = observer.subscribe({
                _responseIBAN.postValue(Resource.success(it))
                Log.d("response done", it.data.bic)
            }, {
                _responseIBAN.postValue(Resource.error(it.toString(), null))
                Log.d("response failed", it.toString())
            })
            compositeDisposable.add(disposable)

        } else _responseIBAN.postValue(Resource.error("No internet connection", null))
    }
}