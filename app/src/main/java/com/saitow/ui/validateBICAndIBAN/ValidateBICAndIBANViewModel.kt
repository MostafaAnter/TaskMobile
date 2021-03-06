package com.saitow.ui.validateBICAndIBAN

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
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mostafa Anter on 9/5/20.
 */
class ValidateBICAndIBANViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var disposable: Disposable? = null

    private val _responseBIC = MutableLiveData<Resource<ValidationResponse>>()
    private val _responseIBAN = MutableLiveData<Resource<ValidationResponse>>()

    val responseBIC: LiveData<Resource<ValidationResponse>>
        get() = _responseBIC
    val responseIBAN: LiveData<Resource<ValidationResponse>>
        get() = _responseIBAN

    fun validateBIC(bicToValidate: String) {
        _responseBIC.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            //clear previous request
            if (disposable != null){
                compositeDisposable.remove(disposable!!)
                disposable?.dispose()
                disposable = null
            }
            disposable = mainRepository.validateBIC(bicToValidate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                _responseBIC.postValue(Resource.success(it))
            }, {
                _responseBIC.postValue(Resource.error(it.toString(), null))
            })
            compositeDisposable.add(disposable!!)

        } else _responseBIC.postValue(Resource.error("No internet connection", null))
    }

    fun validateIBAN(iban: String) {
        _responseIBAN.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            //clear previous request
            if (disposable != null){
                compositeDisposable.remove(disposable!!)
                disposable?.dispose()
                disposable = null
            }
            val compositeDisposable = CompositeDisposable()
            disposable = mainRepository.validateIBAN(iban)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                _responseIBAN.postValue(Resource.success(it))
            }, {
                _responseIBAN.postValue(Resource.error(it.toString(), null))
            })
            compositeDisposable.add(disposable!!)

        } else _responseIBAN.postValue(Resource.error("No internet connection", null))
    }
}