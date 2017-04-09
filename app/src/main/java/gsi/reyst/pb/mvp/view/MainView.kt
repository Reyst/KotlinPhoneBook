package gsi.reyst.pb.mvp.view

import android.app.FragmentManager

interface MainView : Back {

    fun getFragmentManager() : FragmentManager

}

interface Back {
    fun doBack()
}