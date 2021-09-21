package org.nobledev.arkane.architecture.component

/**
 * State used to determine activity for [Component]s
 */
sealed class ComponentState {
    /**
     * Enabled [Component] State
     *
     * @constructor Create empty Enabled
     */
    class Enabled : ComponentState()

    /**
     * Disabled [Component] State
     *
     * @constructor Create empty Disabled
     */
    class Disabled : ComponentState()
}
