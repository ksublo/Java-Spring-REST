package blo0021.projectJAVAII.repository;

import blo0021.projectJAVAII.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
