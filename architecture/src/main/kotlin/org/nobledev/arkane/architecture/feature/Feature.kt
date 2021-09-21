package org.nobledev.arkane.architecture.feature

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.plugin.PluginManager
import org.nobledev.arkane.architecture.feature.attribute.AttributeKey
import org.nobledev.arkane.architecture.feature.attribute.Attributes
import org.nobledev.arkane.architecture.feature.attribute.ConfigAdapter
import org.nobledev.arkane.architecture.feature.pipeline.Pipeline
import org.nobledev.arkane.architecture.feature.pipeline.PipelineContext
import org.nobledev.arkane.architecture.plugin.ArkanePlugin

/**
 * Feature that can be installed into a plugin and operate like normal.
 *
 * @property name the feature uses for different systems
 * @property concurrent true if the feature should use safe handling for multi-threaded processes
 */
abstract class Feature(val name: String, val concurrent: Boolean) {
    private val attribs = Attributes(concurrent)
    private val properties = mutableListOf<AttributeKey<*>>()

    protected abstract val pipeline: Pipeline

    private var operatingPlugin: ArkanePlugin? = null

    /**
     * Called when this feature is installed. If you override this, be sure to call the super function!
     *
     * @param plugin this feature is being installed to.
     */
    open fun onInstall(plugin: ArkanePlugin) {
        if (operatingPlugin != null) throw IllegalStateException("Attempted to reinstall feature : $name")
        operatingPlugin = plugin
        runPipeline()
        plugin.logger.info("Feature $name has been installed")
    }

    /**
     * Called when this feature is enabled.
     *
     * @param plugin this feature is being installed to.
     */
    open fun onEnable(plugin : ArkanePlugin) {}

    /**
     * Executes the pipeline and attempts to override properties from the plugins configuration.
     *
     */
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

    /**
     * Creates a property handler with defaults to use with the self configuration system.
     *
     * @param T of property value
     * @param name of property
     * @param adapter to use when looking in the config
     * @param default value
     * @return [AttributeKey] holding property information for retrieving it from attribute map.
     */
    protected fun <T : Any> setProperty(name: String, adapter: ConfigAdapter<T>, default : T) : AttributeKey<T> {
        val key = AttributeKey(name, adapter, default)
        properties.add(key)
        return key
    }

    private fun <T : Any> setAttributeFromConfig(key : AttributeKey<T>, conf : ConfigurationSection) {
        attribs.put(key, key.adapt(conf))
    }

    /**
     * Gets a property from the attribute map.
     *
     * @param T Type of property stored
     * @param key [AttributeKey] holding the property
     */
    protected fun <T : Any> getProperty(key : AttributeKey<T>) = attribs.getOrNull(key) ?: key.default

    /**
     * Builder function used to create the feature build pipeline.
     */
    protected fun buildPipeline(builder: Pipeline.() -> Unit) : Pipeline = Pipeline().apply(builder)


}




