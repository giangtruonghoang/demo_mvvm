package net.dirox.c4p.data.service

import io.reactivex.Observable
import net.dirox.c4p.common.Api
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile
import retrofit2.http.*

interface ApiService {

    // Get List User GitHub
    @GET(Api.USER_GITHUB_LIST)
    fun getListUserGitHub(
        @Query("per_page") page: Int,
        @Query("since") since: Int
    ): Observable<List<UserGitHub>>

    // Get User GitHub
    @GET(Api.USER_GITHUB_ID)
    fun getUserGitHub(
        @Path("username") username: String
    ): Observable<UserGitHubProfile>
}