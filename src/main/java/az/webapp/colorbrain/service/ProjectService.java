package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectEntity> getAllActiveProject() {
        return projectRepository.findAllByActiveTrue();
    }

    public List<ProjectEntity> getAllFinishedProject() {
        return projectRepository.findAllByActiveFalse();
    }

    public void saveProject(ProjectEntity projectEntity) {
        projectEntity.setCreatedAt(LocalDateTime.now());
        projectEntity.setStatus(true);
        projectEntity.setActive(true);
        System.out.println(projectEntity.toString());
        projectRepository.save(projectEntity);
    }

    public void updateProject(ProjectEntity projectEntity) {
        ProjectEntity projectEntityFromDb = getOneProjectById(projectEntity.getId());
        projectEntity.setCreatedAt(projectEntityFromDb.getCreatedAt());
        projectEntity.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(projectEntity);
    }

    public void deleteProject(ProjectEntity projectEntity) {
        projectRepository.delete(projectEntity);
    }

    public ProjectEntity getOneProjectById(Long id) {
        return projectRepository.getOne(id);
    }
}

