/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tss.model;

/**
 *
 * @author DELL
 */
public class UserProfile {
    private int userId;
    private String domain;
    private String skill;
    private String intrest;
    private String fieldOfStudy;
    private double similarityScore;

    public UserProfile(String domain, String skill, String intrest, String fieldOfStudy, double similarityScore) {
        this.domain = domain;
        this.skill = skill;
        this.intrest = intrest;
        this.fieldOfStudy = fieldOfStudy;
        this.similarityScore = similarityScore;
    }

    
    public UserProfile() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getIntrest() {
        return intrest;
    }

    public void setIntrest(String intrest) {
        this.intrest = intrest;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    @Override
    public String toString() {
        return "UserProfile{" + "userId=" + userId + ", gender=" + domain + ", skill=" + skill + ", intrest=" + intrest + ", filedOfStudy=" + fieldOfStudy + '}';
    }

    
}
