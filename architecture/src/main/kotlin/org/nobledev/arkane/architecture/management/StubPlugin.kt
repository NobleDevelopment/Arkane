package org.nobledev.arkane.architecture.management

import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginLoader
import org.bukkit.plugin.RegisteredListener
import org.nobledev.arkane.architecture.plugin.ArkanePlugin
import java.io.File
import java.io.InputStream
import java.lang.ClassLoader.getSystemResourceAsStream
import java.nio.file.Files.copy
import java.nio.file.Files.exists
import java.util.jar.JarFile
import java.util.logging.Logger
import java.util.regex.Pattern
import java.util.regex.Pattern.compile

/**
 * DO NOT USE THIS
 */
@Deprecated("You should not use this in your plugins!")
class StubPlugin(private val name : String) : Plugin {

    private var naggable = false
    private var enabled = true
    private val config = YamlConfiguration()

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ) = emptyList<String>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean = false


    override fun getDataFolder(): File  = File("plugins", name)

    override fun getDescription(): PluginDescriptionFile = PluginDescriptionFile(name, "1.0.0", "")

    override fun getConfig(): FileConfiguration = config

    override fun getResource(filename: String) = getSystemResourceAsStream(filename)!!

    override fun saveConfig()  = config.save(File(dataFolder, "config.yml"))

    override fun saveDefaultConfig() = saveResource("config.yml", false)

    override fun saveResource(resourcePath: String, replace: Boolean) {
        val file = dataFolder.toPath().resolve(resourcePath)
        if(replace || !exists(file)) getResource(resourcePath).use { copy(it, file) }
    }

    override fun reloadConfig() = config.load(File(dataFolder, "config.yml"))



    override fun getServer(): Server = Bukkit.getServer()

    override fun isEnabled(): Boolean = enabled

    override fun onDisable() {}

    override fun onLoad() {}

    override fun onEnable() {}

    override fun isNaggable(): Boolean = isNaggable

    override fun setNaggable(canNag: Boolean) {
        naggable = canNag
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator? {
        TODO("Not yet implemented")
    }

    override fun getLogger(): Logger = Bukkit.getLogger()

    override fun getName(): String = name

    override fun getPluginLoader(): PluginLoader = object : PluginLoader {
        override fun enablePlugin(plugin: Plugin) = getPluginManager().enablePlugin(plugin)
        override fun disablePlugin(plugin: Plugin) = getPluginManager().disablePlugin(plugin)

        override fun getPluginDescription(file: File): PluginDescriptionFile = JarFile(file).run {
            PluginDescriptionFile(getInputStream(getJarEntry("plugin.yml")))
        }

        override fun getPluginFileFilters(): Array<Pattern> = arrayOf(compile("\\.jar$"))

        override fun loadPlugin(file: File): Plugin {
            TODO("Not yet implemented")
        }

        override fun createRegisteredListeners(
            listener: Listener,
            plugin: Plugin
        ): MutableMap<Class<out Event>, MutableSet<RegisteredListener>> {
            throw UnsupportedOperationException("Not yet implemented!")
        }
    }
}