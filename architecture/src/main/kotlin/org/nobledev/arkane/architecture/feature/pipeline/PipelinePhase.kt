package org.nobledev.arkane.architecture.feature.pipeline

/**
 * Pipeline phase for building environments for features to operate in.
 *
 * @property name
 * @property executor
 */
data class PipelinePhase(
    val name : String,
    val executor : (PipelineContext) -> Unit
) {
    override fun toString(): String = "Pipeline Phase [$name]"
}