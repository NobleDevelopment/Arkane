package org.nobledev.arkane.architecture.event

import java.util.TreeMap

typealias Event<Listener> = (Listener) -> (Unit)

open class TreeEvent<Listener> : Event<Listener> {

    var iterating = false

    val listeners = TreeMap<Int, Listener>()
    val changes = ArrayList<Pair<Int, Listener?>>()
    var index = 0

    inline fun forEach(block : (Listener) -> (Unit)) {
        iterating = true
        listeners.values.forEach(block)
        iterating = false
        changes.removeIf { (current, listener) ->
            if( listener == null) listeners -= current
            else listeners[current] = listener
            true
        }

    }
    override fun invoke(listener: Listener) {
        val current = index++
        if(iterating) changes.add(current to listener)
        else listeners[current] = listener
    }
}