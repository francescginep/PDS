package PDS.pdsproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import PDS.pdsproject.*;


@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodoDao todoDao;

    public TodoService(TodoDao todoRepository) {
        this.todoDao = todoRepository;
    }

    public ResponseEntity<List<Todo>> findAll() {
        try {
            return new ResponseEntity<>(todoDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error finding Todos: ", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addToDo(Todo todo) {
        try {
            todoDao.save(todo);
            return new ResponseEntity<>("Todo- added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding Todo: ", e);
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Todo>> getTodo(String title, Integer id) {
        try {
            if (title != null) {
                return new ResponseEntity<>(todoDao.findByTitle(title), HttpStatus.OK);
            } else if (id != null) {
                Optional<Todo> result = todoDao.findById(id);
                if (result.isPresent()) {
                    return new ResponseEntity<>(Collections.singletonList(result.get()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error fetching Todo: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteTodo(String title, Integer id) {
        try {
            if (title != null) {
                todoDao.deleteByTitle(title);
                return new ResponseEntity<>("Todo- with title '" + title + "' deleted successfully.",
                        HttpStatus.OK);
            } else if (id != null) {
                todoDao.deleteById(id);
                return new ResponseEntity<>("Todo- with ID '" + id + "' deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error deleting Todo: ", e);
            return new ResponseEntity<>("Error deleting Todo.", HttpStatus.BAD_REQUEST);
        }
    }

    /* public ResponseEntity<Todo> updateTodo(Integer id, String title) {
        Optional<Todo> todo = todoDao.findById(id);
        if (todo.isPresent()) {
            Todo existing = todo.get();
            existing.setTitle(title);
            todoDao.save(existing);
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } */

    public ResponseEntity<Todo> updateTodo(Integer id, Todo newTodo) {
        Optional<Todo> todo = todoDao.findById(id);
        boolean isTitle = newTodo.getTitle() != null;
        if (todo.isPresent()) {
            Todo existing = todo.get();
            if (isTitle) {
                existing.setTitle(newTodo.getTitle());
            }
            existing.setChecked(newTodo.isChecked());
            todoDao.save(existing);
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}