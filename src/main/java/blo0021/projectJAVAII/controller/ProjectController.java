package blo0021.projectJAVAII.controller;

import blo0021.projectJAVAII.model.Project;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import blo0021.projectJAVAII.service.ProjectService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects(){
        log.info("Fetching all projects");
        return projectService.findAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        log.info("Fetching project by id: {}", id);
        return projectService.findProjectById(id)
                .map(project -> {
                    log.info("Found project: {}", project);
                    return ResponseEntity.ok(project);
                })
                .orElseGet(() -> {
                    log.warn("Failed to find project with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        log.info("Creating new project: {}", project);
        return projectService.saveProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        log.info("Updating project with id: {}", id);
        return projectService.findProjectById(id)
                .map(project -> {
                    log.info("Updating project details from {} to {}", project, projectDetails);
                    project.setName(projectDetails.getName());
                    project.setDescription(projectDetails.getDescription());
                    return ResponseEntity.ok(projectService.saveProject(project));
                })
                .orElseGet(() -> {
                    log.warn("Failed to find project with id: {} for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        log.info("Attempting to delete project with id: {}", id);
        return projectService.findProjectById(id)
                .map(project -> {
                    projectService.deleteProject(id);
                    log.info("Deleted project with id: {}", id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> {
                    log.warn("Failed to delete project with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

}

