package net.dirox.c4p.ui.activity.demo.userdetail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_user_github.*
import kotlinx.android.synthetic.main.activity_detail_user_github.ivAvatar
import kotlinx.android.synthetic.main.activity_detail_user_github.ivBack
import kotlinx.android.synthetic.main.activity_detail_user_github.tvEmail
import kotlinx.android.synthetic.main.activity_detail_user_github.tvName
import net.dirox.c4p.R
import net.dirox.c4p.base.activity.BaseActivity
import net.dirox.c4p.common.extension.back
import net.dirox.c4p.common.extension.custom
import net.dirox.c4p.data.response.UserGitHub.UserGitHubProfile
import net.dirox.c4p.ui.activity.demo.UserGitHubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserGitHubDetailActivity : BaseActivity() {
    companion object {
        var username: String? = null
    }
    private val mViewModel: UserGitHubViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user_github)
        bindViewModel()
        mViewModel.getProfileUserGitHub(username!!)
    }

    override fun onStart() {
        super.onStart()
    }

    fun initData(user : UserGitHubProfile){
            ivBack.setOnClickListener {
                back()
            }
            if (user.login != null)
                tvUserName.text = user.login
            if (user.name != null)
                tvName.text = user.name
            if (user.email != null)
                tvEmail.text = user.email
            if (user.location != null)
                tvLocation.text = user.location
            if (user.avatar_url != null && user.avatar_url.isNotEmpty())
                loadImageAvatar(user.avatar_url)
            if (user.blog != null)
                tvBlog.text = user.blog
    }

    private fun loadImageAvatar(url: String) {
        Glide.with(this).load(url).custom().into(ivAvatar)
        ivAvatar.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent))
    }

    private fun bindViewModel() {
        mViewModel.loading.observe(this, Observer { loading ->
            if (loading) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        mViewModel.error.observe(this, Observer { e ->
        })

        mViewModel.errorResId.observe(this, Observer { id ->
        })

        disposeBag.add(mViewModel.userGitHub.subscribe { res ->
            if (res != null){
                initData(res)
            }

        })
    }
}
