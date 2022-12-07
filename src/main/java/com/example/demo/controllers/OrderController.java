package com.example.demo.controllers;


import com.example.demo.models.Order;
import com.example.demo.models.Shoes;
import com.example.demo.repo.OrderRepository;
import com.example.demo.repo.ShoesRepository;
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
@RequestMapping("/order")
@PreAuthorize("hasAnyAuthority('CASHIER')")

public class OrderController {
    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/ord")
    public String orderMain(Model model)
    {
        Iterable<Order> orders = orderRepository.findAll();
        Iterable<Shoes> shoes = shoesRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("shoes", shoes);
        return "order-main";
    }

    @GetMapping("/add")
    public String orderAdd(@ModelAttribute("order") Order order, Model model)
    {

        Iterable<Shoes> shoes=shoesRepository.findAll();


        ArrayList<Shoes> shoesArrayList=new ArrayList<>();

        for (Shoes sub: shoes){
            if (sub.getBrand()==null){
                shoesArrayList.add(sub);
            }
        }

        model.addAttribute("orders", order);
        model.addAttribute("shoes", shoes);

        return "order-add";
    }

    @PostMapping("/add")
    public String orderAdd(@ModelAttribute("orders") @Valid Order order, BindingResult bindingResult, Model model)
    {

        if(bindingResult.hasErrors())
        {
            Iterable<Shoes> shoes=shoesRepository.findAll();
            model.addAttribute("shoes", shoes);
            return "order-add";
        }
        var shoes = shoesRepository.findById(order.getShoes().getId()).orElseThrow();
        order.setShoes(shoes);
        orderRepository.save(order);
        return "redirect:/order/ord";
    }


    @GetMapping("/{id}")
    public String orderView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Order> order = orderRepository.findById(id);
        ArrayList<Order> res = new ArrayList<>();


        Iterable<Shoes> shoes=shoesRepository.findAll();
        model.addAttribute("shoes", shoes);


        order.ifPresent(res::add);
        model.addAttribute("order", res);
        if(!orderRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "order-view";

    }

    @GetMapping("/{id}/edit")
    public String orderEdit(@PathVariable("id")long id, Model model)
    {

        Iterable<Shoes> shoes=shoesRepository.findAll();
        Order order = orderRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("order",order);
        model.addAttribute("shoes", shoes);

        return "order-view";
    }

    @PostMapping("/{id}/edit")
    public String orderUpdate(@ModelAttribute("order")@Valid Order order, BindingResult bindingResult,  @RequestParam String brand, @PathVariable("id") long id, Model model) {

        order.setId(id);

        if (bindingResult.hasErrors()) {
            Iterable<Shoes> shoes=shoesRepository.findAll();
            model.addAttribute("shoes", shoes);
            return "order-view";
        }
        order.setShoes(shoesRepository.findByBrand(brand));
        orderRepository.save(order);
        return "redirect:/order/ord";
    }

    @PostMapping("/{id}/remove")
    public String orderDelete(@PathVariable("id") long id, Model model){
        Order order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);
        return "redirect:/order/ord";
    }



}
