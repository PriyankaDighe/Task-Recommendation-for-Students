/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tss.service.impl;

import com.tss.model.UserProfile;
import com.tss.repository.UserProfileRepositoory;
import com.tss.service.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
//    @Autowired
//    private UserProfileRepositoory userProfileRepository;
//    
    @Override
    public List<UserProfile> getAllUserTaskProfile(String datasetFileName) {
        List<UserProfile> userProfiles  = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(datasetFileName)));
            String line;
            int count = 0;
            while((line = bufferedReader.readLine())!= null){
                if(count != 0){
                   String[] data = line.split(",");
                   if(data.length == 4){
                       UserProfile userProfile = new UserProfile();
                       userProfile.setDomain(data[0]);
                       userProfile.setSkill(data[1]);
                       userProfile.setIntrest(data[2]);
                       userProfile.setFieldOfStudy(data[3]);
                       userProfile.setUserId(count);
                       userProfiles.add(userProfile);
                   }
                }
                count++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return userProfiles;
    }

	@Override
	public String getPath() {
		String reponsePath = "";
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/classes/");
//			System.out.println(fullPath);
//			System.out.println(pathArr[0]);
			fullPath = pathArr[0];

			// to read a file from webcontent
			reponsePath = new File(fullPath).getPath();
		} catch (Exception ex) {

		}
		return reponsePath;

	}
    
}
