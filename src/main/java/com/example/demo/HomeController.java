package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;


@Controller
public class HomeController {

    @Autowired
    DishRepository dishRepository;

    @RequestMapping("/")
    public String listDishs(Dish dish, Model model){
        model.addAttribute("dishes", dishRepository.findAll());
        return "dishlist";
    }

    @GetMapping("/add")
    public String loadFormPage(Model model){
        model.addAttribute("dish", new Dish());
        return "dishform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Dish dish, BindingResult result){
        if(result.hasErrors()){
            return "dishform";
        }
        dishRepository.save(dish);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String displayDish(@PathVariable("id") long id, Model model){
        model.addAttribute("dish", dishRepository.findById(id).get());
        return "displaydish";
    }

    @RequestMapping("/update/{id}")
    public String updateDish(@PathVariable("id") long id, Model model){
        model.addAttribute("dish", dishRepository.findById(id).get());
        return "dishform";
    }

    @RequestMapping("/delete/{id}")
    public String delDish(@PathVariable("id") long id){
        dishRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search",searchString);
        model.addAttribute("dishes", dishRepository.findAllByNameContainingIgnoreCase(searchString));
        return "dishlist";
    }

}