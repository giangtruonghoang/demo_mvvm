package net.dirox.c4p.ui.activity.demo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_donor.view.*
import net.dirox.c4p.R
import net.dirox.c4p.common.util.Utils
import net.dirox.c4p.common.extension.custom
import net.dirox.c4p.data.response.UserGitHub.UserGitHub

class UserGitHubAdapter(
    var listUser: MutableList<UserGitHub>?,
    var listener: ListenerUserGitHubAdapter?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var isLoaderVisible: Boolean = false
    private var loadingCount: Int = 1
    var canLoadMore: Boolean = true
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder) {
            if (listUser!!.size > 0) {
                holder.bindFund(listUser!![position])
                holder.itemView.setOnClickListener {
                    if (listUser!![position].login != null)
                        listener?.onClickItem(listUser!![position].login!!)
                }
            }
        } else if (holder is LoadingViewHolder) {
            if (position < 100){
                holder.showLoading(isLoaderVisible)
                holder.itemView.setOnClickListener(null)
            }else holder.itemView.visibility = View.GONE
        }

        if (listUser != null && listUser!!.size > 19 && listUser!!.size < 100 && position == listUser!!.size - 1 && canLoadMore) {
            listener?.scrollToLastItem()
        }
    }

    override fun getItemCount(): Int {
        if (listUser != null) {
            loadingCount = if (canLoadMore) 1 else 0
            return listUser!!.size + loadingCount
        } else return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_donor, parent, false)
            Holder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_placeholder_donor, parent, false)
            LoadingViewHolder(view)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLoadMore(_canLoadMore: Boolean){
        canLoadMore = _canLoadMore
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(_data: MutableList<UserGitHub>?, _canLoadMore: Boolean) {
        canLoadMore = _canLoadMore
        if (_data != null) {
            this.listUser = _data
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(_data: List<UserGitHub>?, _canLoadMore: Boolean) {
        canLoadMore = _canLoadMore
        if (_data != null) {
            listUser!!.addAll(_data)
        }
        notifyDataSetChanged()
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showLoading(loading: Boolean) {
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindFund(user: UserGitHub) {
            Utils.makeTextBoldAndColor(itemView.context, user.login!!, user.login,  itemView.tvName)
            if (user.avatar_url != null && user.avatar_url.isNotEmpty())
                Glide.with(itemView.context).load(user.avatar_url)
                    .apply(RequestOptions.circleCropTransform().priority(Priority.HIGH))
                    .custom()
                    .into(itemView.ivAvatar)
            else itemView.ivAvatar.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.ic_avatar_default
                )
            )
            itemView.tvDay.text = user.url
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (listUser != null && listUser!!.size > 0)
            return if (position >= listUser!!.size) {
                VIEW_TYPE_LOADING
            } else {
                VIEW_TYPE_ITEM
            }
        else return VIEW_TYPE_LOADING

    }
}


interface ListenerUserGitHubAdapter {
    fun scrollToLastItem()
    fun onClickItem(username: String)
}