package net.dirox.c4p.data.repository

import io.reactivex.Observable
import net.dirox.c4p.data.remote.RemoteDataSource
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile
import org.koin.core.KoinComponent
import org.koin.core.get

class RepositoryImp: Repository, KoinComponent {

    private val remoteDataSource: RemoteDataSource = get()

    override fun getListUserGitHub(limit: Int, since : Int): Observable<List<UserGitHub>> {
        return remoteDataSource.getListUserGitHub(limit , since)
    }

    override fun getUserGitHub(username: String): Observable<UserGitHubProfile> {
        return remoteDataSource.getUserGitHub(username)
    }
}