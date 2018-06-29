package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @RequestMapping("/")
    public String listDishs(Dish dish, Model model){
        model.addAttribute("dishes", dishRepository.findAll());
        model.addAttribute("feedbackType", feedbackRepository.findAll());
        return "dishlist";
    }

    @RequestMapping("/addfeedback")
    public String addFeedback(Model model)
    {
        model.addAttribute("aFeedback", new Feedback());
        model.addAttribute("dishes", dishRepository.findAll());
        return "feedback";
    }

    @RequestMapping("/savefeedback")
    public String saveFeedback(@ModelAttribute("aFeedback") Feedback feedback, Model model)
    {
        feedbackRepository.save(feedback);
        return "redirect:/";
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

    @PostConstruct
    public void fillTables()
    {
        Feedback p = new Feedback();
        p.setFeedbackType("TASTY!");
        feedbackRepository.save(p);

        p = new Feedback();
        p.setFeedbackType("NASTY!");
        feedbackRepository.save(p);
    }

}