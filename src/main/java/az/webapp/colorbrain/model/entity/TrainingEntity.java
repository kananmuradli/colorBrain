package az.webapp.colorbrain.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "training")
@Getter
@Setter
@ToString
public class TrainingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Təlimin adını daxil edin.")
    @Column(name = "header")
    private String header;

    @NotBlank(message = "Təlim haqqında məlumat daxil edin.")
    @Column(name = "context")
    private String context;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "image_cover")
    private String imageCover;

    @NotNull(message = "Təlimin son qeydiyyat tarixini daxil edin.")
    @Column(name = "last_registration_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastRegistrationDay;

    @NotNull(message = "Təlimin başlayacağı tarixi daxil edin.")
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Təlimin başlayacağı saatı daxil edin.")
    @Column(name = "start_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotBlank(message = "Təlimçi (təlimçilərin) adını daxil edin.")
    @Column(name = "trainer_name")
    private String trainerName;

    @Pattern(regexp = "[0-9]+", message = "Məbləği ancaq rəqəmlə ifadə edin.")
    @Column(name = "training_price")
    private String trainingPrice;

    @Column(name = "registration_is_active")
    private boolean registrationIsActive;

    @Column(name = "status")
    private boolean status;

    @Column(name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_id")
    private List<FileEntity> fileEntities;

    @OneToMany(mappedBy = "trainingEntity", cascade = CascadeType.ALL)
    private List<ParticipantEntity> participantEntities;

}
