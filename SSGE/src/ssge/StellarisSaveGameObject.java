/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Lociatus
 */
public class StellarisSaveGameObject {

    public SaveGameNode gamestate;
    public SaveGameNode meta;
    public String playerID;

    public static enum resource {
        energy,
        minerals,
        food,
        physics_research,
        society_research,
        engineering_research,
        influence,
        unity,
        alloys,
        consumer_goods,
        volatile_motes,
        exotic_gases,
        rare_crystals,
        sr_living_metal,
        sr_zro,
        sr_dark_matter,
        nanites
    }

    public StellarisSaveGameObject(SaveGameNode meta, SaveGameNode gamestate) {
        this.gamestate = gamestate;
        this.meta = meta;
        this.playerID = this.gamestate.keyNode("player").getFirstChild().key("country");
    }

    public List<SaveGameNode> getAllPlanets() {
        List<SaveGameNode> plantes = this.gamestate.getFirstChild("planet").children.get(0).children;
        return plantes;
    }

    public List<SaveGameNode> getAllCounries() {
        List<SaveGameNode> countries = this.gamestate.getFirstChild("country").children.get(0).children;
        countries.removeIf(c -> c.getFirstChild().nodeType != ssge.SaveGameNode.NodeType.LIST);
        return countries;
    }

    public List<SaveGameNode> getAllSpecies() {
        List<SaveGameNode> species = this.gamestate.getFirstChild("species").children.get(0).children;
        return species;
    }

    public SaveGameNode getPlayerCountry() {
        SaveGameNode countries = this.gamestate.getFirstChild("country").children.get(0).getFirstChild(this.playerID);
        return countries;
    }

    public SaveGameNode getCountry(String id) {
        SaveGameNode counries = this.gamestate.getFirstChild("country").children.get(0).getFirstChild(id);
        return counries;
    }

    public SaveGameNode getPlayerTech() {
        SaveGameNode counries = this.getPlayerCountry().getFirstChild().keyNode("tech_status");
        return counries;
    }

    public String[] getCountries() {
        List<String> cList = this.getAllCounries().stream().map(c -> c.getFirstChild().key("name")).collect(Collectors.toList());
        String[] list = new String[cList.size()];
        return cList.toArray(list);
    }

    public List<DropDownItem> getCountriesList() {
        List<DropDownItem> cVec = this.getAllCounries().stream().map(c -> new DropDownItem(c.getFirstChild().key("name"), c.data)).collect(Collectors.toCollection(ArrayList::new));
        return cVec;
    }

    /**
     * Editing Game Info
     */

    public String getVersion() {
        return this.meta.key("version");
    }

    public String getName() {
        return this.meta.key("name");
    }

    public String getMetaDate() {
        return this.meta.key("date").replace("\"", "");
    }

    public String getDLCs() {
        String dlcs = this.meta.keyNode("required_dlcs").children.stream().map(node -> node.data).collect(Collectors.joining("\n"));
        return dlcs;
    }

    /**
     * Editing Country Resources
     */
    public SaveGameNode getPlayerRess() {
        SaveGameNode counries = this.getPlayerCountry().getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources");
        return counries;
    }

    public String getRess(String countryId, String ressType) {
        String ress = "";
        try {
            ress = this.getCountry(countryId).getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").key(ressType);
        } catch (Exception e) {
        }
        return ress;
    }

    public void setPlayerRess(String typ, String value) {
        if (this.getPlayerCountry().getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").isKeyEmpty(typ)) {
            this.getPlayerCountry().getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").addChild(typ, SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY).addChild(value, SaveGameNode.DataType.FLOATNUMBER, SaveGameNode.NodeType.VALUE);

        } else {
            this.getPlayerCountry().getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").keyNode(typ).data = value;
        }
    }

    public void setRess(String country, String typ, String value) {
        if (this.getCountry(country).getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").isKeyEmpty(typ)) {
            this.getCountry(country).getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").addChild(typ, SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY).addChild(value, SaveGameNode.DataType.FLOATNUMBER, SaveGameNode.NodeType.VALUE);

        } else {
            this.getCountry(country).getFirstChild().keyNode("modules").keyNode("standard_economy_module").keyNode("resources").keyNode(typ).data = value;
        }
    }


    /**
     * Editing Ironman
     */
    public boolean isIronman() {
        String metaI = "";
        String gamestateI = "";
        boolean isIronman = false;
        try {
            metaI = this.meta.key("ironman");
        } catch (Exception e) {
        }
        try {
            gamestateI = this.gamestate.keyNode("galaxy").key("ironman");
        } catch (Exception e) {
        }

        if (metaI.equals("yes") && gamestateI.equals("yes")) {
            isIronman = true;
        }
        return isIronman;
    }

    public void setIronman(boolean isIronman) {
        boolean metaIronmanKeyExists = !this.meta.isKeyEmpty("ironman");
        boolean gamestateIronmanKeyExists = !this.gamestate.keyNode("galaxy").isKeyEmpty("ironman");
        String value;
        if (isIronman) {
            value = "yes";
        } else {
            value = "no";
        }
        if (metaIronmanKeyExists) {
            this.meta.keyNode("ironman").data = value;
        } else {
            this.meta.addChild("ironman", SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY).addChild(value, SaveGameNode.DataType.YESNO, SaveGameNode.NodeType.VALUE);
        }

        if (gamestateIronmanKeyExists) {
            this.gamestate.keyNode("galaxy").keyNode("ironman").data = value;
        } else {
            this.gamestate.keyNode("galaxy").addChild("ironman", SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY).addChild(value, SaveGameNode.DataType.YESNO, SaveGameNode.NodeType.VALUE);
        }
    }

    /**
     * Editing Country Modifier
     */
    public List<DropDownItem> getCountryModifierList(String cid) {
        List<DropDownItem> modifiers = this.getCountry(cid).getFirstChild().getAllChilden("timed_modifier").stream().map(m -> new DropDownItem(m.getFirstChild().key("modifier"), m.getFirstChild().key("days"))).collect(Collectors.toCollection(ArrayList::new));
        return modifiers;
    }
    
    public SaveGameNode getContryModifier(String cid, String modifier) {
        SaveGameNode node = null;
        try {
            node = this.getCountry(cid).getFirstChild().getAllChilden("timed_modifier").stream().filter(m->m.getFirstChild().keyNode("modifier").equals(modifier)).findFirst().get();    
        } catch (Exception e) {            
        }
    
        return node;
    }

    public void setCountryModifier(String cid, String modifier, String days) {
        try {
            this.getContryModifier(cid, modifier).getFirstChild().keyNode("days").data = days;
        } catch (Exception e) {
            SaveGameNode node = new SaveGameNode("timed_modifier", SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY);            
            SaveGameNode list = new SaveGameNode("", SaveGameNode.DataType.NULL, SaveGameNode.NodeType.LIST);
            list.addChild("modifier", SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY).addChild(modifier, SaveGameNode.DataType.QUOTEDSTRING, SaveGameNode.NodeType.STRING);
            list.addChild("days", SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY).addChild(days, SaveGameNode.DataType.INTNUMBER, SaveGameNode.NodeType.VALUE);
            node.addChild(list);
            this.getCountry(cid).getFirstChild().addChild(node);
        }
    }
    
    public void delCountryModifier(String cid, String modifier) {
        try {
            this.gamestate.del(this.getContryModifier(cid, modifier));
        } catch (Exception e) {
        }
    }
    
    /**
     * Editing Species Traits
     */    
    public List<DropDownItem> getSpeciesList() {
        List<DropDownItem> sVec = this.getAllSpecies().stream().map(s -> new DropDownItem(s.key("name")+ "-" +s.key("name_list"), s.key("name_list"))).collect(Collectors.toCollection(ArrayList::new));
        return sVec;
    }
    
    public SaveGameNode getSpecies(String name) {
        SaveGameNode node = null;
        try {
            node = this.getAllSpecies().stream().filter(s -> s.key("name_list").contentEquals(name)).findFirst().get();
        } catch (Exception e) {            
        }
        return node;
    }
    
    public List<String> getSpesiesTraitsList(String name) {
        List<String> list = this.getSpecies(name).getFirstChild("traits").getFirstChild().children.stream().map(t -> t.getFirstChild().data).collect(Collectors.toCollection(ArrayList::new));
        return list;
    }
    
    public SaveGameNode getSpesiesTrait(String name, String trait) {
        SaveGameNode node = null;
        try {
            node = this.getSpecies(name).getFirstChild("traits").getFirstChild().children.stream().filter(t -> t.getFirstChild().equals(trait)).findFirst().get();
        } catch (Exception e) {            
        }
        return node;
    }
    
    public void deleteSpeciesTrait(String name, String trait) {
        this.gamestate.del(this.getSpesiesTrait(name, trait));
    }
    
    public void addSpeciesTrait(String name, String trait) {
        if (this.getSpesiesTrait(name, trait)==null) {
            SaveGameNode node = new SaveGameNode("trait", SaveGameNode.DataType.UNQUOTEDSTRING, SaveGameNode.NodeType.KEY);
            node.addChild(trait, SaveGameNode.DataType.QUOTEDSTRING, SaveGameNode.NodeType.STRING);
            this.getSpecies(name).getFirstChild("traits").getFirstChild().addChild(node); 
        }     
    }
    
    public void updateSpeciesTraitsList(List<String> tList, String name) {
        tList.forEach(trait -> this.addSpeciesTrait(name, trait));
        for (String trait:  this.getSpesiesTraitsList(name)) {
            if (!tList.contains(trait)) {
                this.deleteSpeciesTrait(name, trait);
            }
        } 
    }
    
    /**
     * Editing Country Civics
     */    
    public List<String> getCountryCivicList(String cid) {
        return this.getCountry(cid).getFirstChild().keyNode("government").keyNode("civics").children.stream().map(c -> c.data).collect(Collectors.toCollection(ArrayList::new));        
    }
    
    public boolean countryCivicExists(String cid, String civic) {
        return this.getCountry(cid).getFirstChild().keyNode("government").keyNode("civics").children.stream().filter(c -> c.data.equals(civic)).findFirst().isPresent();
    }
    
    public void deleteCountryCivic(String cid, String civic) {
        this.gamestate.del(this.getCountry(cid).getFirstChild().keyNode("government").keyNode("civics").children.stream().filter(c -> c.data.equals(civic)).findFirst().get());
    }
    
    public void addCountryCivic(String cid, String civic) {
        if (!this.countryCivicExists(cid, civic)) {
            SaveGameNode node = new SaveGameNode(civic,SaveGameNode.DataType.QUOTEDSTRING, SaveGameNode.NodeType.VALUE);
            this.getCountry(cid).getFirstChild().keyNode("government").getFirstChild("civics").getFirstChild().addChild(node);
        }
    }
    
    public void updateCountryCivicsList(String cid, List<String> civics) {
        civics.forEach(civic -> this.addCountryCivic(cid, civic));
        for (String civic : this.getCountryCivicList(cid)) {
            if (!civics.contains(civic)) {
                this.deleteCountryCivic(cid, civic);
            }            
        }
    }
}
