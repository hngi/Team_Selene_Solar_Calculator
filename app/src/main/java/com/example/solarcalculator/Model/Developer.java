package com.example.solarcalculator.Model;

import android.media.Image;

public class Developer {
    private Integer mImage;
    private String mName;
    private String mTitle;
    private String mFaceBookUrl;
    private String mTwitterUrl;
    private String mGitHubUrl;
    private String mLinkedInUrl;


    public String getmFaceBookUrl() { return mFaceBookUrl; }
    public void setmFaceBookUrl(String mFaceBookUrl) { this.mFaceBookUrl = mFaceBookUrl; }
    public String getmTwitterUrl() { return mTwitterUrl; }
    public void setmTwitterUrl(String mTwitterUrl) { this.mTwitterUrl = mTwitterUrl; }
    public String getmGitHubUrl() { return mGitHubUrl; }
    public void setmGitHubUrl(String mGitHubUrl) { this.mGitHubUrl = mGitHubUrl; }
    public String getmLinkedInUrl() { return mLinkedInUrl; }
    public void setmLinkedInUrl(String mLinkedInUrl) { this.mLinkedInUrl = mLinkedInUrl; }
    public Integer getmImage() {
        return mImage;
    }
    public void setmImage(Integer mImage) {
        this.mImage = mImage;
    }
    public String getmName() { return mName; }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
