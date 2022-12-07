package com.example.demo.controllers;

import com.example.demo.models.MakingOrder;
import com.example.demo.models.Order;
import com.example.demo.models.Shoes;
import com.example.demo.repo.MakingOrderRepository;
import com.example.demo.repo.OrderRepository;
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
@PreAuthorize("hasAnyAuthority('CASHIER')")
@RequestMapping("/making_order")
public class MakingOrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MakingOrderRepository makingOrderRepository;

    @GetMapping("/mord")
    public String main(Model model) {
        Iterable<MakingOrder> making_order = makingOrderRepository.findAll();
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("making_order", making_order);
        model.addAttribute("orders", orders);

        return "making_order-main";}

    @GetMapping("/add")
    public String add(@ModelAttribute("making-order")MakingOrder making_order, Model model)
    {

        Iterable<Order> orders = orderRepository.findAll();
        ArrayList<Order> orderArrayList=new ArrayList<>();
        for (Order sub: orders){
            if (sub.getId()==null){
                orderArrayList.add(sub);
            }
        }
        model.addAttribute("making-order", making_order);
        model.addAttribute("orders", orders);


        return "making_order-add";
    }

    /*@PostMapping("/add")
    public String postAdd(@ModelAttribute("making_order") @Valid MakingOrder making_order, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()) {

            Iterable<Order> orders = orderRepository.findAll();
            model.addAttribute("orders", orders);

            return "making_order-add";
        }
        making_order.setOrder(order);
        makingOrderRepository.save(making_order);
        return "redirect:/making_order/mord";
    }*/

    @GetMapping("/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<MakingOrder> making_order = makingOrderRepository.findById(id);
        ArrayList<MakingOrder> res = new ArrayList<>();

        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);

        making_order.ifPresent(res::add);
        model.addAttribute("making_order", res);
        if (!makingOrderRepository.existsById(id)) {
            return "redirect:/";
        }
        return "making_order-view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {

        Iterable<Order> orders = orderRepository.findAll();
        MakingOrder making_order = makingOrderRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("making_order", making_order);
        model.addAttribute("orders", orders);

        return "making_order-view";
    }

    @PostMapping("/{id}/edit")
    public String postUpdate(@ModelAttribute ("making_order") @Valid MakingOrder making_order, BindingResult bindingResult, @PathVariable ("id") long id, Model model) {
        if (bindingResult.hasErrors()){
            Iterable<Order> orders = orderRepository.findAll();
        return "making_order-view";
    }
        makingOrderRepository.save(making_order);
        return "redirect:/making_order/mord";
    }

    @PostMapping("/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        MakingOrder making_order = makingOrderRepository.findById(id).orElseThrow();
        makingOrderRepository.delete(making_order);
        return "redirect:/making_order/mord";
    }







}
