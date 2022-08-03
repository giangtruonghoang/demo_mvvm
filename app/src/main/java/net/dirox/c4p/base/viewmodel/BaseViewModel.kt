package net.dirox.c4p.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {
    open val disposeBag = CompositeDisposable()
    open var loading: MutableLiveData<Boolean> = MutableLiveData()
    //error.value = String
    open var error: MutableLiveData<Error> = MutableLiveData()
    //errorResId.value = ID Int of string.xml
    open var errorResId: MutableLiveData<Int> = MutableLiveData()
    //errorResIdString.value = ID Int of string.xml parce to String
    open var errorResIdString: MutableLiveData<String> = MutableLiveData()

    fun <T> observe(
        observable: Observable<T>,
        onNext: (result: T) -> Unit,
        onError: (t: Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        disposeBag.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onComplete)
        )
    }

    fun <T> observeOnce(
        observable: Observable<T>,
        onNext: (result: T) -> Unit = {},
        onError: (t: Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        disposeBag.add(
            observable
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onComplete)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}