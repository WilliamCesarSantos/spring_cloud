package br.com.santos.william.crud.task

import br.com.santos.william.crud.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TaskController @Autowired constructor(val service: TaskService) {

    @GetMapping(value = ["/tasks"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun list(pageable: Pageable) = service.list(pageable)

    @GetMapping(value = ["/tasks/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@PathVariable id: Long): Task = service.get(id).orElseThrow { ResourceNotFoundException() }

    @PostMapping(value = ["/tasks"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun insert(@RequestBody task: Task) {
        return service.insert(task)
                .let {
                    ResponseEntity.status(HttpStatus.CREATED).body(it)
                }
    }

    @PutMapping(value = ["/tasks/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@PathVariable id: Long, @RequestBody task: Task): ResponseEntity<Task> {
        task.id = id
        return service.update(task)
                .let {
                    ResponseEntity.status(HttpStatus.OK).body(it)
                }
    }

    @PutMapping(value = ["/tasks/{id}/finish"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun finishTask(@PathVariable id: Long): ResponseEntity<Task> {
        return service.finishTask(id)
                .let {
                    ResponseEntity.status(HttpStatus.OK).body(it)
                }
    }

    @DeleteMapping(value = ["/tasks/{id}"])
    fun delete(@PathVariable id: Long): ResponseEntity<Task> {
        return service.delete(id).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }

    }
}