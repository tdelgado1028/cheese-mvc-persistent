package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("categories", menuDao.findAll());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(@Valid @ModelAttribute Menu newMenu, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add New Menu");
            model.addAttribute("categories", menuDao.findAll());
            return "menu/add";
        }

        menuDao.save(newMenu);

        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable(value = "id") int id, Model model){

        Menu menu = menuDao.findOne(id);

        model.addAttribute("title", "View "+ menu.getName() +" Menu");
        model.addAttribute("menu", menu);

        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(@PathVariable(value = "id") int id, Model model){

        Menu menu = menuDao.findOne(id);

        //STEP BREAK//

        AddMenuItemForm itemForm = new AddMenuItemForm(cheeseDao.findAll(),menu);

        model.addAttribute("form", itemForm);

        model.addAttribute("title", "Add item to menu: "+ menu.getName());

        return "menu/add-item";
    }

    //add another addItem handler to accept POST requests -- model from CategoryController's POST add
    @RequestMapping(value="add-item/{id}", method = RequestMethod.POST)
    public String addItem(Model model,
                          @ModelAttribute @Valid AddMenuItemForm menuItemForm,
                          @PathVariable(value = "id") int id,
                          Errors errors) {

        //check for errors- return if true -- modelled after addItem GET
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Item to menu: " + menuDao.findOne(menuItemForm.getMenuId()));
            model.addAttribute("form", menuItemForm);
            model.addAttribute("categories", menuDao.findAll());
            return "menu/add-item";
        }

        //error free = save new menu -- menuDao.save(theMenu), redirect to VIEW of menu URL
        else {

            //find given cheese & menu by ID using cheeseDao & menuDao
            //so we know what menu to save
            //so we know what to set the cheese in menu's objects for ManyToMany
            Menu theMenu = menuDao.findOne(menuItemForm.getMenuId());
            Cheese theCheese = cheeseDao.findOne(menuItemForm.getCheeseId());

            //implementing ManyToMany relationship of cheeses in Menu.java
            theMenu.addItem(theCheese);

            //save new menu
            menuDao.save(theMenu);

            //redirect to VIEW of new menu
            return "redirect:../view/" + theMenu.getId();
        }
    }
}
