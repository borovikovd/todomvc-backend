package io.github.borovikovd.todomvc.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table
data class TodoItem(
    @Id var id: Long = 0,
    var title: String,
    var completed: Boolean = false,
    @Column("ord") var order: Int = -1
)
