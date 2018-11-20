package com.bookreader.helper;

import com.bookreader.database.Implementation;
import com.bookreader.model.User;

public class Controller {

    private static Controller controller;
    private final Implementation interfaceImplementation;

    private Controller() {

        this.interfaceImplementation = new Implementation();
        this.interfaceImplementation.openConnection();

    }

    public static Controller getController() {

        if (controller == null) {

            controller = new Controller();

        }

        return controller;

    }

    public boolean signup(User user) {
        return this.interfaceImplementation.insert(user);
    }

    public User login(String username, String password) {
        return this.interfaceImplementation.getUser(username, password);
    }

}
