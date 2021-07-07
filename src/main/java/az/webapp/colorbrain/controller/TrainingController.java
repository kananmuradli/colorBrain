package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @GetMapping("/active")
    public String getAllActiveTraining(Model model) {
        model.addAttribute("trainings", trainingService.getAllActiveTraining());
        return "admin/allTrainingPage";
    }

    @GetMapping("/finished")
    public String getAllFinishedTraining(Model model) {
        model.addAttribute("trainings", trainingService.getAllFinishedTraining());
        return "admin/allTrainingPage";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByTrainingId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", trainingService.getAllFilesByTrainingId(id));
        return "admin/allFilesPage";
    }

    @GetMapping("/{id}")
    public String getOneTrainingById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("trainingEntity", trainingService.getOneTrainingById(id));
        return "admin/oneTrainingPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("trainingEntity", new TrainingEntity());
        return "admin/createTrainingPage";
    }

    @PostMapping("/create")
    public String saveTraining(
            @Valid @ModelAttribute("trainingEntity") TrainingEntity trainingEntity,
            @NotNull @RequestParam("coverImage") MultipartFile file,
            @NotNull @RequestParam("files") List<MultipartFile> files,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createTrainingPage";
        }
        trainingService.saveTraining(trainingEntity, file, files);
        return "redirect:/training/active";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByTrainingId(
            @PathVariable("id") TrainingEntity trainingEntity,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        trainingService.saveAdditionalTrainingFiles(files, trainingEntity);
        return "redirect:/training/" + trainingEntity.getId() + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByTrainingId(
            @RequestParam("fileId") FileEntity fileEntity,
            @PathVariable("id") TrainingEntity trainingEntity
    ) {
        trainingService.deleteFileByTrainingId(fileEntity);
        return "redirect:/training/" + trainingEntity.getId() + "/files";
    }

    @PostMapping("/delete")
    public String deleteTraining(TrainingEntity trainingEntity) {
        trainingService.deleteTraining(trainingEntity);
        return "redirect:/training/active";
    }

    @PostMapping("/update")
    public String updateTraining(
            @Valid @ModelAttribute("trainingEntity") TrainingEntity trainingEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneTrainingPage";
        }
        trainingService.updateTraining(trainingEntity);
        return "redirect:/training/active";
    }

}
