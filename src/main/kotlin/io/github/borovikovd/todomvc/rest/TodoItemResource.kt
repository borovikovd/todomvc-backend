package io.github.borovikovd.todomvc.rest

import io.github.borovikovd.todomvc.model.TodoItem
import io.github.borovikovd.todomvc.repository.TodoItemRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid
import javax.validation.constraints.NotBlank

data class CreateTodoItemRequest(@field:NotBlank val title: String)
data class TodoItemView(val id: Long, val title: String, val completed: Boolean)
data class UpdateTodoItemRequest(@field:NotBlank val title: String?, val completed: Boolean?)

@RestController
class TodoItemResource(val todoItemRepository: TodoItemRepository) {
    @GetMapping("/")
    fun listItems(): Flux<TodoItemView> {
        return todoItemRepository.findAll().map { item ->
            TodoItemView(id = item.id, title = item.title, completed = item.completed)
        }
    }

    @PostMapping("/")
    fun createItem(@Valid @RequestBody request: CreateTodoItemRequest): Mono<TodoItem> {
        val todoItem = TodoItem(title = request.title ?: "", completed = false)
        return todoItemRepository.save(todoItem)
    }

    @PatchMapping("/{todoItemId}")
    fun updateItem(@PathVariable todoItemId: Long, @Valid @RequestBody request: UpdateTodoItemRequest): Mono<TodoItem> {
        return todoItemRepository.findById(todoItemId).flatMap { item: TodoItem ->
            if (request.title != null) {
                item.title = request.title
            }
            if (request.completed != null) {
                item.completed = request.completed
            }
            todoItemRepository.save(item)
        }
    }
}
