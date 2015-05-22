/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psuite;

import data.Data;
import data.MetaData;
import gui.MainFrame;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Thomas
 */
public class PSuite {
    private MainFrame mainFrame;
    private Map<String, MetaData> files;
    
    public PSuite() {
        this.mainFrame = new MainFrame(this);
        this.files = Data.getInstance().getFiles();
        showMainFrame();
        updateFileList("");
    }
    
    private void showMainFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    
    public void saveAndExit() {
        Data.getInstance().setFiles(files);
    }
    
    public void updateFileList(String search) {
        String[] keywords = search.split(" ");
        DefaultListModel lm = new DefaultListModel();
        for (Map.Entry<String, MetaData> entry : files.entrySet()) {
            String filePath = entry.getKey();
            List<String> tags = entry.getValue().getTags();
            for (String keyword : keywords) {
                if (filePath.contains(keyword)) {
                    lm.addElement(filePath);
                    break;
                }
                for (String tag : tags) {
                    if (tag.contains(keyword)) {
                        lm.addElement(filePath);
                        break;
                    }
                }
            }
        }
        mainFrame.setFilesList(lm);
    }
    
    private MetaData getMetaDataByKey(String key) {
        MetaData result = null;
        for (Map.Entry<String, MetaData> entry : files.entrySet()) {
            if (entry.getKey().equals(key)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }
    
    public void updateMetaData(String key) {
        MetaData metaData = getMetaDataByKey(key);
        if (metaData != null) {
            mainFrame.setTags(metaData.getTags());
        }
    }
    
    public void saveMetaData(String key) {
        String text = mainFrame.getTags();
        String[] tags = text.split(" ");
        List<String> aux = new ArrayList<>();
        for (String tag : tags) {
            aux.add(tag);
        }
        MetaData metaData = new MetaData(aux);
        files.replace(key, metaData);
    }
    
    public void addFile(String key) {
        MetaData md = new MetaData(new ArrayList<String>());
        files.put(key, md);
        updateFileList(mainFrame.getSearchText());
    }
    
    public void removeFile(String key) {
        files.remove(key);
        updateFileList(mainFrame.getSearchText());
    }
    
    public void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(PSuite.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       PSuite psuite = new PSuite();
    }
}
