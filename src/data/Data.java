/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import generated.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Thomas
 */
public class Data {
    private static Data instance = null;
    private static final boolean BUILD = true;
    
    protected Data () {}
    
    public static synchronized Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }
    
    public Map<String, MetaData> getFiles() {
        HashMap<String, MetaData> result = new HashMap<>();
        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller u = jc.createUnmarshaller();
            Source s = new StreamSource(getDataLocation());
            JAXBElement<FileListT> e = u.unmarshal(s, FileListT.class);
            List<FileT> files = e.getValue().getFile();
            files.stream().forEach((file) -> {
                String fp = file.getFilePath();
                List<String> tags = file.getTag();
                MetaData md = new MetaData(tags);
                result.put(fp, md);
            });
        } catch (JAXBException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void setFiles(Map<String, MetaData> files) {
        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller m = jc.createMarshaller();
            try {
                OutputStream os = new FileOutputStream(getDataLocation());
                FileListT fl = new FileListT();
                for (Map.Entry<String, MetaData> entry : files.entrySet()) {
                    FileT f = new FileT();
                    f.setFilePath(entry.getKey());
                    for (String s : entry.getValue().getTags()) {
                        f.getTag().add(s);
                    }
                    fl.getFile().add(f);
                }
                ObjectFactory o = new ObjectFactory();
                m.marshal(o.createFiles(fl), os);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private String getDataLocation() {
        if (BUILD) {
            return System.getProperty("user.dir") + "\\data.xml";
        } else {
            return System.getProperty("user.dir") + "\\src\\data\\data.xml";
        }
    }
    
    
}
