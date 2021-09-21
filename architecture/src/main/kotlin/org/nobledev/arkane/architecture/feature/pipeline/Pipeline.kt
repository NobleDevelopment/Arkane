package org.nobledev.arkane.architecture.feature.pipeline


//TODO use proper queue here
class Pipeline() {
    private var phases = ArrayDeque<PipelinePhase>()

    fun buildPhase(name : String, exec : (PipelineContext) -> Unit) {
        phases.add(PipelinePhase(name, exec))
    }

    fun executePipeline(context : PipelineContext) {
        while (phases.isNotEmpty()) {
            phases.removeFirst().executor.invoke(context)
        }
    }
}