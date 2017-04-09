package gsi.reyst.pb.DI.components

import dagger.Subcomponent
import gsi.reyst.pb.DI.modules.PersonModule
import gsi.reyst.pb.DI.scopes.PerPerson
import gsi.reyst.pb.mvp.model.PersonModel
import gsi.reyst.pb.mvp.presenter.PersonPresenter

@PerPerson
@Subcomponent(modules = arrayOf(PersonModule::class))
interface PersonComponent {

    fun getPersonPresenter() : PersonPresenter

    fun getPersonModel() : PersonModel

}