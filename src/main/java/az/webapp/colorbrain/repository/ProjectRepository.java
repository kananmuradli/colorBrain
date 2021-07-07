package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAllByActiveTrue();

    List<ProjectEntity> findAllByActiveFalse();
}
