package br.com.santos.william.crud.task

import br.com.santos.william.crud.exception.ConflictException
import br.com.santos.william.crud.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.transaction.Transactional

@Service
class TaskService @Autowired constructor(private val repository: TaskRepository) {

    fun list(pageable: Pageable) = repository.findAll(pageable)

    fun get(id: Long) = repository.findById(id)

    @Transactional
    fun insert(@Validated task: Task): Task {
        task.id?.let { throw ConflictException() }
        return repository.save(task)
    }

    @Transactional
    fun update(@Validated task: Task): Task {
        checkTaskHasFinish(task.id!!)
        updateCloseAt(task);
        return repository.save(task)
    }

    @Transactional
    fun finishTask(id: Long): Task {
        return checkTaskHasFinish(id)
                .let {
                    it.status = TaskStatus.FINISHED
                    update(it)
                }
    }

    @Transactional
    fun delete(id: Long): Task {
        return get(id).orElseThrow { ResourceNotFoundException() }
                .let {
                    repository.delete(it)
                    return it
                }
    }

    private fun updateCloseAt(task: Task) {
        with(task, {
            if (status == TaskStatus.FINISHED && closedAt == null) {
                closedAt = LocalDateTime.now(ZoneOffset.UTC)
            }
        })
    }

    private fun checkTaskHasFinish(id: Long): Task {
        val task = get(id).orElseThrow { ResourceNotFoundException() }
        if (task.closedAt != null && task.status == TaskStatus.FINISHED) {
            throw ConflictException("Not allowed change a finished task")
        }
        return task;
    }

}