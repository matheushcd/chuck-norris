package com.matheushcd.chucknorris.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.matheushcd.chucknorris.R
import com.matheushcd.chucknorris.contract.MainContract
import com.matheushcd.chucknorris.model.Joke
import com.matheushcd.chucknorris.presenter.MainPresenter
import com.matheushcd.chucknorris.view.adapter.CurrencyListAdapter


class MainActivity : AppCompatActivity(), MainContract.View {

    var lvJokes: ListView? = null

    var presenter : MainPresenter = MainPresenter()
    internal lateinit var adapter: CurrencyListAdapter
    var progressBar: ProgressBar? = null
    var spCategories: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()

        presenter.setView(this)
        presenter.getList()
        presenter.getListCategory()
    }

    private fun setupView(){
        progressBar = findViewById<ProgressBar>(R.id.PBProgress)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        lvJokes = findViewById(R.id.lvJokes)
        val btSearch = findViewById<Button>(R.id.btSearch)
        val btSearchByCategory = findViewById<Button>(R.id.btSearchCategory)
        lvJokes!!.setOnItemClickListener { _, view, position, _ ->
            val item = adapter.getItem(position)
            val intent: Intent = Intent(this, DetailJokeActivity::class.java)
            intent.putExtra("URL", item.url)
            startActivity(intent)
        }

        btSearch.setOnClickListener {
            presenter.search(etSearch.text.toString())
        }

        btSearchByCategory.setOnClickListener {
            presenter.searchBycategory(spCategories!!.selectedItem.toString())
        }

    }

    override fun loadRecycler(list: ArrayList<Joke>) {
        adapter = CurrencyListAdapter(this, list)
        lvJokes?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun loadSpinner(list: ArrayList<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategories = findViewById<View>(R.id.spCategories) as Spinner
        spCategories!!.adapter = adapter
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun showMsgError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}