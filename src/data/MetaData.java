/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class MetaData {
    //<editor-fold defaultstate="collapsed" desc="Instance Variables">
    private List<String> tags;
    private Date addedAt, openedAt;
    //</editor-fold>
    
    /**
     * Constructor.
     * @param t the list of tags.
     * @param a the added_at date.
     * @param o the opened_at date.
     */
    public MetaData(final List<String> t, final Date a, final Date o) {
        this.tags = t;
        this.addedAt = a;
        this.openedAt = o;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Accessor Methods">
    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    /**
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }
    
       /**
     * @return the addedAt
     */
    public Date getAddedAt() {
        return addedAt;
    }

    /**
     * @return the openedAt
     */
    public Date getOpenedAt() {
        return openedAt;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
        /**
     * @param addedAt the addedAt to set
     */
    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * @param openedAt the openedAt to set
     */
    public void setOpenedAt(Date openedAt) {
        this.openedAt = openedAt;
    }
    //</editor-fold>
    //</editor-fold>
}
