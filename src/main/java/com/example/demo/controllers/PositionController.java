package com.example.demo.controllers;


import com.example.demo.models.Position;
import com.example.demo.models.Staff;
import com.example.demo.repo.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequestMapping("/position")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping("/pst")
    public String PositionMain(Model model)
    {
        Iterable<Position> position = positionRepository.findAll();
        model.addAttribute("position", position);
        return "position-main";
    }

    @GetMapping("/add")
    public String PositionAdd(@ModelAttribute("position") Position position)
    {
        return "position-add";
    }


    @PostMapping("/add")
    public String PositionAdd(@ModelAttribute("position")@Valid Position position, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "position-add";
        }
        positionRepository.save(position);
        return "redirect:/position/pst";
    }

    @GetMapping("/{id}")
    public String PositionView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Position> position = positionRepository.findById(id);
        ArrayList<Position> res = new ArrayList<>();
        position.ifPresent(res::add);
        model.addAttribute("position", res);
        if(!positionRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "position-view";

    }

    @GetMapping("/{id}/edit")
    public String PositionEdit(@PathVariable("id")long id, Model model)
    {
        Position position = positionRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("position",position);
        return "position-view";
    }

    @PostMapping("/{id}/edit")
    public String PositionUpdate(@ModelAttribute("position")@Valid Position position, BindingResult bindingResult,
                              @PathVariable("id") long id) {

        position.setId(id);
        if (bindingResult.hasErrors()) {
            return "position-view";
        }
        positionRepository.save(position);
        return "redirect:/position/pst";
    }


    @PostMapping("/{id}/remove")
    public String PositionDelete(@PathVariable("id") long id, Model model){
        Position position = positionRepository.findById(id).orElseThrow();
        positionRepository.delete(position);
        return "redirect:/position/pst";
    }



}