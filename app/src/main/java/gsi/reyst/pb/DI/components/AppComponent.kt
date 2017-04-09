package gsi.reyst.pb.DI.components

import android.content.Context
import dagger.Component
import gsi.reyst.pb.DI.modules.AppModule
import gsi.reyst.pb.DI.modules.PersonModule
import gsi.reyst.pb.activities.MainActivity
import gsi.reyst.pb.mvp.model.PersonListModel
import gsi.reyst.pb.mvp.presenter.PersonListPresenter
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun plus(module: PersonModule) : PersonComponent

    fun getApplicationContext(): Context

    fun getPersonListPresenter(): PersonListPresenter

    fun getPersonListModel(): PersonListModel

}