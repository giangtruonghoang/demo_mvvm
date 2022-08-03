package net.dirox.c4p.data.local

import io.reactivex.Observable

interface LocalDataSource {

    fun getVersion(): Observable<String>
}