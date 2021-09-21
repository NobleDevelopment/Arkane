package org.nobledev.arkane.architecture.event

import org.bukkit.event.Event as BukkitEvent

import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.RegisteredListener
import org.nobledev.arkane.architecture.event.Event
import org.nobledev.arkane.architecture.management.StubPlugin
import java.lang.reflect.Method
import java.util.IdentityHashMap
import kotlin.reflect.KClass

private val STUB_PLUGIN = StubPlugin("Arkane Events")
private val LISTENER_LISTS = IdentityHashMap<KClass<out BukkitEvent>, ListenerList>()



private class ListenerList(val type : KClass<out BukkitEvent>) : TreeEvent<(Any) -> (Unit)>(), Listener, EventExecutor {

    fun findHandler(type : Class<*>) : Method? {
        if(type == BukkitEvent::class.java) return null

        return type.declaredMethods.find {
            it.name == "getHandlerList"
        } ?: findHandler(type.superclass)
    }

    init {
        val handler = findHandler(type.java)?.invoke(null) as HandlerList
        handler.register(RegisteredListener(this, this, EventPriority.NORMAL, STUB_PLUGIN, false))
    }

    override fun execute(listener: Listener, event: BukkitEvent) {
        if(type.java.isAssignableFrom(event.javaClass)) {
            forEach { it(event) }
        }
    }
}

fun <Type : BukkitEvent> listen(type: KClass<Type>) = LISTENER_LISTS.computeIfAbsent(type, ::ListenerList) as Event<(Type).() -> (Unit)>

inline fun <reified Type : BukkitEvent> listen(noinline listener : (Type).() -> (Unit)) = listen(Type::class)(listener)