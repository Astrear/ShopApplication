package mx.com.wolf.shop.flow.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import mx.com.wolf.shop.R
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.flow.login.di.DaggerLoginComponent
import mx.com.wolf.shop.flow.login.di.LoginModule
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginContract.View {

    @Inject lateinit var  presenter: LoginPresenter

    fun inject() = DaggerLoginComponent
            .builder()
            .applicationComponent((applicationContext as ShopApplication).applicationComponent)
            .loginModule(LoginModule())
            .build()
            .inject(this)

    override fun setLoadingIndicator(status: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        inject()
        presenter.attachView(this)
        button_login.setOnClickListener {
            val userName = input_username.editText!!.text.toString()
            val password = input_password.editText!!.text.toString()

            if(password.isNotBlank() && userName.isNotBlank())
                presenter.login(userName, password)
        }
    }
}
