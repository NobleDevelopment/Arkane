package org.nobledev.arkane.architecture.feature.pipeline

import org.nobledev.arkane.architecture.feature.attribute.Attributes
import org.nobledev.arkane.architecture.plugin.ArkanePlugin

data class PipelineContext(
    val operatingPlugin : ArkanePlugin,
    val attributeMap : Attributes
) {
}