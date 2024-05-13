package com.codesprint.file_explorer;

import java.io.File;

public record FileTreeNode(File file) {

    @Override
    public String toString() {
        return file.getName();
    }
}