package com.example.demo.controllers;

import com.example.demo.models.Branch;
import com.example.demo.models.Shoes;
import com.example.demo.repo.BranchRepository;
import com.example.demo.repo.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AvailabilityController {
    @Autowired
    private ShoesRepository shoesRepository;
    @Autowired
    private BranchRepository branchRepository;

    @GetMapping("/availability")
    private String Main(Model model){
        Iterable<Shoes> shoes = shoesRepository.findAll();
        model.addAttribute("shoes", shoes);
        Iterable<Branch> branches = branchRepository.findAll();
        model.addAttribute("branches", branches);
        return "availability";
    }

    @PostMapping("/availability/add")
    public String blogPostAdd(@RequestParam Long shoes, @RequestParam Long branch, Model model)
    {
        Shoes shoes2 = shoesRepository.findById(shoes).orElseThrow();
        Branch branch2 = branchRepository.findById(branch).orElseThrow();
        shoes2.getBranches().add(branch2);
        shoesRepository.save(shoes2);
        return "redirect:/availability";
    }

    @PostMapping("branch/delShoes/{id_shoes}/{id_branch}")
    public String blogPostDell(@PathVariable Long id_branch, @PathVariable Long id_shoes, Model model)
    {
        Shoes shoes2 = shoesRepository.findById(id_shoes).orElseThrow();
        Branch branch2 = branchRepository.findById(id_branch).orElseThrow();
        shoes2.getBranches().remove(branch2);
        shoesRepository.save(shoes2);
        return "redirect:/availability";
    }
}

/*    @PostMapping("/branch")
    public String shopPostAdd(@ModelAttribute("branch") @Valid Branch branch, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "branch-add";
        }
        branchRepository.save(branch);
        return "redirect:/branch";
    }
    @GetMapping("/branch")
    public String shopMain(Model model)
    {
        Iterable<Branch> branches = branchRepository.findAll();
        model.addAttribute("branch", branches);
        return "branch";
    }
    @GetMapping("/branch-add")
    public String newShop(Branch branch,Model model)
    {
        return "branch-add";
    }*/

