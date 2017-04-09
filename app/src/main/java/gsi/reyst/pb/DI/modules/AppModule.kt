package gsi.reyst.pb.DI.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import gsi.reyst.pb.mvp.model.PersonListModel
import gsi.reyst.pb.mvp.model.PersonListModelImpl
import gsi.reyst.pb.mvp.presenter.MainActivityPresenter
import gsi.reyst.pb.mvp.presenter.MainActivityPresenterImpl
import gsi.reyst.pb.mvp.presenter.PersonListPresenter
import gsi.reyst.pb.mvp.presenter.PersonListPresenterImpl
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideMainActivityPresenter(): MainActivityPresenter = MainActivityPresenterImpl()

    @Provides
    @Singleton
    fun providePersonListPresenter(): PersonListPresenter = PersonListPresenterImpl()

    @Provides
    @Singleton
    fun providePersonListModel(): PersonListModel = PersonListModelImpl()

}