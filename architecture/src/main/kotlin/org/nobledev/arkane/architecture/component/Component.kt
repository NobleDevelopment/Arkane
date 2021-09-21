package org.nobledev.arkane.architecture.component

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.bukkit.event.Event
import org.koin.core.component.KoinComponent

class Component : KoinComponent {
    private val _state = MutableStateFlow<ComponentState>(ComponentState.Disabled())

    val scope = CoroutineScope(SupervisorJob())

    val state : ComponentState
        get() = _state.value



    fun onEnable(action : () -> Unit) {
        _state.onEach {
            if(it is ComponentState.Enabled) {
                action.invoke()
            }
        }
    }

    fun onDisable(action : () -> Unit) {
        _state.onEach {
            if(it is ComponentState.Disabled) {
                action.invoke()
            }
        }
    }

    fun enable() {
        if(_state.value is ComponentState.Enabled) return
        scope.launch {
            _state.emit(ComponentState.Enabled())
        }

    }

    fun disable() {
        if(_state.value is ComponentState.Disabled) return
        scope.launch {
            _state.emit(ComponentState.Disabled())
        }
    }

}

fun Component(builder : Component.() -> Unit) = Component().apply(builder)