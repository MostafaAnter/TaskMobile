package com.saitow.ui.searchBankData

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saitow.data.model.SearchResponse
import com.saitow.data.repository.MainRepository
import com.saitow.utils.NetworkHelper
import com.task.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mostafa Anter on 9/5/20.
 */
class SearchBankDataViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper) : ViewModel() {

    private val _response = MutableLiveData<Resource<SearchResponse>>()

    val response: LiveData<Resource<SearchResponse>>
        get() = _response

    fun doSearch(routingCode: String) {
        _response.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            val compositeDisposable = CompositeDisposable()
            val observer = mainRepository.searchForBankData(routingCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
            val disposable = observer.subscribe({
                _response.postValue(Resource.success(it))
                Log.d("response done", it.data.bic)
            }, {
                _response.postValue(Resource.error(it.toString(), null))
                Log.d("response failed", it.toString())
            })
            compositeDisposable.add(disposable)

        } else _response.postValue(Resource.error("No internet connection", null))
    }
}