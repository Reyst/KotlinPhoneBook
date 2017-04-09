package gsi.reyst.pb.mvp.presenter

import android.os.Bundle
import gsi.reyst.pb.DI.ComponentHolder
import gsi.reyst.pb.adapters.PersonAdapter
import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.mvp.model.PersonListModel
import gsi.reyst.pb.mvp.view.BaseFragment
import gsi.reyst.pb.mvp.view.PersonFragment
import gsi.reyst.pb.mvp.view.PersonListView
import gsi.reyst.pb.util.FRAGMENT_PERSON_EDIT
import gsi.reyst.pb.util.KEY_PERSON
import gsi.reyst.pb.util.KEY_POSITION

interface PersonListPresenter {
    fun initPresenterAndView(view: PersonListView)
    fun addPerson()
}


class PersonListPresenterImpl : BaseFragment(), PersonListPresenter, PersonAdapter.PersonEditor {
    private lateinit var view: PersonListView

    private lateinit var adapter: PersonAdapter
    private lateinit var model: PersonListModel

    override fun initPresenterAndView(view: PersonListView) {
        this.view = view
        model = ComponentHolder.instance.appComponent.getPersonListModel()
        adapter = PersonAdapter(model.getPersonList(), this)
        view.setAdapter(adapter)
    }

    private fun startEditPerson(person: Person, index: Int) {
        val params = Bundle()
        params.putSerializable(KEY_PERSON, person)
        params.putSerializable(KEY_POSITION, index)

        val fragmentChanger = view.obtainFragmentChanger()
        val epf = fragmentChanger.getFragmentById(FRAGMENT_PERSON_EDIT, params, false) as PersonFragment
        epf.onChangePersonListener = adapter
        fragmentChanger.changeFragment(epf, true)
    }

    override fun fabClick() = addPerson()

    override fun addPerson() = startEditPerson(Person(), -1)

    override fun editPerson(person: Person, index: Int) = startEditPerson(person, index)

}