package gsi.reyst.pb.mvp.model

import gsi.reyst.pb.domain.Person

interface PersonListModel {

    fun getPersonList() : MutableList<Person>

}

class PersonListModelImpl : PersonListModel {

    val data: MutableList<Person>

    init {
        data = ArrayList()
    }

    override fun getPersonList(): MutableList<Person> = data

}