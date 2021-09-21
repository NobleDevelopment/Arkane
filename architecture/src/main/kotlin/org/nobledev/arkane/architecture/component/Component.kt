package org.nobledev.arkane.architecture.component

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.bukkit.event.Event
import org.koin.core.component.KoinComponent

/**
 * Components are used for systems that should be enabled and disabled during a single plugins lifecycle.
 */
class Component : KoinComponent {
    private val _state = MutableStateFlow<ComponentState>(ComponentState.Disabled())

    val scope = CoroutineScope(SupervisorJob())

    val state : ComponentState
        get() = _state.value

    /**
     * Registers a handler that is called when component state is set to [ComponentState.Enabled]
     *
     * @param action that will be invoked.
     */
    fun onEnable(action : () -> Unit) {
        _state.onEach {
            if(it is ComponentState.Enabled) {
                action.invoke()
            }
        }
    }

    /**
     * Registers a handler that is called when component state is set to [ComponentState.Disabled]
     *
     * @param action that will be invoked.
     */
    fun onDisable(action : () -> Unit) {
        _state.onEach {
            if(it is ComponentState.Disabled) {
                action.invoke()
            }
        }
    }

    /**
     * Sets the state to [ComponentState.Enabled]
     *
     */
    fun enable() {
        if(_state.value is ComponentState.Enabled) return
        scope.launch {
            _state.emit(ComponentState.Enabled())
        }

    }

    /**
     * Sets the state to [ComponentState.Enabled]
     *
     */
    fun disable() {
        if(_state.value is ComponentState.Disabled) return
        scope.launch {
            _state.emit(ComponentState.Disabled())
        }
    }

}

/**
 * Component builder DSL
 */
fun Component(builder : Component.() -> Unit) = Component().apply(builder)