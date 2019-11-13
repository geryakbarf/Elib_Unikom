package xyz.geryakbarf.android.elibunikom.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_not_login.*
import kotlinx.android.synthetic.main.fragment_not_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.api.LoginApi
import xyz.geryakbarf.android.elibunikom.models.BaseModels
import xyz.geryakbarf.android.elibunikom.ui.RegisterActivity
import xyz.geryakbarf.android.elibunikom.utils.Session

class NotSigninFragment : Fragment(), View.OnClickListener {

    val url = "http://android.geryakbarf.xyz/elib/"
    lateinit var progressDialog: ProgressDialog
    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_not_login, container, false)

        view.btnRegister.setOnClickListener(this)
        view.btnLogin.setOnClickListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = Session(view.context)
        progressDialog = ProgressDialog(view.context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
    }

    private fun changeFragment() {
        val t: FragmentTransaction = this.fragmentManager!!.beginTransaction()
        val fragment = ProfileFragment()
        t.replace(R.id.nav_host_fragment, fragment)
        t.commit()
    }

    private fun login() {
        val username = etUsername.text.toString()
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username cannot be empty!")
            return
        }

        val password = etPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password cannot be empty")
            return
        }

        progressDialog.show()

        val retrofit =
            Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build()
        val loginApi: LoginApi = retrofit.create(LoginApi::class.java)
        val response: Call<BaseModels> = loginApi.login(password, username)
        response.enqueue(object : Callback<BaseModels> {
            override fun onFailure(call: Call<BaseModels>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(context, "Connection Error!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseModels>, response: Response<BaseModels>) {
                val kode = response.body()?.kode
                if (kode == 1) {
                    progressDialog.dismiss()
                    session.save("username", response.body()!!.username)
                    session.save("email", response.body()!!.email)
                    session.save("name", response.body()!!.name)
                    session.save("akses", response.body()!!.akses)
                    session.isLogin("isLogin", true)
                    changeFragment()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(context, response.body()?.pesan, Toast.LENGTH_SHORT).show()
                }
            }

        })

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnRegister -> {
                val intent = Intent(view?.context, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btnLogin -> login()
        }
    }
}