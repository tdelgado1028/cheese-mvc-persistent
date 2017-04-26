package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

//Going to copy this import from Category.java, seems necessary with Cheese?
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Rhodondron on 4/26/2017.
 */
public class AddMenuItemForm {

    //for rendering form
    private Menu menu;
    private Iterable<Cheese> cheeses;

    //for processing form -- ergo validate not null
    @NotNull
    private int menuId;

    @NotNull
    private int cheeseId;

    //default no-arg
    public AddMenuItemForm(){}

    //accept/set values for menu and cheeses -- rendering fields
    public AddMenuItemForm(Iterable<Cheese> cheeses, Menu menu){
        this.cheeses = cheeses;
        this.menu = menu;
    }

    //getters/setters for all **rtclck "Generate"
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Iterable<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(Iterable<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getCheeseId() {
        return cheeseId;
    }

    public void setCheeseId(int cheeseId) {
        this.cheeseId = cheeseId;
    }
}
