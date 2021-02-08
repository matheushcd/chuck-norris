package com.matheushcd.chucknorris.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.matheushcd.chucknorris.R
import com.matheushcd.chucknorris.model.Joke
import com.squareup.picasso.Picasso


class CurrencyListAdapter(private var activity: Activity, private var items: ArrayList<Joke>): BaseAdapter(){

    private class ViewHolder(row: View?) {
        var tvJokeDescription: TextView? = null
        var ivJokeImage: ImageView? = null

        init {
            this.tvJokeDescription = row?.findViewById(R.id.tvJokeDescription)
            this.ivJokeImage = row?.findViewById(R.id.ivJokeImage)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_joke, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item: Joke = items[position]
        viewHolder.tvJokeDescription?.text = item.value
        Picasso.with(activity).load(item.icon_url).into(viewHolder.ivJokeImage)
        return view as View
    }

    override fun getItem(position: Int): Joke {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}