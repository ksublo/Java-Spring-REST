package blo0021.projectJAVAII.controller;

import blo0021.projectJAVAII.model.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import blo0021.projectJAVAII.service.TaskService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        log.info("Fetching all tasks");
        return taskService.findAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        log.info("Fetching task by id: {}", id);
        return taskService.findTaskById(id)
                .map(task -> {
                    log.info("Found task: {}", task);
                    return ResponseEntity.ok(task);
                })
                .orElseGet(() -> {
                    log.warn("Task not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        log.info("Creating new task: {}", task);
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        log.info("Updating task with id: {}", id);
        return taskService.findTaskById(id)
                .map(task -> {
                    log.info("Updating task details from {} to {}", task, taskDetails);
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setStatus(taskDetails.getStatus());
                    return ResponseEntity.ok(taskService.saveTask(task));
                })
                .orElseGet(() -> {
                    log.warn("Failed to find task with id: {} for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("Attempting to delete task with id: {}", id);
        return taskService.findTaskById(id)
                .map(task -> {
                    taskService.deleteTask(id);
                    log.info("Deleted task with id: {}", id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> {
                    log.warn("Failed to delete task with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }
}
