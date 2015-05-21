/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.List;

/**
 *
 * @author Thomas
 */
public class MetaData {
    //<editor-fold defaultstate="collapsed" desc="Instance Variables">
    private List<String> tags;
    //</editor-fold>
    
    /**
     * Constructor.
     * @param t the list of tags.
     */
    public MetaData(final List<String> t) {
        this.tags = t;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Accessor Methods">
    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    /**
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    //</editor-fold>
    //</editor-fold>
}
