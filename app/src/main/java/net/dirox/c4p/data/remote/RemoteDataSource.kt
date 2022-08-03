package net.dirox.c4p.data.remote

import io.reactivex.Observable
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile

interface RemoteDataSource {
    fun getListUserGitHub(page: Int , since : Int): Observable<List<UserGitHub>>
    fun getUserGitHub(username : String): Observable<UserGitHubProfile>
}