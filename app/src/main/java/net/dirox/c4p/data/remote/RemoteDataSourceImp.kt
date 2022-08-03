package net.dirox.c4p.data.remote

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile
import net.dirox.c4p.data.service.ApiService
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.HttpException

class RemoteDataSourceImp: RemoteDataSource, KoinComponent {

    private val context: Context = get()
    private val mService: ApiService = get()
    private val gson: Gson = get()
    override fun getListUserGitHub(per_page: Int , since : Int): Observable<List<UserGitHub>> {
        return mService.getListUserGitHub(per_page, since)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .handleErrors()
    }

    override fun getUserGitHub(username: String): Observable<UserGitHubProfile> {
        return mService.getUserGitHub(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .handleErrors()
    }
}

fun <T> Observable<T>.handleErrors(): Observable<T> = onErrorResumeNext { t: Throwable -> classifiedErrors(t) }

fun <T> classifiedErrors(t: Throwable): ObservableSource<T> {
    return if (t is HttpException) {
        when {
            t.code() == 504 -> Observable.never()
            t.code() >= 500 -> Observable.error(Throwable("Internal server error"))
            else -> Observable.error(t)
        }
    } else {
        Observable.never()
    }
}