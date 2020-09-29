package com.kodilla.integration;


public class FileTransformer {
    public String transformFile(String fileName) {
            String[] splitDirectory = fileName.split("/");
            return splitDirectory[splitDirectory.length - 1];
    }

}
