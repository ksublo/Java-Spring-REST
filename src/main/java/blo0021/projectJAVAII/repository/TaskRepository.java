package blo0021.projectJAVAII.repository;

import blo0021.projectJAVAII.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
