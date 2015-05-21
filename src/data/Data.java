/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import generated.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Thomas
 */
public class Data {
    private static final String DATAPATH = "/data/data.xml";
    private static Data instance = null;
    
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
            Source s = new StreamSource(Data.class.getResourceAsStream(DATAPATH));
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
}
