/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Lociatus
 */
public class SaveGameNode implements Iterable<SaveGameNode> {


    public String data;
    public SaveGameNode parent;
    public NodeType nodeType;
    public DataType dataType;

    public static enum NodeType {
        ROOTLIST, LIST, KEY, VALUE, STRING
    }

    public static enum DataType {
        INTNUMBER, FLOATNUMBER, QUOTEDSTRING, UNQUOTEDSTRING, YESNO, NULL, LIST, DATE
    }
    public List<SaveGameNode> children;

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isList() {
        return nodeType == ssge.SaveGameNode.NodeType.LIST;
    }

    public boolean isIntNumber() {
        return dataType == ssge.SaveGameNode.DataType.INTNUMBER;
    }

    public boolean isFloatNumber() {
        return dataType == ssge.SaveGameNode.DataType.FLOATNUMBER;
    }

    private List<SaveGameNode> elementsIndex;
    
    public SaveGameNode(String data, DataType dataType, NodeType nodeType) {
        this.data = data;
        this.nodeType = nodeType;
        this.dataType = dataType;
        this.children = new LinkedList<SaveGameNode>();
        this.elementsIndex = new LinkedList<SaveGameNode>();
        this.elementsIndex.add(this);

    }

    public SaveGameNode(String data, DataType dataType) {
        this.data = data;
        this.dataType = dataType;
        this.children = new LinkedList<SaveGameNode>();
        this.elementsIndex = new LinkedList<SaveGameNode>();
        this.elementsIndex.add(this);

    }

    public SaveGameNode addChild(SaveGameNode childNode) {
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public SaveGameNode addChild(String child, DataType dataType) {
        SaveGameNode childNode = new SaveGameNode(child, dataType);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public SaveGameNode addChild(String child, DataType dataType, NodeType nodeType) {
        SaveGameNode childNode = new SaveGameNode(child, dataType, nodeType);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        int level;
        if (this.isRoot()) {
            level = 0;
        } else if (this.isList()) {
            level = parent.getLevel() + 1;
        } else {
            level = parent.getLevel();
        }
        return level;
    }

    private void registerChildForSearch(SaveGameNode node) {
        elementsIndex.add(node);
        if (parent != null) {
            parent.registerChildForSearch(node);
        }
    }

    private void delChildForSearch(SaveGameNode node) {
        elementsIndex.remove(node);
        if (parent != null) {
            parent.delChildForSearch(node);
        }
    }
    
    public void del(SaveGameNode node) {
        node.delChildForSearch(node);
        node.parent.children.remove(node);        
    }

    public SaveGameNode findTreeNode(Comparable cmp) {
        for (SaveGameNode element : this.elementsIndex) {
            String elData = element.data;
            if (cmp.compareTo(elData) == 0) {
                return element;
            }
        }

        return null;
    }
    
    /**
     * Retrun the first child key node with a certain name of this node.
     * @param name
     * @return 
     */
    public SaveGameNode getFirstChild(String name) {
        SaveGameNode node;
        node = this.children.stream().filter(child -> child.equals(name)).findFirst().get();
        return node;
    }
    
    /**
     * Get the first child node of a node, can also be a list.
     * @return 
     */
    public SaveGameNode getFirstChild() {
        SaveGameNode node;
        node = this.children.get(0);
        return node;
    }
    
    /**
     * Retrun all the child key nodes with a certain name of this node.
     * @param name
     * @return 
     */
    public List<SaveGameNode> getAllChilden(String name) {
        List<SaveGameNode> list = new ArrayList();
        list.addAll(this.children.stream().filter(child -> child.equals(name)).collect(Collectors.toList()));
        return list;
    }
    
    /**
     * Returns 
     * @param name
     * @return 
     */
    public SaveGameNode keyNode(String name) {
        SaveGameNode node;
        node = this.children.stream().filter(child -> child.equals(name)).findFirst().get().children.get(0);
        return node;
    }
    
    /**
     * Return the data of the first child of a certain named key node
     * returns  "c" for the node (keynode)b=(valuenode)c.
     * @param name
     * @return 
     */
    public String key(String name) {
        String value;
        value = this.children.stream().filter(child -> child.equals(name)).findFirst().get().children.get(0).data;
        return value;
    }

    public boolean isKeyEmpty(String name) {
        boolean value = true;
        long count = this.children.stream().filter(child -> child.equals(name)).count();
        if (count >= 1) {
            value = false;
        }
        return value;
    }
    
    /**
     * Compare the data of a node to a string.
     * @param data The string to compare to
     * @return 
     */
    public boolean equals(String data) {
        boolean equal = false;
        try {
            equal = this.data.equals(data);
        } catch (Exception e) {

        }
        return equal;
    }

    public boolean isOffspringOf(String data) {
        boolean isOffspringOf = false;
        if (!(this.nodeType == ssge.SaveGameNode.NodeType.ROOTLIST)) {
            if (this.parent.data != null && this.parent.data.equals(data)) {
                isOffspringOf = true;
            } else {
                isOffspringOf = this.parent.isOffspringOf(data);
            }
        }
        return isOffspringOf;
    }
    
    /**
     * Create the correct amount of tabs for the conversion to a string
     * 
     * @param level The number of tabs
     * @return the amount of tabs as string
     */
    private String tabulatorString(int level) {
        String tabs = "";
        for (int i = 0; i < level; i++) {
            tabs += "\t";
        }
        return tabs;
    }

    /**
     * The node-path from the Rootnode to this node.
     * 
     * @return The path as a String
     */
    public String nodePath() {
        String path;
        if (this.parent == null) {
            path = "(" + this.nodeType + "|" + this.dataType + "|" + this.data + ")";
        } else {
            path = this.parent.nodePath() + " (" + this.nodeType + "|" + this.dataType + "|" + this.data + ")";
        }
        return path;
    }

    @Override
    public String toString() {
        String childString;
        String s;
        String tabs = tabulatorString(this.getLevel());
        switch (this.nodeType) {
            case ROOTLIST:
                s = this.children.stream().map(child -> child.toString()).collect(Collectors.joining());
                break;
            case LIST:
                boolean isPolicyFlags = false;
                boolean isModules = false;
                boolean isBuilding = false;
                boolean isRandomList = false;
                boolean isVariables = false;
                boolean isStoredTechpointsForTech = false;
                if (this.parent.nodeType != ssge.SaveGameNode.NodeType.LIST) {
                    isRandomList = this.parent.data.equalsIgnoreCase("random");
                    isBuilding = this.parent.data.equalsIgnoreCase("buildings");
                    isPolicyFlags = this.parent.data.equalsIgnoreCase("policy_flags");
                    isVariables = this.parent.data.equalsIgnoreCase("variables");
                    isStoredTechpointsForTech = this.parent.data.equalsIgnoreCase("stored_techpoints_for_tech");
                    if (this.isOffspringOf("starbases")) {
                        isModules = this.parent.data.equalsIgnoreCase("modules");
                    }
                }
                boolean isIntList = this.children.stream().allMatch(child -> (child.dataType == ssge.SaveGameNode.DataType.INTNUMBER) && child.children.isEmpty());
                boolean isFloatList = this.children.stream().allMatch(child -> (child.dataType == ssge.SaveGameNode.DataType.FLOATNUMBER) && child.children.isEmpty());
                boolean isStringList = this.children.stream().allMatch(child -> (child.dataType == ssge.SaveGameNode.DataType.QUOTEDSTRING) && child.children.isEmpty());
                boolean isYesNoList = this.children.stream().allMatch(child -> (child.dataType == ssge.SaveGameNode.DataType.YESNO) && child.children.isEmpty());
                boolean isKeyList = this.children.stream().allMatch(child -> (child.dataType == ssge.SaveGameNode.DataType.UNQUOTEDSTRING) && child.children.isEmpty());
                String parentIsNoKeyTabs = "";
                // Set correct number ob tabs for "}"
                if (this.parent.nodeType != ssge.SaveGameNode.NodeType.KEY) {
                    parentIsNoKeyTabs = tabulatorString(this.getLevel() - 1);
                }
                if (isRandomList) {
                    childString = this.children.stream().map(child -> child.data).collect(Collectors.joining(" "));
                    s = "{ " + childString + " }\n";
                    break;
                } else if (isModules) {
                    childString = this.children.stream().map(child -> child.toString().replace("\n", "\t")).collect(Collectors.joining("\t"));
                    s = "{\n" + parentIsNoKeyTabs + childString + " }\n";
                    break;
                } else if (isBuilding) {
                    childString = this.children.stream().map(child -> child.toString().replace("\n", "\t")).collect(Collectors.joining("\t"));
                    s = "{\n" + parentIsNoKeyTabs + childString + " }\n";
                    break;
                } else if (isPolicyFlags) {
                    childString = this.children.stream().map(child -> child.data).collect(Collectors.joining(" ", tabs, "\n"));
                } else if ((isIntList || isFloatList || isYesNoList) && !this.children.isEmpty()) {
                    childString = this.children.stream().map(child -> child.data).collect(Collectors.joining(" ", tabs, "\n"));
                } else if (isStringList && !this.children.isEmpty()) {
                    childString = this.children.stream().map(child -> "\"" + child.data + "\"").collect(Collectors.joining("\n" + tabs, tabs, "\n"));
                } else if (isKeyList && !this.children.isEmpty()) {
                    childString = this.children.stream().map(child -> child.data).collect(Collectors.joining("\n" + tabs, tabs, "\n"));
                } else if (isVariables || isStoredTechpointsForTech) {
                    childString = this.children.stream().map(child -> child.toString()).collect(Collectors.joining()).replace("=", "\n=");
                } else {
                    childString = this.children.stream().map(child -> child.toString()).collect(Collectors.joining());
                }
                tabs = tabulatorString(this.getLevel() - 1);
                s = parentIsNoKeyTabs + "{\n" + childString + tabs + "}\n";
                break;
            case KEY:
                childString = this.children.stream().map(child -> child.toString()).collect(Collectors.joining());
                if (this.dataType == ssge.SaveGameNode.DataType.QUOTEDSTRING) {
                    s = tabs + "\"" + this.data + "\"=" + childString;
                } else {

                    s = tabs + this.data + "=" + childString;
                }
                break;
            case STRING:
                s = "\"" + this.data + "\"\n";
                break;
            default:
                s = this.data + "\n";
        }
        return s;
    }

    @Override
    public Iterator<SaveGameNode> iterator() {
        SaveGameNodeIter iter = new SaveGameNodeIter(this);
        return iter;
    }
    
}
