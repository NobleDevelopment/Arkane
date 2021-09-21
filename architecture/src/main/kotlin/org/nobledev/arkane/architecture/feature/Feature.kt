package org.nobledev.arkane.architecture.feature

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.plugin.PluginManager
import org.nobledev.arkane.architecture.feature.attribute.AttributeKey
import org.nobledev.arkane.architecture.feature.attribute.Attributes
import org.nobledev.arkane.architecture.feature.attribute.ConfigAdapter
import org.nobledev.arkane.architecture.feature.pipeline.Pipeline
import org.nobledev.arkane.architecture.feature.pipeline.PipelineContext
import org.nobledev.arkane.architecture.plugin.ArkanePlugin

abstract class Feature(val name: String, val concurrent: Boolean) {
    private val attribs = Attributes(concurrent)
    private val properties = mutableListOf<AttributeKey<*>>()

    protected abstract val pipeline: Pipeline

    private var operatingPlugin: ArkanePlugin? = null

    open fun onInstall(plugin: ArkanePlugin) {
        if (operatingPlugin != null) throw IllegalStateException("Attempted to reinstall feature : $name")
        operatingPlugin = plugin
        runPipeline()
        plugin.logger.info("Feature $name has been installed")
    }

    open fun onEnable(plugin : ArkanePlugin) {}

    fun runPipeline() {
        if (operatingPlugin == null) throw IllegalStateException("Attempted to execute pipeline without being installed!")
        attemptLoadConfiguration()
        pipeline.executePipeline(PipelineContext(operatingPlugin!!, attribs))
    }

    private fun attemptLoadConfiguration() {
        val conf = operatingPlugin!!.config.getConfigurationSection(name) ?: return
        properties.forEach { key ->
            if(conf.contains(key.name)) {
               setAttributeFromConfig(key, conf)
            }
        }
    }

    protected fun <T : Any> setProperty(name: String, adapter: ConfigAdapter<T>, default : T) : AttributeKey<T> {
        val key = AttributeKey(name, adapter, default)
        properties.add(key)
        return key
    }

    private fun <T : Any> setAttributeFromConfig(key : AttributeKey<T>, conf : ConfigurationSection) {
        attribs.put(key, key.adapt(conf))
    }

    protected fun <T : Any> getProperty(key : AttributeKey<T>) = attribs.getOrNull(key) ?: key.default

    protected fun buildPipeline(builder: Pipeline.() -> Unit) : Pipeline = Pipeline().apply(builder)


}




