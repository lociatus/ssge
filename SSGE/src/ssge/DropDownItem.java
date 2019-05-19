/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

/**
 *
 * @author Lociatus
 */
public class DropDownItem {
    private final String text;
    private final String id;

    public DropDownItem(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return text;
    }    
}
