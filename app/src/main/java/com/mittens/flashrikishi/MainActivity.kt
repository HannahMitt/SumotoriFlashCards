package com.mittens.flashrikishi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.mittens.flashrikishi.api.SumoService
import com.mittens.flashrikishi.models.Rikishi
import com.mittens.flashrikishi.models.RikishiList
import com.mittens.flashrikishi.models.WrappedRikishi
import com.mittens.flashrikishi.models.WrappedRikishiList
import com.mittens.flashrikishi.views.RikishiViewLarge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    val apiKey = "gAt6edQQ9mGoOyWWaV1HxgGDmOyRia8l"

    lateinit var apiService: SumoService
    lateinit var rikishiView: RikishiViewLarge

    var rikishiList = RikishiList(ArrayList<Int>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rikishiView = RikishiViewLarge(findViewById(R.id.main_view_rikishi))
        rikishiView.card.visibility = View.INVISIBLE

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (rikishiView.name.visibility == View.VISIBLE) {
                getMysteryRikishi()
                button.text = "Show Name"
            } else {
                rikishiView.showName()
                button.text = "Next Rikishi"
            }
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("https://wrapapi.com/use/hannahmitt/sumo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiService = retrofit.create(SumoService::class.java)

        apiService.banzuke(apiKey)
                .enqueue(object : Callback<WrappedRikishiList> {
                    override fun onResponse(call: Call<WrappedRikishiList>?, response: Response<WrappedRikishiList>?) {
                        rikishiList = response?.body()?.data ?: rikishiList
                        getMysteryRikishi()
                    }

                    override fun onFailure(call: Call<WrappedRikishiList>?, t: Throwable?) {
                        Log.d("MAIN", "Banzuke failure")
                    }
                })
    }

    fun getMysteryRikishi() {
        if (rikishiList.rikishi.isEmpty()) {
            // No Rikishi in list
            Log.e("MAIN", "Empty rikishi")
            return
        }

        val index = Math.abs(Random().nextInt(rikishiList.rikishi.size))
        val rikishiId = rikishiList.rikishi[index]

        rikishiView.hideName()

        apiService.rikishi(rikishiId, apiKey)
                .enqueue(object : Callback<WrappedRikishi> {
                    override fun onResponse(call: Call<WrappedRikishi>?, response: Response<WrappedRikishi>?) {
                        response?.body()?.data?.let {
                            showRikishi(response.body()!!.data)
                        }
                    }

                    override fun onFailure(call: Call<WrappedRikishi>?, t: Throwable?) {
                        Log.d("MAIN", "Rikishi failure")
                    }
                })
    }

    fun showRikishi(rikishi: Rikishi) {
        rikishiView.card.visibility = View.VISIBLE
        rikishiView.bindRikishi(rikishi)
    }
}
