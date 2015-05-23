/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psuite;

import data.Data;
import data.DataAdapter;
import data.MetaData;
import gui.MainFrame;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author Thomas
 */
public class PSuite {
    private MainFrame mainFrame;
    private DataAdapter dataAdapter;
    private Map<String, MetaData> files;
    
    public PSuite() {
        this.mainFrame = new MainFrame(this);
        this.files = Data.getInstance().getFiles();
        this.dataAdapter = new DataAdapter();
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
        mainFrame.setFilesList(getListModel(getSearchResults(search)));
    }
    
    public void sortByAddDateAscending(String search) {
        Map<String, MetaData> results = getSearchResults(search);
        DefaultListModel aux = new DefaultListModel();
        aux.setSize(results.size());
        for (Map.Entry<String, MetaData> entry : results.entrySet()) {
            int index = 0;
            for (Map.Entry<String, MetaData> entry2 : results.entrySet()) {
                if (entry != entry2) {
                    if (entry2.getValue().getAddedAt().before(entry.getValue().getAddedAt())) {
                        index++;
                    }
                }
            }
            aux.add(index, entry.getKey());
        }
        aux = cleanListModel(aux);
        mainFrame.setFilesList(aux);
    }
    
    public void sortByAddDateDescending(String search) {
        Map<String, MetaData> results = getSearchResults(search);
        DefaultListModel aux = new DefaultListModel();
        aux.setSize(results.size());
        for (Map.Entry<String, MetaData> entry : results.entrySet()) {
            int index = 0;
            for (Map.Entry<String, MetaData> entry2 : results.entrySet()) {
                if (entry != entry2) {
                    if (entry2.getValue().getAddedAt().after(entry.getValue().getAddedAt())) {
                        index++;
                    }
                }
            }
            aux.add(index, entry.getKey());
        }
        aux = cleanListModel(aux);
        mainFrame.setFilesList(aux);
    }
    
    public void sortByOpenDateAscending(String search) {
        Map<String, MetaData> results = getSearchResults(search);
        DefaultListModel aux = new DefaultListModel();
        aux.setSize(results.size());
        for (Map.Entry<String, MetaData> entry : results.entrySet()) {
            int index = 0;
            for (Map.Entry<String, MetaData> entry2 : results.entrySet()) {
                if (entry != entry2) {
                    Date open = entry.getValue().getOpenedAt();
                    Date open2 = entry2.getValue().getOpenedAt();
                    if (open != null && open2 != null) {
                        if (open2.before(open)) {
                            index++;
                        }
                    }
                    if (open == null) {
                        index = -1;
                    }
                }
            }
            if (index >= 0) {
               aux.add(index, entry.getKey()); 
            }
        }
        aux = cleanListModel(aux);
        mainFrame.setFilesList(aux);
    }
    
    public void sortByOpenDateDescending(String search) {
        Map<String, MetaData> results = getSearchResults(search);
        DefaultListModel aux = new DefaultListModel();
        aux.setSize(results.size());
        for (Map.Entry<String, MetaData> entry : results.entrySet()) {
            int index = 0;
            for (Map.Entry<String, MetaData> entry2 : results.entrySet()) {
                if (entry != entry2) {
                    Date open = entry.getValue().getOpenedAt();
                    Date open2 = entry2.getValue().getOpenedAt();
                    if (open != null && open2 != null) {
                        if (open2.after(open)) {
                            index++;
                        }
                    }
                    if (open == null) {
                        index = -1;
                    }
                }
            }
            if (index >= 0) {
               aux.add(index, entry.getKey()); 
            }
        }
        aux = cleanListModel(aux);
        mainFrame.setFilesList(aux);
    }
    
    private DefaultListModel getListModel(Map<String, MetaData> results) {
        DefaultListModel lm = new DefaultListModel();
        for (Map.Entry<String, MetaData> entry : results.entrySet()) {
            lm.addElement(entry.getKey());
        }
        return lm;
    }
    
    private DefaultListModel cleanListModel(DefaultListModel lm) {
        DefaultListModel aux = new DefaultListModel();
        for (int i = 0; i < lm.getSize(); i++) {
            Object o = lm.get(i);
            if (o != null) {
                aux.addElement(o);
            }
        }
        return aux;
    }
    
    private Map<String, MetaData> getSearchResults (String search) {
        String[] keywords = search.split(" ");
        Map<String, MetaData> result = new HashMap<>();
        for (Map.Entry<String, MetaData> entry : files.entrySet()) {
            String filePath = entry.getKey();
            List<String> tags = entry.getValue().getTags();
            for (String keyword : keywords) {
                if (filePath.contains(keyword)) {
                    result.put(filePath, entry.getValue());
                    break;
                }
                for (String tag : tags) {
                    if (tag.contains(keyword)) {
                        result.put(filePath, entry.getValue());
                        break;
                    }
                }
            }
        }
        return result;
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
            Date doa = metaData.getOpenedAt();
            String soa = "NA";
            if (doa != null) {
                soa = dataAdapter.dateToString(doa);
            }
            mainFrame.setMetaData(dataAdapter.dateToString(
                    metaData.getAddedAt()), soa, metaData.getTags());
        }
    }
    
    public void saveMetaData(String key) throws ParseException {
        String text = mainFrame.getTags();
        String[] tags = text.split(" ");
        List<String> aux = new ArrayList<>();
        for (String tag : tags) {
            aux.add(tag);
        }
        String a = mainFrame.getAddedAtText();
        String o = mainFrame.getOpenedAtText();
        if (!a.equals("") && !o.equals("")) {
            Date openedAt = null;
            if (!o.equals("NA")) {
                openedAt = dataAdapter.stringToDate(o);
            }
            Date addedAt = dataAdapter.stringToDate(a);
            MetaData metaData = new MetaData(aux, addedAt, openedAt);
            files.replace(key, metaData);
        }
    }
    
    public void addFile(String key) {
        MetaData md = new MetaData(new ArrayList<String>(), new Date(), null);
        files.put(key, md);
        updateFileList(mainFrame.getSearchText());
    }
    
    public void removeFile(String key) {
        files.remove(key);
        updateFileList(mainFrame.getSearchText());
    }
    
    public void openFile(File file) {
        MetaData md = getMetaDataByKey(file.toString());
        mainFrame.setMetaData(dataAdapter.dateToString(md.getAddedAt()), 
                dataAdapter.dateToString(new Date()), md.getTags());
        try {
            saveMetaData(file.toString());
        } catch (ParseException ex) {
            Logger.getLogger(PSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateMetaData(file.toString());
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
    public static void main(String[] args) throws ParseException, DatatypeConfigurationException {
       PSuite psuite = new PSuite();
    }
}
