package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Rhodondron on 4/25/2017.
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    //Spring creates an object of class CategoryDao on startup on auto
    @Autowired
    private CategoryDao categoryDao;

    //index handler for category/index.hmtl
    @RequestMapping(value = "")
    public String indexHandler(Model model){

        //retrieve list of all categories -- .findall()
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryDao.findAll());

        return "category/index";
    }

    //add handler to GET new Category object
    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddForm(Model model){

        model.addAttribute("title", "Add New Category");
        model.addAttribute(new Category());
        model.addAttribute("categories", categoryDao.findAll());

        return "category/add";
    }

    //add handler to accept POST requests
    @RequestMapping(value="add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Category category, Errors errors){

        //check for errors- return if true
        if (errors.hasErrors()){
            model.addAttribute("title", "Add New Category");
            model.addAttribute("categories", categoryDao.findAll());
            return "category/add";
        }
        //error free = save new Category object -- categoryDao.save(category), redirect to category/index.html
        else{
            categoryDao.save(category);
            return "redirect:";
        }

    }


}
