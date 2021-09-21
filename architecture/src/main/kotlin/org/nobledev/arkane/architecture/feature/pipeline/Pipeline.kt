package org.nobledev.arkane.architecture.feature.pipeline

/**
 * Pipeline system to allow a Feature to build the required environment for operation.
 */
class Pipeline {
    private var phases = ArrayDeque<PipelinePhase>()

    /**
     * Builds a phase of operation into the pipeline.
     *
     * @param name of the phase
     * @param exec function invoked on run
     */
    fun buildPhase(name : String, exec : (PipelineContext) -> Unit) {
        phases.add(PipelinePhase(name, exec))
    }

    /**
     * Executes pipeline with a given context.
     *
     * @param context
     */
    fun executePipeline(context : PipelineContext) {
        while (phases.isNotEmpty()) {
            phases.removeFirst().executor.invoke(context)
        }
    }
}