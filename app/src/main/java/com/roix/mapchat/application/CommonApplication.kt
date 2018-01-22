package com.roix.mapchat.application

import android.app.Application
import com.google.firebase.FirebaseApp
import com.roix.mapchat.BuildConfig
import com.roix.mapchat.FactoryRegistry
import com.roix.mapchat.MemberInjectorRegistry
import com.roix.mapchat.toothpick.common.ApplicationModule
import com.roix.mapchat.toothpick.common.ApplicationScope
import toothpick.Toothpick
import toothpick.Toothpick.setConfiguration
import toothpick.configuration.Configuration.forDevelopment
import toothpick.configuration.Configuration.forProduction
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator
import toothpick.smoothie.module.SmoothieApplicationModule

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class CommonApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        val configuration = if (BuildConfig.DEBUG) forDevelopment() else forProduction()
        setConfiguration(configuration.disableReflection())
        FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())

        val appScope = Toothpick.openScope(this)
        appScope.installModules(SmoothieApplicationModule(this), ApplicationModule(this))
        appScope.bindScopeAnnotation(ApplicationScope::class.java)
    }
}