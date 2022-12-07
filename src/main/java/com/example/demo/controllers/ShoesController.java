package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.ArticleRepository;
import com.example.demo.repo.CategoriesRepository;
import com.example.demo.repo.ProviderRepository;
import com.example.demo.repo.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shoes")
@PreAuthorize("hasAnyAuthority('CASHIER, USER')")
public class ShoesController {
    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private CategoriesRepository categoriesRepository;


    @GetMapping("/shs")
    public String blogMain(Model model)
    {
        Iterable<Article> articles = articleRepository.findAll();
        Iterable<Provider> providers=providerRepository.findAll();
        Iterable<Shoes> shoes = shoesRepository.findAll();
        Iterable<Categories> categories = categoriesRepository.findAll();
        model.addAttribute("articles", articles);
        model.addAttribute("shoes", shoes);
        model.addAttribute("providers", providers);
        model.addAttribute("categories", categories);
        return "shoes-main";
    }

    @GetMapping("/add")
    public String ShoesAdd(Shoes shoes, Model model)
    {

        Iterable<Article> articles=articleRepository.findAll();
        Iterable<Provider> providers=providerRepository.findAll();
        Iterable<Categories> categories = categoriesRepository.findAll();
        ArrayList<Article> articleArrayList=new ArrayList<>();
        ArrayList<Provider> providerArrayList=new ArrayList<>();
        ArrayList<Categories> categoriesArrayList =new ArrayList<>();
        for (Provider sub: providers){
            if (sub.getOrganization()==null){
                providerArrayList.add(sub);
            }
        }

        for (Article sub: articles){
            if (sub.getNumber()==null){
                articleArrayList.add(sub);
            }
        }

        for (Categories sub: categories){
            if (sub.getType()==null){
                categoriesArrayList.add(sub);
            }
        }

        model.addAttribute("shoes", shoes);
        model.addAttribute("providers", providers);
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);

        return "shoes-add";
    }

    @PostMapping("/add")
    public String ShoesAdd(@ModelAttribute("shoes") @Valid Shoes shoes, BindingResult bindingResult, @RequestParam String organization, String number, String type, Model model)
    {
        shoes.setProvider(providerRepository.findByOrganization(organization));
        shoes.setArticle(articleRepository.findByNumber(number));
        shoes.setCategories(categoriesRepository.findByType(type));
        if(bindingResult.hasErrors())
        {
            Iterable<Article> articles=articleRepository.findAll();
            Iterable<Provider> providers=providerRepository.findAll();
            Iterable<Categories> categories=categoriesRepository.findAll();

            model.addAttribute("providers", providers);
            model.addAttribute("articles", articles);
            model.addAttribute("categories", categories);

            return "shoes-add";
        }
        shoesRepository.save(shoes);
        return "redirect:/shoes/shs";
    }

    @GetMapping("/filter")
    public String shoesFilter(Model model)
    {
        return "shoes-filter";
    }

    @PostMapping("/filter/result")
    public String shoesResult(@RequestParam String brand, Model model)
    {

        List<Shoes> result = shoesRepository.findByBrandContains(brand);

        model.addAttribute("result", result);
        return "shoes-filter";
    }

    @GetMapping("/{id}")
    public String shoesView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Shoes> shoes = shoesRepository.findById(id);
        ArrayList<Shoes> res = new ArrayList<>();

        Iterable<Article> articles=articleRepository.findAll();
        Iterable<Provider> providers=providerRepository.findAll();
        Iterable<Categories> categories=categoriesRepository.findAll();

        model.addAttribute("providers", providers);
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);

        shoes.ifPresent(res::add);
        model.addAttribute("shoes", res);
        if(!shoesRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "shoes-view";

    }

    @GetMapping("/{id}/edit")
    public String shoesEdit(@PathVariable("id")long id,
                              Model model)
    {
        Iterable<Article> articles=articleRepository.findAll();
        Iterable<Provider> providers=providerRepository.findAll();
        Iterable<Categories> categories=categoriesRepository.findAll();
        Shoes shoes = shoesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("providers", providers);
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("shoes", shoes);
        return "shoes-view";
    }

    @PostMapping("/{id}/edit")
    public String shoesUpdate(@ModelAttribute("shoes")@Valid Shoes shoes, BindingResult bindingResult,  @RequestParam String organization, @RequestParam String number, @RequestParam String type, @PathVariable("id") long id) {

      shoes.setId(id);
        if (bindingResult.hasErrors()) {
            Iterable<Article> articles=articleRepository.findAll();
            Iterable<Provider> providers=providerRepository.findAll();
            Iterable<Categories> categories=categoriesRepository.findAll();
            return "shoes-view";
        }
        shoes.setArticle(articleRepository.findByNumber(number));
        shoes.setProvider(providerRepository.findByOrganization(organization));
        shoes.setCategories(categoriesRepository.findByType(type));
        shoesRepository.save(shoes);
        return "redirect:/shoes/shs";
    }

    @PostMapping("/{id}/remove")
    public String shoesDelete(@PathVariable("id") long id, Model model){
        Shoes shoes = shoesRepository.findById(id).orElseThrow();
        shoesRepository.delete(shoes);
        return "redirect:/shoes/shs";
    }

}

