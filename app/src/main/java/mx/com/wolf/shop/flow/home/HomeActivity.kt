package mx.com.wolf.shop.flow.home

import android.annotation.TargetApi
import android.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import mx.com.wolf.shop.R
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.extensions.batchRequestPermissions
import mx.com.wolf.shop.extensions.hasPermissions
import mx.com.wolf.shop.flow.home.di.DaggerHomeComponent
import mx.com.wolf.shop.flow.home.di.HomeModule
import mx.com.wolf.shop.flow.home.fragment.AddFragment
import mx.com.wolf.shop.flow.home.fragment.DeleteFragment
import mx.com.wolf.shop.flow.home.fragment.ListFragment
import mx.com.wolf.shop.flow.home.fragment.SettingsFragment
import mx.com.wolf.shop.flow.login.LoginActivity
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeContract.View {

    companion object {
        val TAG = HomeActivity::class.simpleName
    }

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var itemRepository: ItemRepository

    fun inject() {
        DaggerHomeComponent
                .builder()
                .applicationComponent((application as ShopApplication).applicationComponent)
                .homeModule(HomeModule())
                .build()
                .inject(this)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        inject()
        presenter.attachView(this)

        itemRepository = ViewModelProviders.of(this, viewModelFactory)[ItemRepository::class.java]

        navigation.setOnNavigationItemSelectedListener({ v ->
            val fragment: Fragment? = when(v.itemId) {
                R.id.action_list -> ListFragment()
                R.id.action_add -> AddFragment()
                R.id.action_delete -> DeleteFragment()
                R.id.action_settings -> SettingsFragment()
                else -> null
            }

            if(fragment != null) {

                val tag = fragment.javaClass.name
                val current = fragmentManager.findFragmentById(R.id.fragment_container)
                if(current.javaClass.name != tag)
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(tag)
                            .commit()

            }
            true
        })

        with(ListFragment()) {
            this@HomeActivity.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, this)
                    .addToBackStack(this.javaClass.name)
                    .commit()
        }
    }

    override fun onBackPressed() {
        if(fragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestMultPermissions(permissions: Array<String>, requestId: Int) {
        if(!hasPermissions(permissions))
            batchRequestPermissions(permissions, requestId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
