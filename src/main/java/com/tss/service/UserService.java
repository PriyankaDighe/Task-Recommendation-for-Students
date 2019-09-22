/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tss.service;

import com.tss.model.UserProfile;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface UserService {
    public List<UserProfile> getAllUserTaskProfile(String datasetFileName);
    public String  getPath();
}
