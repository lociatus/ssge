/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

import java.util.LinkedList;
import ssge.SSGLexer.Token;

/**
 *
 * @author Lociatus
 */
public class SSGParser {
    
    /**
     * The starting point to create the savegame as a tree of nodes.
     * 
     * @param tokenList The list of tokens to interpret 
     * @return The rootnode of the hole savegame
     */
    public static SaveGameNode parser(LinkedList<Token> tokenList) {
        SaveGameNode root = new SaveGameNode(null, null, ssge.SaveGameNode.NodeType.ROOTLIST);
        while (tokenList.size()>0) {    
            root.addChild(parserStep(tokenList));
        }
        return root;                
    }
    /**
     * A single step to interpret the list of tokens.
     * 
     * @param tokenList The list of the next token.
     * @return Node
     */
    private static SaveGameNode parserStep(LinkedList<Token> tokenList) {
        SaveGameNode node;
        SaveGameNode.DataType dataType;
        SaveGameNode.NodeType nodeType = ssge.SaveGameNode.NodeType.VALUE;
        boolean isLast = false;
        Token token = tokenList.pop();
        Token next;
        if (tokenList.isEmpty()) {
            isLast = true;
        }        
        
        switch (token.type) {
            case INTNUMBER: 
                token.data = token.data.trim();
                node = parserCreateNode(SaveGameNode.DataType.INTNUMBER, nodeType, token, tokenList);
                break;
            case FLOATNUMBER: 
                node = parserCreateNode(SaveGameNode.DataType.FLOATNUMBER, nodeType, token, tokenList);
                break;
            case DATE:
                node = parserCreateNode(SaveGameNode.DataType.DATE, nodeType, token, tokenList);
                break;
            case EMPTYSTRING:
            case QUOTEDSTRING: 
                token.data = token.data.substring(1, token.data.length()-1);
                node = parserCreateNode(SaveGameNode.DataType.QUOTEDSTRING, ssge.SaveGameNode.NodeType.STRING, token, tokenList);
                break;
            case YESNO:
                token.data = token.data.trim();
                node = parserCreateNode(SaveGameNode.DataType.YESNO, nodeType, token, tokenList);
                break;
            case UNQUOTEDSTRING: 
                node = parserCreateNode(SaveGameNode.DataType.UNQUOTEDSTRING, nodeType, token, tokenList);
                break;
            case LISTSTART:
                dataType=SaveGameNode.DataType.NULL;
                nodeType=SaveGameNode.NodeType.LIST;
                node = new SaveGameNode(null, dataType ,nodeType);
                next = tokenList.pop();
                while (next.type!=ssge.SSGLexer.TokenType.LISTEND){
                    tokenList.push(next);                    
                    node.addChild(parserStep(tokenList));
                    next = tokenList.pop();
                }                
                break;
            default:
                throw new AssertionError();
        }        
        return node;                
    }
    
    /**
     * Creates a save game node.
     * In order to create a node it is necessary to look in the next token.
     * 
     * @param dataType The type of the data as defined in SaveGameNode.
     * @param nodeType The type of the node as defined in SaveGameNode.
     * @param token The current token.
     * @param tokenList The list of the next token.
     * @return 
     */
    private static SaveGameNode parserCreateNode(SaveGameNode.DataType dataType, SaveGameNode.NodeType nodeType, Token token, LinkedList<Token> tokenList){
        SaveGameNode node = new SaveGameNode(token.data, dataType ,nodeType);
        if (!tokenList.isEmpty()) {
            Token next = tokenList.pop();
            if (next.type == ssge.SSGLexer.TokenType.SETTER) {
                node.nodeType=ssge.SaveGameNode.NodeType.KEY;
                node.addChild(parserStep(tokenList));
            } else {
                tokenList.push(next);
            }
        }
        return node;
    }    
}
