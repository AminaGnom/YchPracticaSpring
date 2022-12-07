package com.example.demo.controllers;

import com.example.demo.models.Branch;
import com.example.demo.repo.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/branch")
@PreAuthorize("hasAnyAuthority('CASHIER, ADMIN, USER')")

public class BranchController {
    @Autowired
    private BranchRepository branchRepository;

    @GetMapping("/br")
    public String BranchMain(Model model) {
        Iterable<Branch> branches = branchRepository.findAll();
        model.addAttribute("branch", branches);
        return "branch";
    }

    @GetMapping("/branch/add")
    public String BranchAdd(@ModelAttribute("branch") Branch branch, Model model)
    {

        return "branch-add";
    }

    @PostMapping("/branch/add")
    public String BranchAdd(@ModelAttribute("branch")@Valid Branch branch, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "branch-add";
        }
        branchRepository.save(branch);
        return "redirect:/branch/br";
    }


    @GetMapping("/{id}")
    public String BranchView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Branch> branch = branchRepository.findById(id);
        ArrayList<Branch> res = new ArrayList<>();
        branch.ifPresent(res::add);
        model.addAttribute("branch", res);
        if(!branchRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "branch-view";

    }

    @GetMapping("/{id}/edit")
    public String BranchEdit(@PathVariable("id")long id, Model model)
    {
        Branch branch = branchRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("branch",branch);
        return "branch-view";
    }

    @PostMapping("/{id}/edit")
    public String BranchUpdate(@ModelAttribute("branch")@Valid Branch branch, BindingResult bindingResult,
                              @PathVariable("id") long id) {

        branch.setId(id);
        if (bindingResult.hasErrors()) {
            return "branch-view";
        }
        branchRepository.save(branch);
        return "redirect:/branch/br";
    }

    @PostMapping("/{id}/remove")
    public String BranchDelete(@PathVariable("id") long id, Model model){
        Branch branch = branchRepository.findById(id).orElseThrow();
        branchRepository.delete(branch);
        return "redirect:/branch/br";
    }
}