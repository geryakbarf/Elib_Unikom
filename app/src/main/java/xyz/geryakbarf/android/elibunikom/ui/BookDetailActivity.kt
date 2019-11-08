package xyz.geryakbarf.android.elibunikom.ui

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_book_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.api.DownloadApi
import xyz.geryakbarf.android.elibunikom.api.GetDetailApi
import xyz.geryakbarf.android.elibunikom.models.BaseModels
import xyz.geryakbarf.android.elibunikom.models.DetailModels

class BookDetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var url: String
    lateinit var progressDialog: ProgressDialog
    val urlku = "http://android.geryakbarf.xyz/elib/"
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        supportActionBar?.hide()

        id = intent.getStringExtra("id")
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.loading))
        btnBack.setOnClickListener(this)
        btnDownload.setOnClickListener(this)
        setDataView()
    }

    private fun setDataView() {
        Glide.with(applicationContext).load(intent.getStringExtra("img")).into(imgBooks)
        textView3.text = intent.getStringExtra("category")
        progressDialog.show()

        val retrofit =
            Retrofit.Builder().baseUrl(urlku).addConverterFactory(GsonConverterFactory.create())
                .build()
        val getDetailApi: GetDetailApi = retrofit.create(GetDetailApi::class.java)
        val call: Call<DetailModels> = getDetailApi.getDetail(id)
        call.enqueue(object : Callback<DetailModels> {
            override fun onFailure(call: Call<DetailModels>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Connection Error!", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onResponse(call: Call<DetailModels>, response: Response<DetailModels>) {
                progressDialog.dismiss()
                txtTitle.text = response.body()?.title
                txtDetail.text = response.body()?.description
                txtAuthor.text = response.body()?.author
                txtPublisher.text = response.body()?.publisher
                txtYear.text = response.body()?.year
                txtDate.text = response.body()?.date
                url = response.body()!!.file
            }

        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBack -> super.onBackPressed()
            R.id.btnDownload -> {
                //Todo Increase the download point from database
                val retrofit =
                    Retrofit.Builder().baseUrl(urlku)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val upvote: DownloadApi = retrofit.create(DownloadApi::class.java)
                val call: Call<BaseModels> = upvote.upvote(id)
                call.enqueue(object : Callback<BaseModels> {
                    override fun onFailure(call: Call<BaseModels>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failed to Download", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<BaseModels>,
                        response: Response<BaseModels>
                    ) {
                        val kode = response.body()?.kode
                        if (kode == 1) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                        } else
                            Toast.makeText(
                                applicationContext,
                                "Failed to Download :(",
                                Toast.LENGTH_SHORT
                            ).show()
                    }

                })


            }
        }
    }
}
