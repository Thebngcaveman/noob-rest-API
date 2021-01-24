package com.codefury.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController //make this class => response,JSON
public class TodoController {
    private List<Todo> todos = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();


    @GetMapping("/todo")
    public List<Todo> getTodos(){
        return todos;
    }

    @GetMapping("/todo/{id}")
    public Todo getTodosById(@PathVariable long id){
        return todos.stream().filter(result -> result.getId() == id)
                .findFirst().orElseThrow(() -> new TodoNotFoundException(id));
    }

    @GetMapping("/todo/search") //RequestParam need to insert param first
    public String getTodosByName(@RequestParam(defaultValue = "Dame Dane") String name){
        return "search: "+ name;
    }

    //same path but different HTTP method = fine
    @PostMapping("/todo")
    public void addTodo(@RequestBody Todo todo){
        todos.add(new Todo(counter.getAndIncrement(), todo.getName()));
    }

    @ResponseStatus(HttpStatus.CREATED) //check if success response 201 created
    @PutMapping("/todo/{id}")
    public void editTodo(@RequestBody Todo todo, @PathVariable long id){
        todos.stream().filter(result -> result.getId() == id)
                .findFirst()
                .ifPresentOrElse(result -> {
                    result.setName(todo.getName()); //if found set new name;
                }, () -> {
                    throw new TodoNotFoundException(id);
                });
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable long id){
        todos.stream().filter(result -> result.getId() == id)
                .findFirst()
                .ifPresentOrElse(result -> {
                    todos.remove(result);
                }, () -> {
                    throw new TodoNotFoundException(id);
                });
    }
}

// GET POST PUT how do we deal with these requests





