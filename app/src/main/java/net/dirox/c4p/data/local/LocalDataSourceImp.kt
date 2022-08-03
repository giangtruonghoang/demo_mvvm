package net.dirox.c4p.data.local

import android.content.Context
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.get

class LocalDataSourceImp: LocalDataSource, KoinComponent {

    private val context: Context = get()

    override fun getVersion(): Observable<String> {
        return Observable.just("LocalDataSource 1.0")
    }
}