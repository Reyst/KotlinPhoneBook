package gsi.reyst.pb.mvp.view

import android.app.Fragment
import gsi.reyst.pb.util.FragmentChanger

abstract class BaseFragment : Fragment(), BaseView {
    lateinit var fragmentChanger: FragmentChanger
}

interface BaseView {
    fun fabClick()
}