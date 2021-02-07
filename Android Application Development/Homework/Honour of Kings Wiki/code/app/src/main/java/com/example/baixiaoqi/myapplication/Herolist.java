package com.example.baixiaoqi.myapplication;

import java.io.Serializable;

public class Herolist implements Serializable {

    private String defaultImage, name, title, position, SurvivabilityValue, AttackDamageValue;
    private String SkillEffectivenessValue, StartingAbilityValue, BestPartnerHero, SuppressHeroes;

    public Herolist(String defaultImage, String name, String title, String position, String SurvivabilityValue, String AttackDamageValue,
                    String SkillEffectivenessValue, String StartingAbilityValue, String BestPartnerHero, String SuppressHeroes) {
        this.defaultImage = defaultImage;
        this.name = name;
        this.title = title;
        this.position = position;
        this.SurvivabilityValue = SurvivabilityValue;
        this.AttackDamageValue = AttackDamageValue;
        this.SkillEffectivenessValue = SkillEffectivenessValue;
        this.StartingAbilityValue = StartingAbilityValue;
        this.BestPartnerHero = BestPartnerHero;
        this.SuppressHeroes = SuppressHeroes;
    }
    public Herolist(){

    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setSurvivabilityValue(String survivabilityValue) {
        SurvivabilityValue = survivabilityValue;
    }

    public String getSurvivabilityValue() {
        return SurvivabilityValue;
    }

    public void setAttackDamageValue(String attackDamageValue) {
        AttackDamageValue = attackDamageValue;
    }

    public String getAttackDamageValue() {
        return AttackDamageValue;
    }

    public void setSkillEffectivenessValue(String skillEffectivenessValue) {
        SkillEffectivenessValue = skillEffectivenessValue;
    }

    public String getSkillEffectivenessValue() {
        return SkillEffectivenessValue;
    }

    public void setStartingAbilityValue(String startingAbilityValue) {
        StartingAbilityValue = startingAbilityValue;
    }

    public String getStartingAbilityValue() {
        return StartingAbilityValue;
    }

    public void setBestPartnerHero(String bestPartnerHero) {
        BestPartnerHero = bestPartnerHero;
    }

    public String getBestPartnerHero() {
        return BestPartnerHero;
    }

    public void setSuppressHeroes(String suppressHeroes) {
        SuppressHeroes = suppressHeroes;
    }

    public String getSuppressHeroes() {
        return SuppressHeroes;
    }
}
