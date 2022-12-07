package com.example.demo.controllers;

import com.example.demo.models.Staff;
import com.example.demo.repo.StaffRepository;
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
@RequestMapping("/staff")
@PreAuthorize("hasAnyAuthority('ADMIN, CASHIER')")

public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/stf")
    public String blogMain(Model model)
    {
        Iterable<Staff> staff = staffRepository.findAll();
        model.addAttribute("staff", staff);
        return "staff-basic";
    }

    @GetMapping("/add")
    public String StaffAdd(@ModelAttribute("staff") Staff staff)
    {
        return "staff-add";
    }


    @PostMapping("/add")
    public String StaffAdd(@ModelAttribute("staff")@Valid Staff staff, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "staff-add";
        }
        staffRepository.save(staff);
        return "redirect:/staff/stf";
    }

    @GetMapping("/filter")
    public String staffFilter(Model model)
    {
        return "staff-filter";
    }

    @PostMapping("/filter/result")
    public String blogResult(@RequestParam String fio, Model model)
    {
        List<Staff> result = staffRepository.findByFioContains(fio);
        List<Staff> result1 = staffRepository.findByFio(fio);
        model.addAttribute("result", result);
        return "staff-filter";
    }

    @GetMapping("/{id}")
    public String staffView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Staff> staff = staffRepository.findById(id);
        ArrayList<Staff> res = new ArrayList<>();
        staff.ifPresent(res::add);
        model.addAttribute("staff", res);
        if(!staffRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "staff-view";

    }

    @GetMapping("/{id}/edit")
    public String staffEdit(@PathVariable("id")long id,
                            Model model)
    {
        Staff staff = staffRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("staff",staff);
        return "staff-view";
    }

    @PostMapping("/{id}/edit")
    public String staffUpdate(@ModelAttribute("staff")@Valid Staff staff, BindingResult bindingResult,
                                @PathVariable("id") long id) {

        staff.setId(id);
        if (bindingResult.hasErrors()) {
            return "staff-view";
        }
        staffRepository.save(staff);
        return "redirect:/staff/stf";
    }

    @PostMapping("/{id}/remove")
    public String staffDelete(@PathVariable("id") long id, Model model){
        Staff staff = staffRepository.findById(id).orElseThrow();
        staffRepository.delete(staff);
        return "redirect:/staff/stf";
    }



}
