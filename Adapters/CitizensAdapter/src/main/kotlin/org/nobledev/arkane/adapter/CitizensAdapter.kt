package org.nobledev.arkane.adapter

import org.nobledev.arkane.architecture.feature.Feature
import org.nobledev.arkane.architecture.feature.pipeline.Pipeline

class CitizensAdapter : Feature("CitizensAdapter", true) {

    override val pipeline: Pipeline = buildPipeline {
    }
}