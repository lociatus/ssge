/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lociatus
 */
public class SSGLexer {

    /**
     * Enumeration of the Token types and their regular expressions.
     * 
     */
    public static enum TokenType {
        DATE("\\\"\\d+\\.\\d{2}.\\d{2}\\\""),
        FLOATNUMBER("-?\\d+\\.\\d{3,5}"),
        INTNUMBER("-?\\d+[ \\n\\t]"),
        QUOTEDSTRING("\\\"[[\\w][\\p{IsLatin}]'-@][ \\.[\\w][\\p{IsLatin}]'-@]*\\\""),
        EMPTYSTRING("\\\"{2}"),
        YESNO("(yes|no)(?:[ \\s])"),
        UNQUOTEDSTRING("[\\.\\w]+"),
        SETTER("="),
        LISTSTART("\\{"),
        LISTEND("\\}");
        
        public final String pattern;
        
        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }
    
    public static class Token {
        public TokenType type;
        public String data;
        
        /**
         * 
         * @param type The type of the token.
         * @param data The actual data of the token.
         */
        public Token(TokenType type, String data) {
            this.data = data;
            this.type = type;
        }
        
        @Override
        public String toString() {
            return String.format("([%s] %s)", type.name(), data);
        }
        
    }
    /**
     * Goes through all the regular expressions described in tokentype.
     * The order of the regular expressions in tokentype plays a role which 
     * token is recognized.
     * 
     * @param input The string to be convertet into a list of tonken
     * @return The list of recognized token.
     */
    public static LinkedList<Token> lexer(String input) {
        LinkedList<Token> tokenList = new LinkedList<>();
        
        StringBuilder tokenPatternBuffer = new StringBuilder();
        for (TokenType tokenType: TokenType.values()) {
            tokenPatternBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }        
        Pattern tokenPatterns = Pattern.compile(tokenPatternBuffer.substring(1));
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            if (matcher.group(TokenType.INTNUMBER.name()) != null) {
                tokenList.add(new Token(TokenType.INTNUMBER, matcher.group(TokenType.INTNUMBER.name())));
                continue;                
            } else if (matcher.group(TokenType.FLOATNUMBER.name()) != null) {
                tokenList.add(new Token(TokenType.FLOATNUMBER, matcher.group(TokenType.FLOATNUMBER.name())));
                continue;                
            } else if (matcher.group(TokenType.UNQUOTEDSTRING.name()) != null) {
                tokenList.add(new Token(TokenType.UNQUOTEDSTRING, matcher.group(TokenType.UNQUOTEDSTRING.name())));
                continue;                
            } else if (matcher.group(TokenType.QUOTEDSTRING.name()) != null) {
                tokenList.add(new Token(TokenType.QUOTEDSTRING, matcher.group(TokenType.QUOTEDSTRING.name())));
                continue;              
            } else if (matcher.group(TokenType.EMPTYSTRING.name()) != null) {
                tokenList.add(new Token(TokenType.EMPTYSTRING, matcher.group(TokenType.EMPTYSTRING.name())));
                continue;              
            } else if (matcher.group(TokenType.YESNO.name()) != null) {
                tokenList.add(new Token(TokenType.YESNO, matcher.group(TokenType.YESNO.name())));
                continue;               
            } else if (matcher.group(TokenType.DATE.name()) != null) {
                tokenList.add(new Token(TokenType.DATE, matcher.group(TokenType.DATE.name())));
                continue;                
            } else if (matcher.group(TokenType.SETTER.name()) != null) {
                tokenList.add(new Token(TokenType.SETTER, matcher.group(TokenType.SETTER.name())));
                continue;                
            } else if (matcher.group(TokenType.LISTSTART.name()) != null) {
                tokenList.add(new Token(TokenType.LISTSTART, matcher.group(TokenType.LISTSTART.name())));
                continue;                
            } else if (matcher.group(TokenType.LISTEND.name()) != null) {
                tokenList.add(new Token(TokenType.LISTEND, matcher.group(TokenType.LISTEND.name())));
                continue;                
            }
        }
        
        return tokenList;
    }
    
}
