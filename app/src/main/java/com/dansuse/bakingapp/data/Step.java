package com.dansuse.bakingapp.data;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class Step {
    Integer id;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public Step(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

//    class StepList{
//        List<Step> mStepList;
//    }
}
