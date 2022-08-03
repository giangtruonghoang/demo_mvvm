package net.dirox.c4p.ui.activity.demo

import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject
import net.dirox.c4p.base.viewmodel.BaseViewModel
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile
import net.dirox.c4p.data.repository.Repository
import org.koin.core.get

class UserGitHubViewModel : BaseViewModel() {

    private val repository: Repository = get()

    var listUserGitHub: PublishSubject<List<UserGitHub>> = PublishSubject.create()
    var userGitHub: PublishSubject<UserGitHubProfile> = PublishSubject.create()
    var canLoadMore: MutableLiveData<Boolean> = MutableLiveData(true)

    private var limit: Int = 20
    private var isLoadingData: Boolean = false

    fun getListUserGitHub(since : Int) {
        isLoadingData = true

        disposeBag.add(
            repository.getListUserGitHub(limit, since)
                .subscribe({ res ->
                    isLoadingData = false
                    listUserGitHub.onNext(res)
                }, { e ->
                    isLoadingData = false
                    error.value = Error(e)
                })
        )
    }

    fun getProfileUserGitHub(username : String) {
        isLoadingData = true

        disposeBag.add(
            repository.getUserGitHub(username)
                .subscribe({ res ->
                    isLoadingData = false
                    userGitHub.onNext(res)
                }, { e ->
                    isLoadingData = false
                    error.value = Error(e)
                })
        )
    }
}