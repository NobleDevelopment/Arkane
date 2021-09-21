package org.nobledev.arkane.architecture.plugin

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.nobledev.arkane.architecture.feature.Feature

abstract class ArkanePlugin : JavaPlugin() {

    private val modules = mutableListOf<Module>()

    private val features = mutableListOf<Feature>()



    override fun onEnable() {
        generateSelfModule()
        features.forEach {
            it.onEnable(this)
        }
        startKoin {
            modules(modules)
        }


    }

    fun createModule(decleration: ModuleDeclaration) {
        val module = Module(createAtStart = false, override = false)
        decleration(module)
        modules.add(module)
    }

    private fun generateSelfModule() = createModule {
        single { this@ArkanePlugin.server.pluginManager }
        single { this@ArkanePlugin.server.scheduler }
    }


    fun <T : Feature> install(feature: Feature) {
        features.add(feature)
        feature.onInstall(this)
    }


}

