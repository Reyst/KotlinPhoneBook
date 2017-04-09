package gsi.reyst.pb.mvp.model

import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.domain.Phone
import gsi.reyst.pb.util.LoggerD

interface PersonModel {

    val index : Int

    fun getPerson() : Person
    fun save()
    fun isPersonNew(): Boolean
    fun addPhone(phone: Phone)
}

class PersonModelImpl(person : Person, override val index : Int) : PersonModel {

    private val currentPerson : Person = person

    private val workPerson : Person = Person(person.name, person.surname)

    init {
        workPerson.phones.addAll(person.phones)
        LoggerD(workPerson.toString())
    }

    override fun getPerson(): Person = workPerson

    override fun isPersonNew(): Boolean = index == -1

    override fun addPhone(phone: Phone) {
        workPerson.phones.add(phone)
    }

    override fun save() {
        currentPerson.name = workPerson.name
        currentPerson.surname = workPerson.surname

        with(currentPerson.phones) {
            clear()
            addAll(workPerson.phones)
        }
    }
}