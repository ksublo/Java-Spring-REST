package blo0021.projectJAVAII.service;

import blo0021.projectJAVAII.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import blo0021.projectJAVAII.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id){
        return projectRepository.findById(id);
    }

    public Project saveProject(Project project){
        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }


}
