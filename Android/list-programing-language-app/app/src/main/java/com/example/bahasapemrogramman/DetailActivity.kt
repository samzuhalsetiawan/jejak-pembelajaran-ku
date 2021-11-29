package com.example.bahasapemrogramman

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.biubiubiu.justifytext.library.JustifyTextView
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_POSITION = "extraPosition"
        const val EXTRA_NAME = "programmingName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailPosition = intent.getIntExtra(EXTRA_POSITION, 0)
        val detailName = intent.getStringExtra(EXTRA_NAME)

        val ivPL: ImageView = findViewById(R.id.iv_detail_logo_pl)
        val tvDetailName: TextView = findViewById(R.id.tv_detail_name)
        val tvTahun: TextView = findViewById(R.id.tv_detail_tahun)
        val tvPembuat: TextView = findViewById(R.id.tv_detail_pembuat)
        val tvEkstensi: TextView = findViewById(R.id.tv_detail_ekstensi)
        val tvRincian: JustifyTextView = findViewById(R.id.tv_rincian)

        Glide.with(this)
            .load(PLDetails.logo[detailPosition])
            .apply(RequestOptions().override(200, 200))
            .into(ivPL)
        tvDetailName.text = detailName
        tvTahun.text = PLDetails.quickDetail[detailPosition][0]
        tvPembuat.text = PLDetails.quickDetail[detailPosition][1]
        tvEkstensi.text = PLDetails.quickDetail[detailPosition][2]
        tvRincian.text = PLDetails.fullDetail[detailPosition]
//        tvRincian.movementMethod = ScrollingMovementMethod()

    }
}