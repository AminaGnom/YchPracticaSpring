package com.example.demo.controllers;



import com.example.demo.models.Provider;
import com.example.demo.repo.ProviderRepository;
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
@RequestMapping("/provider")
@PreAuthorize("hasAnyAuthority('CASHIER')")
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping("/prd")
    public String providerMain(Model model)
    {
        Iterable<Provider> providers = providerRepository.findAll();
        model.addAttribute("providers", providers);
        return "provider-main";
    }

    @GetMapping("/add")
    public String providerAdd(@ModelAttribute("providers") Provider provider)
    {
        return "provider-add";
    }


    @PostMapping("/add")
    public String providerAdd(@ModelAttribute("providers")@Valid Provider provider, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "provider-add";
        }
        providerRepository.save(provider);
        return "redirect:/provider/prd";
    }

    @GetMapping("/{id}")
    public String providerView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Provider> provider = providerRepository.findById(id);
        ArrayList<Provider> res = new ArrayList<>();
        provider.ifPresent(res::add);
        model.addAttribute("providers", res);
        if(!providerRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "provider-view";

    }

    @GetMapping("/{id}/edit")
    public String providerEdit(@PathVariable("id")long id, Model model)
    {
        Provider provider = providerRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("providers",provider);
        return "provider-view";
    }

    @PostMapping("/{id}/edit")
    public String providerUpdate(@ModelAttribute("providers")@Valid Provider provider, BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        provider.setId(id);
        if (bindingResult.hasErrors()) {
            return "provider-view";
        }
        providerRepository.save(provider);
        return "redirect:/provider/prd";
    }


    @PostMapping("/{id}/remove")
    public String providerDelete(@PathVariable("id") long id, Model model){
        Provider provider = providerRepository.findById(id).orElseThrow();
        providerRepository.delete(provider);
        return "redirect:/provider/prd";
    }



}
