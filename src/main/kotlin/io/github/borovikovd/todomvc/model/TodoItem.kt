package io.github.borovikovd.todomvc.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class TodoItem(
    @Id var id: Long = 0,
    var title: String,
    var completed: Boolean
)
