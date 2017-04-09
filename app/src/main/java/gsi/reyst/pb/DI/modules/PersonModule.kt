package gsi.reyst.pb.DI.modules

import dagger.Module
import dagger.Provides
import gsi.reyst.pb.DI.scopes.PerPerson
import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.mvp.model.PersonModel
import gsi.reyst.pb.mvp.model.PersonModelImpl
import gsi.reyst.pb.mvp.presenter.PersonPresenter
import gsi.reyst.pb.mvp.presenter.PersonPresenterImpl

@Module
class PersonModule(val person : Person, val index : Int) {

    @PerPerson
    @Provides
    fun providePerson() : Person = person

    @PerPerson
    @Provides
    fun provideIndex() : Int = index

    @PerPerson
    @Provides
    fun providePersonPresenter() : PersonPresenter = PersonPresenterImpl(person, index)

    @PerPerson
    @Provides
    fun providePersonModel() : PersonModel = PersonModelImpl(person, index)


}