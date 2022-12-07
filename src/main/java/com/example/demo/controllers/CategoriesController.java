package com.example.demo.controllers;

import com.example.demo.models.Categories;
import com.example.demo.repo.CategoriesRepository;
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
@RequestMapping("/categories")
@PreAuthorize("hasAnyAuthority('CASHIER')")
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping("/cgt")
    public String categoriesMain(Model model)
    {
        Iterable<Categories> categories = categoriesRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories-main";
    }

    @GetMapping("/add")
    public String categoriesAdd(@ModelAttribute("categories") Categories categories)
    {
        return "categories-add";
    }


    @PostMapping("/add")
    public String categoriesAdd(@ModelAttribute("categories")@Valid Categories categories, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "categories-add";
        }
        categoriesRepository.save(categories);
        return "redirect:/categories/cgt";
    }

    @GetMapping("/{id}")
    public String categoriesView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Categories> categories = categoriesRepository.findById(id);
        ArrayList<Categories> res = new ArrayList<>();
        categories.ifPresent(res::add);
        model.addAttribute("categories", res);
        if(!categoriesRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "categories-view";

    }

    @GetMapping("/{id}/edit")
    public String categoriesEdit(@PathVariable("id")long id, Model model)
    {
        Categories categories = categoriesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("categories",categories);
        return "categories-view";
    }

    @PostMapping("/{id}/edit")
    public String categoriesUpdate(@ModelAttribute("categories")@Valid Categories categories, BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        categories.setId(id);
        if (bindingResult.hasErrors()) {
            return "categories-view";
        }
        categoriesRepository.save(categories);
        return "redirect:/categories/cgt";
    }


    @PostMapping("/{id}/remove")
    public String categoriesDelete(@PathVariable("id") long id, Model model){
        Categories categories= categoriesRepository.findById(id).orElseThrow();
        categoriesRepository.delete(categories);
        return "redirect:/categories/cgt";
    }



}


