/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psuite;

import data.Data;
import data.MetaData;
import gui.MainFrame;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;

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
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       PSuite psuite = new PSuite();
//       Map<String, MetaData> files = Data.getInstance().getFiles();
//       psuite.outputFiles(files);
//       LinkedList<String> tags = new LinkedList<>();
//       tags.add("Hello World");
//       MetaData metaData = new MetaData(tags);
//       files.put("Added File", metaData);
//       Data.getInstance().setFiles(files);
//       files = Data.getInstance().getFiles();
//       psuite.outputFiles(files);
    }
    
    private void outputFiles(Map<String, MetaData> files) {
        for (Map.Entry<String, MetaData> entry : files.entrySet()) {
            String line = entry.getKey() + ", tags = {";
            for (String tag : entry.getValue().getTags()) {
                line = line + tag + " ";
            }
            line = line + "}";
            System.out.println(line);
        }
    }
    
}
