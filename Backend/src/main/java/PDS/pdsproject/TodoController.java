package PDS.pdsproject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PDS.pdsproject.*;


@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return todoService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/search")
    public ResponseEntity<List<Todo>> searchTodo(@RequestParam(required = false) String title,
            @RequestParam(required = false) Integer id) {
        if (title == null && id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return todoService.getTodo(title, id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public ResponseEntity<String> addToDo(@RequestBody Todo todo) {
        return todoService.addToDo(todo);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity<String> deleteTodo(@RequestParam(required = false) String title,
            @RequestParam(required = false) Integer id) {
        if (title == null && id == null) {
            return new ResponseEntity<>("Either title or id must be provided.", HttpStatus.BAD_REQUEST);
        }
        return todoService.deleteTodo(title, id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}", produces = "application/json")
    public ResponseEntity<Todo> updateTodo(@PathVariable Integer id, @RequestBody Todo newTodo) {
        return todoService.updateTodo(id, newTodo);
    }
}