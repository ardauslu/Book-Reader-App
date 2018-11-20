/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookreader.database;

import com.bookreader.model.User;

/**
 *
 * @author ardau
 */
public interface NewInterface {
    
    
    boolean insert(User user);
    
    User getUser(String userName,String password);
    
}
