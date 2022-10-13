package com.akbaramanov.huffmanarchiver.auxiliaryClasses;

public class ResponseFile {
    private String fileName;

    private String url;


    public ResponseFile(String name, String url) {

        fileName = name;
        this.url = url;
    }
    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
