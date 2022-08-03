package net.dirox.c4p.data.repository

import io.reactivex.Observable
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile

interface Repository {
    fun getListUserGitHub(limit: Int, since : Int): Observable<List<UserGitHub>>
    fun getUserGitHub(username : String): Observable<UserGitHubProfile>
}