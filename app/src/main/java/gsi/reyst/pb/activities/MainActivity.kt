package gsi.reyst.pb.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import gsi.reyst.pb.DI.ComponentHolder
import gsi.reyst.pb.R
import gsi.reyst.pb.mvp.presenter.MainActivityPresenter
import gsi.reyst.pb.mvp.view.MainView
import gsi.reyst.pb.util.FRAGMENT_PERSON_LIST
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        ComponentHolder.instance.appComponent.inject(this)
        presenter.onCreate(this)
        fab.setOnClickListener { presenter.fabClick() }

        val fragmentChanger = presenter.getFragmentChanger()

        with(fragmentChanger) {
            if (getCurrentFragment() == null) {
                val newFragment = getFragmentById(FRAGMENT_PERSON_LIST, Bundle(), true)
                if (newFragment != null) changeFragment(newFragment, false)
            }
        }
    }

    override fun doBack() = onBackPressed()

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.getFragmentChanger().updateCurrentFragment()
    }
}
