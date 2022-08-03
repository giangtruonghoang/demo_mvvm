package net.dirox.c4p.ui.activity.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_github.*
import net.dirox.c4p.R
import net.dirox.c4p.base.activity.BaseActivity
import net.dirox.c4p.data.response.UserGitHub.UserGitHub
import net.dirox.c4p.ui.activity.demo.adapter.ListenerUserGitHubAdapter
import net.dirox.c4p.ui.activity.demo.adapter.UserGitHubAdapter
import net.dirox.c4p.ui.activity.demo.userdetail.UserGitHubDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListAllUserGitHubActivity : BaseActivity(), ListenerUserGitHubAdapter {
    companion object {
    }
    private val mViewModel: UserGitHubViewModel by viewModel()
    private var mListUserGitHub: MutableList<UserGitHub> = mutableListOf()
    private lateinit var mAdapter: UserGitHubAdapter
    private var since: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_github)
        initViews()
        bindViewModel()
        mViewModel.getListUserGitHub(1)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initViews() {
        mSwipeRefreshLayout.setOnRefreshListener {
            since = 1
            mListUserGitHub.clear()
            mAdapter.notifyDataSetChanged()
            mViewModel.getListUserGitHub(since)
        }

        mAdapter = UserGitHubAdapter(mListUserGitHub, this);
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(mAdapter)
    }

    private fun bindViewModel() {
        mViewModel.loading.observe(this, Observer { loading ->
            if (loading) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        mViewModel.error.observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
        })

        mViewModel.errorResId.observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
        })

        disposeBag.add(mViewModel.listUserGitHub.subscribe { res ->
            if (res != null && res.size != 0){
                lnNoItems.visibility = View.GONE
                if (since == 1){
                    mAdapter.setData(res as MutableList<UserGitHub>?, true)
                    since = res.last().id
                } else if (since < res.last().id && since > 1){
                    mAdapter.addData(res, true)
                    since = res.last().id
                }else if(since == since){
                    mAdapter.setLoadMore(false)
                }
            }else if (since == 1){
                lnNoItems.visibility = View.VISIBLE
            }
            mSwipeRefreshLayout.setRefreshing(false)
        })
    }

    override fun scrollToLastItem() {
        mViewModel.getListUserGitHub(since)
    }

    override fun onClickItem(username: String) {
        if (username.isNotEmpty()){
            UserGitHubDetailActivity.username = username
            val intent = Intent(this, UserGitHubDetailActivity::class.java)
            startActivity(intent)
        }
    }
}
