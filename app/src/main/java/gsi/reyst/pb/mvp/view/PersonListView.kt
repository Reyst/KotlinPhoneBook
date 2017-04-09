package gsi.reyst.pb.mvp.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gsi.reyst.pb.DI.ComponentHolder
import gsi.reyst.pb.R
import gsi.reyst.pb.mvp.presenter.PersonListPresenter
import gsi.reyst.pb.util.FragmentChanger

interface PersonListView {

    fun setAdapter(adapter: RecyclerView.Adapter<*>)

    fun obtainFragmentChanger(): FragmentChanger
}

class PersonListFragment : BaseFragment(), PersonListView {
    companion object {

        fun getInstance(params: Bundle): PersonListFragment {
            val fragment = PersonListFragment()
            fragment.arguments = params
            return fragment
        }
    }
    lateinit private var rv_person: RecyclerView

    lateinit private var presenter: PersonListPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ComponentHolder.instance.appComponent.getPersonListPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var fragmentView = view
        if (view == null) {
            fragmentView = inflater!!.inflate(R.layout.fragment_person_list, container, false)
            rv_person = fragmentView.findViewById(R.id.rv_person) as RecyclerView
            rv_person.layoutManager = LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
            presenter.initPresenterAndView(this)
        } else {
            fragmentView.invalidate()
        }

        return fragmentView
    }

    override fun obtainFragmentChanger(): FragmentChanger = fragmentChanger

    override fun fabClick() {
        presenter.addPerson()
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        rv_person.adapter = adapter
    }
}
