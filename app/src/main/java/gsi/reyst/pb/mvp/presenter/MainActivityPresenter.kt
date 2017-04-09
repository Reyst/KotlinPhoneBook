package gsi.reyst.pb.mvp.presenter

import gsi.reyst.pb.mvp.view.BaseView
import gsi.reyst.pb.mvp.view.MainView
import gsi.reyst.pb.util.FragmentChanger
import gsi.reyst.pb.util.FragmentChangerImpl

interface MainActivityPresenter {

    fun getFragmentChanger(): FragmentChanger

    fun onCreate(view: MainView)

    fun fabClick()

}

class MainActivityPresenterImpl : MainActivityPresenter {
    lateinit var view: MainView

    private lateinit var fragmentChanger: FragmentChanger

    override fun onCreate(view: MainView) {
        this.view = view
        fragmentChanger = FragmentChangerImpl(view.getFragmentManager(), view)
    }

    override fun getFragmentChanger(): FragmentChanger = fragmentChanger

    override fun fabClick() = (fragmentChanger.getCurrentFragment() as BaseView).fabClick()

}

