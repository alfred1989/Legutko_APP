package com.example.legutkoapplication.model;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String producer;
    String species;
    String name;
    String variety;
    String color;
    String group;
    String subgroup;
    String estimatedCrop;
    String offPresence;
    String offPercentage;
    String description;
    String standardPlantation;
    String comment;
    String batch;
    String code;
    String plantationId;
    String commentaryInPL;
    String descriptionInPL;
    String symbol;
    String historical_data;

    public Product() {
    }

    public Product(int id, String producer, String species, String name, String variety, String color, String group, String subgroup,
                   String estimatedCrop, String offPresence, String offPercentage, String description, String standardPlantation,
                   String comment, String batch, String code, String plantationId, String commentaryInPL, String descriptionInPL, String symbol,  String historical_data) {
        this.id = id;
        this.producer = producer;
        this.species = species;
        this.name = name;
        this.variety = variety;
        this.color = color;
        this.group = group;
        this.subgroup = subgroup;
        this.estimatedCrop = estimatedCrop;
        this.offPresence = offPresence;
        this.offPercentage = offPercentage;
        this.description = description;
        this.standardPlantation = standardPlantation;
        this.comment = comment;
        this.batch = batch;
        this.code = code;
        this.plantationId = plantationId;
        this.commentaryInPL = commentaryInPL;
        this.descriptionInPL = descriptionInPL;
        this.symbol = symbol;
        this.historical_data = historical_data;
    }

    public String getHistorical_data() {
        return historical_data;
    }

    public void setHistorical_data(String historical_data) {
        this.historical_data = historical_data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getEstimatedCrop() {
        return estimatedCrop;
    }

    public void setEstimatedCrop(String estimatedCrop) {
        this.estimatedCrop = estimatedCrop;
    }

    public String getOffPresence() {
        return offPresence;
    }

    public void setOffPresence(String offPresence) {
        this.offPresence = offPresence;
    }

    public String getOffPercentage() {
        return offPercentage;
    }

    public void setOffPercentage(String offPercentage) {
        this.offPercentage = offPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStandardPlantation() {
        return standardPlantation;
    }

    public void setStandardPlantation(String standardPlantation) {
        this.standardPlantation = standardPlantation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlantationId() {
        return plantationId;
    }

    public void setPlantationId(String plantationId) {
        this.plantationId = plantationId;
    }

    public String getCommentaryInPL() {
        return commentaryInPL;
    }

    public void setCommentaryInPL(String commentaryInPL) {
        this.commentaryInPL = commentaryInPL;
    }

    public String getDescriptionInPL() {
        return descriptionInPL;
    }

    public void setDescriptionInPL(String descriptionInPL) {
        this.descriptionInPL = descriptionInPL;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

