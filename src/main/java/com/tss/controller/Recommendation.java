/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tss.controller;

import com.tss.algorithm.LSHMinHash;
import com.tss.model.UserProfile;
import com.tss.service.UserService;
import com.tss.service.impl.UserServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author DELL
 */

@RestController
public class Recommendation  {
    @Autowired
    private UserService userService;
    
    public static final String TEST_DATASET_FILE_NAME = "D:\\ME_PROJECT\\NEATBEANS\\TaskRecommendation\\dataset\\testData.csv";
    public static final String DATASET_FILE_NAME = "D:\\ME_PROJECT\\NEATBEANS\\TaskRecommendation\\dataset\\user.csv";

    @RequestMapping(value="/Recommendation", method = RequestMethod.POST)
    protected List<Map<UserProfile, List<UserProfile>>> processRequest(@RequestBody List<UserProfile> testTaskProfiles) {
        List<Map<UserProfile, List<UserProfile>>> result = new ArrayList<>();
        Map<UserProfile, List<UserProfile>> recommendationResult = new HashMap<UserProfile, List<UserProfile>>();
//        UserService userService = new UserServiceImpl();
//        List<UserProfile> testTaskProfiles = userService.getAllUserTaskProfile(TEST_DATASET_FILE_NAME);
        for (UserProfile userTaskProfile : testTaskProfiles) {
            List<UserProfile> recommendedList = new ArrayList<UserProfile>();
            List<String> testData = new ArrayList<String>();
            testData.add(userTaskProfile.getDomain());
            testData.add(userTaskProfile.getSkill());
            testData.add(userTaskProfile.getIntrest());
            testData.add(userTaskProfile.getFieldOfStudy());
             System.out.println(userTaskProfile.toString());
            List<UserProfile> userProfiles = userService.getAllUserTaskProfile(userService.getPath()+"\\user.csv");

            for (UserProfile userProfile : userProfiles) {
                List<String> trainData = new ArrayList<String>();
                trainData.add(userProfile.getDomain());
                trainData.add(userProfile.getSkill());
                trainData.add(userProfile.getIntrest());
                trainData.add(userProfile.getFieldOfStudy());
                LSHMinHash<String> lsh = new LSHMinHash<String>(testData, trainData);
                double similarityScore = lsh.findSimilarities();
                userProfile.setSimilarityScore(similarityScore);
                recommendedList.add(userProfile);
            }

            Collections.sort(recommendedList, new Comparator<UserProfile>() {
                @Override
                public int compare(UserProfile o1, UserProfile o2) {

                    return Double.compare(o2.getSimilarityScore(), o1.getSimilarityScore());
                }

            });

            recommendedList = recommendedList.subList(0, 3);
            recommendationResult.put(userTaskProfile, recommendedList);
            result.add(recommendationResult);
        }
         
       return result;
    }

}
