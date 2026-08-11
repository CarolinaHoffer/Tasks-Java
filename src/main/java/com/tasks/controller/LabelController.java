package com.tasks.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.model.Label;
import com.tasks.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    // Gets

    @GetMapping
    public List<Label> getMyLabels() {
        return labelService.getMyLabels();
    }

    @GetMapping("/{id}")
    public Label getMyLabel(@PathVariable Long id) {
        return labelService.getMyLabel(id);
    }

    // Posts

    @PostMapping
    public Label createLabel(@RequestBody Label label) {
        return labelService.createLabel(label);
    }

    // Updates

    @PatchMapping("/{id}")
    public Label updateLabel(
            @PathVariable Long id,
            @RequestBody Label request) {
        return labelService.updateLabel(id, request);
    }

    // Deletes

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
