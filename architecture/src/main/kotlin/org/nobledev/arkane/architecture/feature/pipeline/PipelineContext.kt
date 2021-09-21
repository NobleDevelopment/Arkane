package org.nobledev.arkane.architecture.feature.pipeline

import org.nobledev.arkane.architecture.feature.attribute.Attributes
import org.nobledev.arkane.architecture.plugin.ArkanePlugin

/**
 * Context aware property for building features.
 *
 * @property operatingPlugin the plugin that is installed the feature
 * @property attributeMap properties of the feature.
 */
data class PipelineContext(
    val operatingPlugin: ArkanePlugin,
    val attributeMap: Attributes
) {
}