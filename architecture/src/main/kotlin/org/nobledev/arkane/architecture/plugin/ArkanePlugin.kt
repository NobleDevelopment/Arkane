package org.nobledev.arkane.architecture.plugin

import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.nobledev.arkane.architecture.feature.Feature

/**
 * Arkane plugin that automates Arkane's systems. All plugin classes should extend from this.
 */
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

    /**
     * Creates a module for dependency injection management
     */
    fun createModule(decleration: ModuleDeclaration) {
        val module = Module(createAtStart = false, override = false)
        decleration(module)
        modules.add(module)
    }

    private fun generateSelfModule() = createModule {
    }

    /**
     * Installs a feature to the plugin
     *
     * @param feature to install
     */
    fun install(feature: Feature) {
        features.add(feature)
        feature.onInstall(this)
    }
}

