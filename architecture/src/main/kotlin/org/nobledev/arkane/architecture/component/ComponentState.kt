package org.nobledev.arkane.architecture.component

sealed class ComponentState {
    class Enabled : ComponentState()
    class Disabled : ComponentState()
}
