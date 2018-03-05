package com.roix.mapchat.toothpick.common

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

/**
 * Created by roix on 05.03.2018.
 */
class CiceroneModule(cicerone: Cicerone<Router>) : Module(){
    init {
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(Router::class.java).toInstance(cicerone.router)
    }
}