package com.tss.algorithm;

import java.util.ArrayList;
import java.util.List;
import com.tss.model.UserProfile;
import com.tss.service.UserService;
import com.tss.service.impl.UserServiceImpl;
import java.util.Collections;
import java.util.Comparator;
public class TestAlgo {

    public static final String TEST_DATASET_FILE_NAME = "./dataset/testData.csv";
    public static final String DATASET_FILE_NAME = "./dataset/user.csv";

    public static void main(String[] args) {
        List<UserProfile> recommendedList = new ArrayList<UserProfile>();
        UserService userService = new UserServiceImpl();
        List<UserProfile> testTaskProfiles = userService.getAllUserTaskProfile(TEST_DATASET_FILE_NAME);
        for (UserProfile userTaskProfile :  testTaskProfiles) {
            List<String> testData = new ArrayList<String>();
            testData.add(userTaskProfile.getDomain());
            testData.add(userTaskProfile.getSkill());
            testData.add(userTaskProfile.getIntrest());
            testData.add(userTaskProfile.getFieldOfStudy());
            System.out.println("=========================================");
            System.out.println("---------- TEST DATA --------------");
            System.out.println("=========================================");
            System.out.println("Domain :: " + testData.get(0));
            System.out.println("Skill :: " + testData.get(1));
            System.out.println("Intrest :: " + testData.get(2));
            System.out.println("Field of study :: " + testData.get(3));
            System.out.println("-----------------------------------");

            List<UserProfile> userProfiles = userService.getAllUserTaskProfile(DATASET_FILE_NAME);
            System.out.println("Find All Records :: " + userProfiles.size());
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
            System.out.println("=========================================");
            System.out.println("TOTAL RECOMMEND TASK PROFILE :: " + recommendedList.size());
            System.out.println("=========================================");
            for (UserProfile land : recommendedList) {
                System.out.println("TASK Profile Id :: " + land.getUserId());
                System.out.println("Domain :: " + land.getDomain());
                System.out.println("Skill :: " + land.getSkill());
                System.out.println("Intrest :: " + land.getIntrest());
                System.out.println("Filed of study :: " + land.getFieldOfStudy());
                System.out.println("LSH Score :: " + land.getSimilarityScore());

                System.out.println("----------------------------------");
            }
            System.out.println("=============== NEXT TASK PROFILE ==================");
        }
    }

}
