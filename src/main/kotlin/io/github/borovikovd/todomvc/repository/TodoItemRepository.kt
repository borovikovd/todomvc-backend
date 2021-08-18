package io.github.borovikovd.todomvc.repository

import io.github.borovikovd.todomvc.model.TodoItem
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TodoItemRepository : ReactiveCrudRepository<TodoItem, Long>
