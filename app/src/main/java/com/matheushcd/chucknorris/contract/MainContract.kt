package com.matheushcd.chucknorris.contract

import android.content.Context
import com.matheushcd.chucknorris.model.Joke

interface MainContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showMsgError(msg: String)
        fun getContext(): Context
        fun loadRecycler(list: ArrayList<Joke>)
        fun loadSpinner(list: ArrayList<String>)
    }

    interface Presenter {
        fun setView(view: View)
        fun getList()
        fun getListCategory()
        fun search(query: String)
        fun onDestroy()
        fun searchBycategory(category: String)
    }

}