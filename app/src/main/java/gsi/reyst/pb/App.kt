package gsi.reyst.pb

import android.app.Application
import gsi.reyst.pb.DI.ComponentHolder
import gsi.reyst.pb.DI.components.DaggerAppComponent
import gsi.reyst.pb.DI.modules.AppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ComponentHolder.instance.appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

    }
}
