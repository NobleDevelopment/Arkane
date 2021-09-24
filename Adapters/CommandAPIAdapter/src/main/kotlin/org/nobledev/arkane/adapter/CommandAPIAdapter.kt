package org.nobledev.arkane.adapter

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIConfig
import org.nobledev.arkane.architecture.feature.Feature
import org.nobledev.arkane.architecture.feature.pipeline.Pipeline
import org.nobledev.arkane.architecture.management.StubPlugin
import org.nobledev.arkane.architecture.plugin.ArkanePlugin

object CommandAPIAdapter : Feature("CommandAPI Adapter", true) {

    private val commandStub = StubPlugin("Arkane Commands System")
    private var isInstalled = false
    private var isEnabled = false
    override val pipeline: Pipeline = buildPipeline {
        buildPhase("Setup CommandAPI configuration"){
            CommandAPI.onLoad(CommandAPIConfig().silentLogs(true))
        }
    }

    override fun onInstall(plugin: ArkanePlugin) {
        if(isInstalled) return
        isInstalled = true
        super.onInstall(plugin)
    }

    override fun onEnable(plugin: ArkanePlugin) {
        if(isEnabled) return
        CommandAPI.onEnable(plugin)
        runPipeline()

    }
}