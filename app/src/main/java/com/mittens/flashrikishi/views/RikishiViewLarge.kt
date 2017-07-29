package com.mittens.flashrikishi.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mittens.flashrikishi.R
import com.mittens.flashrikishi.models.Rikishi

class RikishiViewLarge {

    val imageBaseUrl = "http://sumo.or.jp"

    val card: View
    val image: ImageView
    val name: TextView

    constructor(view: View) {
        card = view.findViewById(R.id.rikishi_card)
        image = view.findViewById<ImageView>(R.id.rikishi_image);
        name = view.findViewById<TextView>(R.id.rikishi_text_name);
    }

    fun bindRikishi(rikishi: Rikishi) {
        Glide.with(image).load(imageBaseUrl + rikishi.picture).into(image)
        name.setText(rikishi.name)
    }

    fun hideName() {
        name.visibility = View.GONE
    }

    fun showName() {
        name.visibility = View.VISIBLE
    }

}