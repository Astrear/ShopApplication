package mx.com.wolf.shop.flow.home

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import mx.com.wolf.shop.R
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.extensions.requestPermission
import mx.com.wolf.shop.extensions.shouldShowPermissionRationale
import mx.com.wolf.shop.flow.home.di.DaggerHomeComponent
import mx.com.wolf.shop.flow.home.di.HomeModule
import mx.com.wolf.shop.flow.home.fragment.AddFragment
import mx.com.wolf.shop.flow.home.fragment.DeleteFragment
import mx.com.wolf.shop.flow.home.fragment.ListFragment
import javax.inject.Inject
import android.graphics.Bitmap
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.ImageView


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
                R.id.action_settings -> null
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
            Log.i(TAG, "In navigation with")
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
    fun requestWritePermission(permission: String) {
        Log.i("Permissions", "CAMERA permission has NOT been granted. Requesting permission.")
        if(!shouldShowPermissionRationale(permission)) {
            requestPermission(permission, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
