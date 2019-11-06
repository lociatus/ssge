/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;

/**
 *
 * @author Lociatus
 */
public class SaveGameParameter implements Serializable {
    private List<String> planetClasses;
    private List<String> planetaryFeatures;
    private List<String> planetModifiers;
    private List<String> empireModifier;
    private List<String> speciesTraits;
    private List<String> leaderTraits;
    private List<String> roulerTraits;
    private List<String> civicsList;
    
    public SaveGameParameter(String path) throws IOException, ParsingException {
        File parameterFile = new File(path);
        Builder builder =  new Builder();
        Document doc = builder.build(parameterFile);
        this.planetClasses = this.loadNode(doc, "planetClasses", "class");
        this.planetaryFeatures = this.loadNode(doc, "planetaryFeatures", "feature");
        this.planetModifiers = this.loadNode(doc, "planetModifiers", "modifier");
        this.empireModifier = this.loadNode(doc, "empireModifier", "modifier");
        this.speciesTraits = this.loadNode(doc, "speciesTraits", "trait");
        this.leaderTraits = this.loadNode(doc, "leaderTraits", "trait");        
        this.roulerTraits = this.loadNode(doc, "roulerTraits", "trait");         
        this.civicsList = this.loadNode(doc, "civicsList", "civic");        
    }
    
    private List<String> loadNode(Document doc, String nodeName, String itemName) {
        List<String> list = new ArrayList();
        doc.getRootElement().getFirstChildElement(nodeName).getChildElements().forEach(e -> list.add(e.getValue()));
        return list;
    }
    
    public List<String> getPlanetClasses() {
        return planetClasses;
    }

    public List<String> getPlanetaryFeatures() {
        return planetaryFeatures;
    }

    public List<String> getPlanetModifiers() {
        return planetModifiers;
    }

    public List<String> getEmpireModifier() {
        return empireModifier;
    }

    public List<DropDownItem> getEmpireModifierDD() {
        return this.empireModifier.stream().map(m -> new DropDownItem(m, "")).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<String> getSpeciesTraits() {
        return speciesTraits;
    }

    public List<String> getLeaderTraits() {
        return leaderTraits;
    }

    public List<String> getRoulerTraits() {
        return roulerTraits;
    }

    public List<String> getCivicsList() {
        return civicsList;
    }
}
