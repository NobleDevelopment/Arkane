package org.nobledev.arkane.architecture.feature.attribute

import org.bukkit.Color
import org.bukkit.configuration.ConfigurationSection


typealias ConfigAdapter<T> = (key : String, config : ConfigurationSection) -> T
object ConfigAdapters {
    val String : ConfigAdapter<String> = {key, conf ->
        conf.getString(key) ?: ""
    }

    val Int : ConfigAdapter<Int> = {key, conf ->
        conf.getInt(key)
    }

    val Double : ConfigAdapter<Double> = { key, conf ->
        conf.getDouble(key)
    }

    val Long : ConfigAdapter<Long> = {key, conf ->
        conf.getLong(key)
    }

    val Boolean : ConfigAdapter<Boolean> = {key, conf ->
        conf.getBoolean(key)
    }

    val Color : ConfigAdapter<Color> = {key, conf ->
        conf.getColor(key) ?: org.bukkit.Color.WHITE
    }

    val StringList : ConfigAdapter<List<String>> = {key, conf ->
        conf.getStringList(key)
    }

}
