package org.nobledev.arkane.architecture.feature.pipeline

data class PipelinePhase(
    val name : String,
    val executor : (PipelineContext) -> Unit
) {
    override fun toString(): String = "Pipeline Phase [$name]"
}