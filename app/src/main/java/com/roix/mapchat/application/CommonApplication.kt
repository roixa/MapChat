package com.roix.mapchat.application

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.google.firebase.FirebaseApp
import com.roix.mapchat.BuildConfig
import com.roix.mapchat.FactoryRegistry
import com.roix.mapchat.MemberInjectorRegistry
import com.roix.mapchat.toothpick.common.ApplicationModule
import com.roix.mapchat.toothpick.common.ApplicationScope
import com.roix.mapchat.toothpick.common.CiceroneModule
import com.squareup.leakcanary.LeakCanary
import ru.terrakok.cicerone.Cicerone
import toothpick.Toothpick
import toothpick.configuration.Configuration.forDevelopment
import toothpick.configuration.Configuration.forProduction
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class CommonApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        FirebaseApp.initializeApp(this)

        val configuration = if (BuildConfig.DEBUG) forDevelopment() else forProduction()
        Toothpick.setConfiguration(configuration.disableReflection())
        FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())

        val appScope = Toothpick.openScope(this)
        appScope.installModules(
                ApplicationModule(this),
                CiceroneModule(Cicerone.create())
        )
        appScope.bindScopeAnnotation(ApplicationScope::class.java)
        try {
            MapsInitializer.initialize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}