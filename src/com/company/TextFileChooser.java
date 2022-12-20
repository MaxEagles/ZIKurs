package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class TextFileChooser extends JFileChooser {
    public TextFileChooser() {
        setCurrentDirectory(new File("../../Desktop"));
        setFileFilter(new FileNameExtensionFilter("txt", "txt"));
        setAcceptAllFileFilterUsed(false);
    }
}
