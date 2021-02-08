package com.matheushcd.chucknorris.presenter

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.matheushcd.chucknorris.BuildConfig
import com.matheushcd.chucknorris.contract.MainContract
import com.matheushcd.chucknorris.model.Joke
import com.matheushcd.chucknorris.model.ResultJoke
import org.json.JSONArray

class MainPresenter : MainContract.Presenter {

    private var view : MainContract.View? = null

    override fun setView(view: MainContract.View) {
        this.view = view
    }

    override fun getList() {
        view?.showProgress()
        val queue = Volley.newRequestQueue(view?.getContext())
        val url = BuildConfig.BASE_URL+"random"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val stringJson = response.toString()
                    val joke = Gson().fromJson(stringJson, Joke::class.java)
                    val resultArray: ArrayList<Joke> = arrayListOf()
                    resultArray.add(joke)

                    view?.hideProgress()
                    view?.loadRecycler(resultArray)
                },
                { error ->
                    view?.hideProgress()
                    view?.showMsgError("Falha na requisição, tente novamente mais tarde.")
                }
        )
        queue.add(jsonObjectRequest)
    }

    override fun getListCategory() {
        val queue = Volley.newRequestQueue(view?.getContext())
        val url = BuildConfig.BASE_URL+"categories"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->
                    val resultArrayCategories: ArrayList<String> = arrayListOf()
                    val jsonArrayCategoriesResponse = response as JSONArray
                    if (jsonArrayCategoriesResponse != null) {
                        val len: Int = jsonArrayCategoriesResponse.length()
                        for (i in 0 until len) {
                            resultArrayCategories.add(jsonArrayCategoriesResponse.get(i).toString())
                        }
                        view?.loadSpinner(resultArrayCategories)
                    } else {
                        view?.showMsgError("Falha ao buscar categorias, tente novamente mais tarde.")
                    }
                },
                { error ->
                    view?.showMsgError("Falha na requisição, tente novamente mais tarde.")
                }

        )
        queue.add(jsonArrayRequest)
    }

    override fun search(query: String) {
        if (query.isNotBlank()) {
            if(query.length < 3){
                view?.showMsgError("O termo pesquisado precisa ter no mínimo 3 letras.")
            } else {
                view?.showProgress()
                val queue = Volley.newRequestQueue(view?.getContext())
                val url = BuildConfig.BASE_URL + "search?query=" + query

                val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                        { response ->
                            val stringJson = response.toString()
                            val result = Gson().fromJson(stringJson, ResultJoke::class.java)

                            if (result.result.size > 0) {
                                view?.hideProgress()
                                view?.loadRecycler(result.result as ArrayList<Joke>)
                            } else {
                                view?.hideProgress()
                                view?.showMsgError("Sem piadas para o termo pesquisado.")
                            }
                        },
                        { error ->
                            view?.hideProgress()
                            view?.showMsgError("Falha na requisição, tente novamente mais tarde.")
                        }
                )
                queue.add(jsonObjectRequest)
            }
        } else {
            view?.showMsgError("Digite algum termo antes de pesquisar.")
        }
    }

    override fun searchBycategory(category: String) {
        if (category.isNotBlank()) {
            view?.showProgress()
            val queue = Volley.newRequestQueue(view?.getContext())
            val url = BuildConfig.BASE_URL + "random?category=" + category

            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->
                        val stringJson = response.toString()
                        val joke = Gson().fromJson(stringJson, Joke::class.java)
                        val resultArray: ArrayList<Joke> = arrayListOf()
                        resultArray.add(joke)

                        view?.hideProgress()
                        view?.loadRecycler(resultArray)

                    },
                    { error ->
                        view?.hideProgress()
                        view?.showMsgError("Falha na requisição, tente novamente mais tarde.")
                    }
            )
            queue.add(jsonObjectRequest)
        } else {
            view?.showMsgError("Digite algum termo antes de pesquisar.")
        }
    }

    override fun onDestroy() {
        this.view = null
    }

}