package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Rhodondron on 4/26/2017.
 */

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("title", "Menu");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenu(Model model) {
        model.addAttribute("title", "Add New Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(@Valid @ModelAttribute Menu newMenu, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add New Menu");
            model.addAttribute(new Menu());
            model.addAttribute(errors); //*??
            return "menu/add";
        }

        menuDao.save(newMenu);

        return "redirect:view/" + newMenu.getId();
    }
}
