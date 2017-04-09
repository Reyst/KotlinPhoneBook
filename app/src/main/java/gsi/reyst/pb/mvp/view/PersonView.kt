package gsi.reyst.pb.mvp.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import gsi.reyst.pb.DI.ComponentHolder
import gsi.reyst.pb.DI.modules.PersonModule
import gsi.reyst.pb.R
import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.mvp.presenter.PersonPresenter
import gsi.reyst.pb.util.KEY_PERSON
import gsi.reyst.pb.util.KEY_POSITION
import gsi.reyst.pb.util.getTextChangeObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

interface PersonView : BaseView {
    var onChangePersonListener: OnChangePersonListener

    fun setAdapter(adapter: RecyclerView.Adapter<*>)
    fun goBack()
    fun getActivity(): Context
}

interface OnChangePersonListener {
    fun onPersonChanged(person: Person, index: Int)
    fun onPersonAdded(person: Person)
}

class PersonFragment : BaseFragment(), PersonView {

    companion object {
        fun getInstance(params: Bundle): PersonFragment {
            val fragment = PersonFragment()
            fragment.arguments = params
            return fragment
        }
    }

    private lateinit var disposable: CompositeDisposable
    override lateinit var onChangePersonListener: OnChangePersonListener

    lateinit private var te_name: TextInputEditText
    lateinit private var te_surname: TextInputEditText
    lateinit private var btn_save: Button
    lateinit private var rv_phones: RecyclerView

    lateinit var presenter: PersonPresenter

    fun getPersonPresenter(person: Person, index: Int): PersonPresenter =
            ComponentHolder.instance.appComponent.plus(PersonModule(person, index)).getPersonPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val person = arguments.getSerializable(KEY_PERSON) as Person
        val position = arguments.getInt(KEY_POSITION, -1)
        presenter = getPersonPresenter(person, position)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentView = inflater!!.inflate(R.layout.fragment_person, container, false)

        rv_phones = fragmentView.findViewById(R.id.rv_phones) as RecyclerView
        rv_phones.layoutManager = LinearLayoutManager(fragmentView.context, LinearLayoutManager.VERTICAL, false)

        btn_save = fragmentView.findViewById(R.id.btn_save) as Button
        btn_save.setOnClickListener { presenter.save() }

        te_name = fragmentView.findViewById(R.id.te_name) as TextInputEditText
        te_surname = fragmentView.findViewById(R.id.te_surname) as TextInputEditText

        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(this)
        te_name.setText(presenter.getPersonName())
        te_surname.setText(presenter.getPersonSurname())
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        rv_phones.adapter = adapter
    }

    override fun fabClick() {
        presenter.addPhone()
    }

    override fun goBack() {
        fragmentChanger.doBack()
    }

    override fun onStart() {
        super.onStart()
        disposable = CompositeDisposable()
        disposable.add(Observable.combineLatest(
                getTextChangeObservable(te_name)
                        .doOnNext { text -> presenter.setPersonName(text) }
                        .map { it -> !TextUtils.isEmpty(it) }
                        .doOnNext { it -> checkAndSetError(it, te_name, getString(R.string.str_cannot_be_empty)) },
                getTextChangeObservable(te_surname)
                        .doOnNext { text -> presenter.setPersonSurname(text) }
                        .map { it -> !TextUtils.isEmpty(it) }
                        .doOnNext { it -> checkAndSetError(it, te_surname, getString(R.string.str_cannot_be_empty)) },
                BiFunction<Boolean, Boolean, Boolean> { f1, f2 -> f1 && f2 })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ btn_save::setEnabled }))
    }

    private fun checkAndSetError(flag: Boolean, textView: TextView, error: String) {
        if (flag) textView.error = null
        else textView.error = error
    }

    override fun onStop() {
        disposable.clear()
        super.onStop()
    }
}
