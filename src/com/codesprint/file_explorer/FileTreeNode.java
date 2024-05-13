package com.codesprint.file_explorer;

import java.io.File;

public record FileTreeNode(File file) {

    @Override
    public String toString() {
        return file.getName();
    }

    public FileTreeNode(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    //fetch actual file_Name
    public String getSpecificFileName(String file){
        StringBuilder fileName = new StringBuilder();
        for(int i=0;i<file.length();i++){
            if(file.charAt(i)=='\\') fileName.append("\\");
            fileName.append(file.charAt(i));
        }
        String[] fileNameSub = fileName.toString().split("\\\\");
        return fileNameSub[fileNameSub.length-1];
    }
}