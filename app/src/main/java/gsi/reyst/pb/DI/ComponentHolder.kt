package gsi.reyst.pb.DI

import gsi.reyst.pb.DI.components.AppComponent
import gsi.reyst.pb.DI.components.PersonComponent
import gsi.reyst.pb.DI.modules.PersonModule
import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.util.LoggerD

class ComponentHolder private constructor() {

    init {
        LoggerD("This ($this) is a singleton of ComponentHolder class")
    }

    private object Holder {
        val INSTANCE = ComponentHolder()
    }

    companion object {
        val instance: ComponentHolder by lazy { Holder.INSTANCE }
    }

    lateinit var appComponent: AppComponent

    private lateinit var personComponent: PersonComponent

    fun getPersonComponent(person: Person, index: Int): PersonComponent = appComponent.plus(PersonModule(person, index))

}