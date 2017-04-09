package gsi.reyst.pb.mvp.presenter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.EditText
import gsi.reyst.pb.DI.ComponentHolder
import gsi.reyst.pb.R
import gsi.reyst.pb.adapters.PhoneAdapter
import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.domain.Phone
import gsi.reyst.pb.mvp.model.PersonModel
import gsi.reyst.pb.mvp.view.PersonView

interface PersonPresenter {

    fun save()

    fun init(view: PersonView)

    fun getPersonName(): String

    fun getPersonSurname(): String

    fun setPersonName(name: String)

    fun setPersonSurname(surname: String)

    fun addPhone()

}

class PersonPresenterImpl(person: Person, index: Int) : PersonPresenter, PhoneAdapter.PhoneEditor {
    val model: PersonModel = ComponentHolder.instance.getPersonComponent(person, index).getPersonModel()

    val context: Context = ComponentHolder.instance.appComponent.getApplicationContext()

    lateinit var view: PersonView

    lateinit var adapter: PhoneAdapter

    override fun init(view: PersonView) {
        this.view = view
        adapter = PhoneAdapter(model.getPerson().phones, this)
        view.setAdapter(adapter)
    }

    override fun save() {
        model.save()

        val listener = view.onChangePersonListener
        if (model.isPersonNew())
            listener.onPersonAdded(model.getPerson())
        else
            listener.onPersonChanged(model.getPerson(), model.index)

        view.goBack()
    }

    override fun editPhone(phone: Phone, index: Int) {

        val alert = AlertDialog.Builder(view.getActivity())
        val input = EditText(view.getActivity())

        input.id = R.id.phone_edit_text_id
        input.setText(phone.phoneNo)
        with(alert) {
            setView(input)
            setPositiveButton(android.R.string.ok) { _, _ ->
                if (!TextUtils.isEmpty(input.text.toString())) {
                    phone.phoneNo = input.text.toString()
                    adapter.notifyItemChanged(index)
                }
            }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }

    override fun addPhone() {
        val alert = AlertDialog.Builder(view.getActivity())
        val input = EditText(view.getActivity())
        input.id = R.id.phone_edit_text_id

        with(alert) {
            setView(input)
            setPositiveButton(android.R.string.ok) { _, _ ->
                val phoneNo = input.text.toString()
                if (!TextUtils.isEmpty(phoneNo)) {
                    model.addPhone(Phone(phoneNo))
                    adapter.notifyDataSetChanged()
                }
            }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }

    override fun getPersonName(): String = model.getPerson().name

    override fun getPersonSurname(): String = model.getPerson().surname

    override fun setPersonName(name: String) {
        model.getPerson().name = name
    }

    override fun setPersonSurname(surname: String) {
        model.getPerson().surname = surname
    }

}