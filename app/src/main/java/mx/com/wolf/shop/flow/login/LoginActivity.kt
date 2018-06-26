package mx.com.wolf.shop.flow.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.dd.processbutton.iml.ActionProcessButton
import kotlinx.android.synthetic.main.activity_login.*
import mx.com.wolf.shop.R
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.extensions.start
import mx.com.wolf.shop.flow.home.HomeActivity
import mx.com.wolf.shop.flow.login.di.DaggerLoginComponent
import mx.com.wolf.shop.flow.login.di.LoginModule
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginContract.View {

    companion object {
        val TAG = LoginActivity::class.simpleName
    }

    @Inject
    lateinit var  presenter: LoginPresenter

    lateinit var loginButton: ActionProcessButton

    fun inject() = DaggerLoginComponent
            .builder()
            .applicationComponent((applicationContext as ShopApplication).applicationComponent)
            .loginModule(LoginModule())
            .build()
            .inject(this)

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showHome() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        inject()
        presenter.attachView(this)
        presenter.login()

        loginButton = button_login.apply {
            setMode(ActionProcessButton.Mode.ENDLESS)
            setOnClickListener(loginButtonListener)
        }
    }

    private val loginButtonListener = View.OnClickListener {
        val userName = input_username.editText!!.text.toString()
        val password = input_password.editText!!.text.toString()

        if (password.isNotBlank() && userName.isNotBlank()) {
            loginButton.start()
            presenter.login(userName, password)
        }
    }
}
