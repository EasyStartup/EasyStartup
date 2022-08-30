package site.easystartup.web.project.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.easystartup.web.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "cover_link")
    private String coverLink;

    @Column(name = "title")
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;

    @Column(name = "description")
    @Size(min = 50, message = "Description should be no less 50 signs")
    @NotNull
    private String description;

    @Column(name = "commercial_status") // 1 - commercial, 0 - non-commercial
    private int commercialStatus;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> technology;

    @ManyToOne
    @JoinColumn(name = "owner_id", updatable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL})
    private List<Participant> participants;

    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @PrePersist
    private void createdDate() {
        this.createdDate = LocalDateTime.now();
    }
}
