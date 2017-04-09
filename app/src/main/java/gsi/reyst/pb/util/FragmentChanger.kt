package gsi.reyst.pb.util

import android.app.FragmentManager
import android.os.Bundle
import android.util.SparseArray
import gsi.reyst.pb.R
import gsi.reyst.pb.mvp.view.Back
import gsi.reyst.pb.mvp.view.BaseFragment
import gsi.reyst.pb.mvp.view.PersonFragment
import gsi.reyst.pb.mvp.view.PersonListFragment

val FRAGMENT_PERSON_LIST = 1
val FRAGMENT_PERSON_EDIT = 2

val KEY_POSITION = "position"
val KEY_PERSON = "person"


interface FragmentChanger : Back {

    fun getFragmentById(fragmentId: Int, params: Bundle, saveInstance: Boolean = true): BaseFragment?

    fun changeFragment(newFragment: BaseFragment, saveState: Boolean)

    fun getCurrentFragment(): BaseFragment?

    fun updateCurrentFragment()

}

class FragmentChangerImpl(val fragmentManager: FragmentManager, val backMover: Back) : FragmentChanger {
    private val fragmentHolder: SparseArray<BaseFragment> = SparseArray()

    private var activeFragment: BaseFragment? = null

    override fun getCurrentFragment(): BaseFragment?  = activeFragment

    override fun getFragmentById(fragmentId: Int, params: Bundle, saveInstance: Boolean): BaseFragment? {
        val result: BaseFragment? = if (fragmentHolder[fragmentId] != null) fragmentHolder[fragmentId]
        else {
            when (fragmentId) {
                FRAGMENT_PERSON_LIST -> PersonListFragment.getInstance(params)
                FRAGMENT_PERSON_EDIT -> PersonFragment.getInstance(params)
                else -> null
            }
        }

        result?.fragmentChanger = this

        if (saveInstance && result != null) fragmentHolder.put(fragmentId, result)

        return result
    }

    override fun doBack() {
        backMover.doBack()
    }

    override fun changeFragment(newFragment: BaseFragment, saveState: Boolean) {
        if (activeFragment != newFragment) {
            val tx = fragmentManager.beginTransaction()
            with(tx) {
                if (activeFragment != null && saveState) addToBackStack(activeFragment?.javaClass?.simpleName)
                replace(R.id.fragment_container, newFragment)
                commit()
            }
            activeFragment = newFragment
        }
    }

    override fun updateCurrentFragment() {
        activeFragment = fragmentManager.findFragmentById(R.id.fragment_container) as BaseFragment
    }
}