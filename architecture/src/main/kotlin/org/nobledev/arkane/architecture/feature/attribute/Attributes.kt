package org.nobledev.arkane.architecture.feature.attribute

import org.bukkit.configuration.ConfigurationSection
import java.util.concurrent.ConcurrentHashMap

/**
 * Specifies a key for an attribute in [Attributes]
 * @param T is type of the value stored in the attribute
 * @property name is a name of the attribute for diagnostic purposes
 */
class AttributeKey<T : Any>(val name : String, private val adapter : ConfigAdapter<T>, val default : T) {
    override fun toString(): String = if(name.isEmpty()) super.toString() else "AttributeKey: $name"

    fun adapt(section : ConfigurationSection) : T {
        return adapter.invoke(name, section)
    }
}

/**
 * Map of attributes accessible by [AttributeKey] in a typed manner
 */
interface Attributes {
    /**
     * Gets a value of the attribute for the specified [key], or throws an exception if an attribute doesn't exist
     */
    operator fun <T : Any> get(key : AttributeKey<T>) : T =
        getOrNull(key) ?: throw IllegalStateException("No instance for key $key")

    /**
     * Gets a value of the attribute for the specified [key], or return null if an attribute doesn't exist
     */
    fun <T : Any> getOrNull(key : AttributeKey<T>) : T?

    /**
     * Checks if an attribute with the specified [key] exists
     */
    operator fun contains(key : AttributeKey<*>) : Boolean

    /**
     * Creates or changes an attribute with the specified [key] using [value]
     */
    fun <T : Any> put(key : AttributeKey<T>, value : T)

    /**
     * Removes an attribute with the specified [key]
     */
    fun <T : Any> remove(key : AttributeKey<T>)

    /**
     * Removes an attribute with the specified [key] and returns its current value,
     * throws an exception if attribute doesn't exist
     */
    fun <T : Any> take(key : AttributeKey<T>) : T = get(key).also { remove(key) }

    /**
     * Removes an attribute with the specified [key] and returns its current value,
     * returns null if attribute doesn't exist
     */
    fun <T : Any> takeOrNull(key : AttributeKey<T>) :T? = getOrNull(key).also { remove(key) }

    /**
     * Gets a value of the attribute for the specified [key], or calls supplied [block] to compute its value
     */
    fun <T : Any> computeIfAbset(key : AttributeKey<T>, block: () -> T) : T

    /**
     * Returns [List] of all [AttributeKey] instances in this map
     */
    val allKeys : List<AttributeKey<*>>
}

/**
 * Adds all attributes from another collection, replacing original values if any
 */
fun Attributes.putAll(other : Attributes) {
    other.allKeys.forEach {
        @Suppress("UNCHECKED_CAST")
        put(it as AttributeKey<Any>, other[it])
    }
}

private abstract class AttributesBase : Attributes {
    protected abstract val map : MutableMap<AttributeKey<*>, Any?>

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getOrNull(key: AttributeKey<T>): T? = map[key] as T?

    override fun contains(key: AttributeKey<*>): Boolean = map.containsKey(key)

    override fun <T : Any> put(key: AttributeKey<T>, value: T) {
        map[key] = value
    }

    override fun <T : Any> remove(key: AttributeKey<T>) {
        map.remove(key)
    }

    override val allKeys: List<AttributeKey<*>>
        get() = map.keys.toList()
}

private class ConcurrentSafeAttributes : AttributesBase() {
    override val map: MutableMap<AttributeKey<*>, Any?> = ConcurrentHashMap()

    override fun <T : Any> computeIfAbset(key: AttributeKey<T>, block: () -> T): T {
        @Suppress("UNCHECKED_CAST")
        map[key]?.let { return it as T }
        val result = block.invoke()
        @Suppress("UNCHECKED_CAST")
        return (map.putIfAbsent(key, result) ?: result) as T
    }
}

private class HashMapAttributes : AttributesBase() {
    override val map: MutableMap<AttributeKey<*>, Any?> = HashMap()

    override fun <T : Any> computeIfAbset(key: AttributeKey<T>, block: () -> T): T {
        @Suppress("UNCHECKED_CAST")
        map[key]?.let { return it as T }
        val result = block.invoke()
        @Suppress("UNCHECKED_CAST")
        return (map.put(key, result) ?: result) as T
    }
}

fun Attributes(concurrent : Boolean) : Attributes =
    if(concurrent) ConcurrentSafeAttributes() else HashMapAttributes()