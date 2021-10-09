package com.whats.app.statussaver.statusdownloader.models;

import android.net.Uri;

public class Savedimages {
    public boolean showCheckbox = false;
    private String name;
    private Uri uri;
    private String path;
    private String filename;
    private boolean isSelected;
    public Savedimages() {
    }

    public Savedimages(String name, Uri uri, String path, String filename,boolean isSelected) {
        this.name = name;
        this.uri = uri;
        this.path = path;
        this.filename = filename;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


