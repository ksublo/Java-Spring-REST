package blo0021.projectJAVAII.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    @JsonIgnoreProperties("tasks")
    private Person assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("tasks")
    private Project project;
}
