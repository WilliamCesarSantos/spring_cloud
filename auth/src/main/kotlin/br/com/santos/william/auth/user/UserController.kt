package br.com.santos.william.auth.user

import br.com.santos.william.auth.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController @Autowired constructor(
    val service: UserService
) {

    @GetMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun list(pageable: Pageable) = service.list(pageable)

    @GetMapping(value = ["/users/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@PathVariable id: Long): User = service.get(id).orElseThrow { ResourceNotFoundException() }

    @PostMapping(value = ["/users"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun insert(@RequestBody user: User) {
        return service.insert(user)
                .let {
                    ResponseEntity.status(HttpStatus.CREATED).body(it)
                }
    }

    @PutMapping(value = ["/users/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
        user.id = id
        return service.update(user)
                .let {
                    ResponseEntity.status(HttpStatus.OK).body(it)
                }
    }

}