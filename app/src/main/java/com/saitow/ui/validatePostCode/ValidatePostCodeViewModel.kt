package com.saitow.ui.validatePostCode

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
class ValidatePostCodeViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var disposable: Disposable? = null

    private val _responsePostCode = MutableLiveData<Resource<ValidationResponse>>()

    val responsePostCode: LiveData<Resource<ValidationResponse>>
        get() = _responsePostCode

    fun validatePostCode(countryCode: String, postCode: String) {
        _responsePostCode.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            //clear previous request
            if (disposable != null){
                compositeDisposable.remove(disposable!!)
                disposable?.dispose()
                disposable = null
            }
            disposable = mainRepository.validatePostCode(countryCode, postCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                _responsePostCode.postValue(Resource.success(it))
            }, {
                _responsePostCode.postValue(Resource.error(it.toString(), null))
            })
            compositeDisposable.add(disposable!!)

        } else _responsePostCode.postValue(Resource.error("No internet connection", null))
    }
}