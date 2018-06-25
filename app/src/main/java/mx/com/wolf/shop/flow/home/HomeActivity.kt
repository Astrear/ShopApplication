package mx.com.wolf.shop.flow.home

import android.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*
import mx.com.wolf.shop.R
import mx.com.wolf.shop.ShopApplication
import mx.com.wolf.shop.flow.home.di.DaggerHomeComponent
import mx.com.wolf.shop.flow.home.di.HomeModule
import mx.com.wolf.shop.flow.home.fragment.ListFragment
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    companion object {
        val TAG = HomeActivity::class.simpleName
    }

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun inject() {
        DaggerHomeComponent
                .builder()
                .applicationComponent((application as ShopApplication).applicationComponent)
                .homeModule(HomeModule())
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        inject()
        presenter.attachView(this)

        navigation.setOnNavigationItemSelectedListener({ v ->
            val fragment: Fragment? = when(v.itemId) {
                R.id.action_list -> ListFragment()
                R.id.action_add -> null
                R.id.action_delete -> null
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
}
