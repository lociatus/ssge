/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import static ssge.SSGLexer.lexer;
import static ssge.SSGParser.parser;

/**
 *
 * @author Lociatus
 */
public class IOSaveGameFile {
    

    public String saveGameFilePath;
    public String newSaveGameFilePath;
    public String gamestatePath;
    public String gamestateString;
    public SaveGameNode gameStateSGN;
    public String metaPath;
    public String metaString;
    public SaveGameNode metaSGN;
    private boolean isSavegameLoadet;

    public IOSaveGameFile() {
    }

    public String getGamestate() {
        return gamestateString;
    }

    public String getMeta() {
        return metaString;
    }

    public void SGNToString() {
        this.gamestateString = this.gameStateSGN.toString();
        this.metaString = this.metaSGN.toString();
    }

    public void stringToSGN() {
        this.gameStateSGN = parser(lexer(this.gamestateString));
        this.metaSGN = parser(lexer(this.metaString));
        this.isSavegameLoadet = true;
    }

    public boolean isIsSavegameLoadet() {
        return isSavegameLoadet;
    }
    
/**
 * Saves the data as a textfile at a dedicatat path.
 * 
 * @param textFilePath
 * @param data 
 */
    void saveTextFile(String textFilePath, String data) {
        File saveGameFile = new File(textFilePath);
        if (saveGameFile.exists()) {
            System.out.println("new saveGameFile exists allready");
        }
        try {
            FileOutputStream fos = new FileOutputStream(saveGameFile);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(IOSaveGameFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Saves the savegame as a zip-file compatible with Stellaris
     */
    public void saveZipFile() {
        File saveGameFile = new File(this.newSaveGameFilePath);
        if (saveGameFile.exists()) {
            System.out.println("new saveGameFile exists allready");
        }
        try {
            System.out.println("speichen, größe gamestate:" + this.gamestateString.length());
            System.out.println("speichen, größe meta:" + this.metaString.length());
            FileOutputStream fos = new FileOutputStream(newSaveGameFilePath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            includeStringToZipFile(this.gamestateString, "gamestate", zipOut);
            includeStringToZipFile(this.metaString, "meta", zipOut);
            zipOut.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(IOSaveGameFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The step of saving a single file in a Zip-file.
     * @param string The data zu store
     * @param fileName The filename of the data in the Zipfile
     * @param zipOut The Zip-output-stream to store the file into
     * @throws IOException 
     */
    private void includeStringToZipFile(String string, String fileName, ZipOutputStream zipOut) throws IOException {
        InputStream metaStream = new ByteArrayInputStream(string.getBytes());
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = metaStream.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

    }
    
    /**
     * Load the Savegame as two text files 
     */
    public void loadTextFiles() {
        File saveGameFileGamestate = new File(this.gamestatePath);
        File saveGameFileMeta = new File(this.metaPath);
        if (saveGameFileMeta.exists() && saveGameFileGamestate.exists()) {
            System.out.println("saveGameFile exists");
        } else {
            System.out.println("saveGameFile missing");
        }
        try {
            this.gamestateString = loadSingelTextFile(saveGameFileGamestate);
            this.metaString = loadSingelTextFile(saveGameFileMeta);
        } catch (IOException ex) {
            Logger.getLogger(IOSaveGameFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The step of loading a single text file.
     * 
     * @param file The to load the data from.
     * @return The content of the file as string
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private String loadSingelTextFile(File file) throws FileNotFoundException, IOException {
        StringBuilder fileBuilder = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        Reader reader = new BufferedReader(new InputStreamReader(fis));
        int c;
        while ((c = reader.read()) != -1) {
            fileBuilder.append((char) c);
        }
        fis.close();
        return fileBuilder.toString();
    }

    /**
     * Load Savegame from a Zipfile
     */
    public void loadZipFile() {
        File saveGameFile = new File(this.saveGameFilePath);
        if (saveGameFile.exists()) {
            System.out.println("saveGameFile exists");
        } else {
            System.out.println("saveGameFile missing");
        }
        try {
            ZipFile zf = new ZipFile(this.saveGameFilePath);
            ZipEntry gamestateFile = zf.getEntry("gamestate");
            ZipEntry metaFile = zf.getEntry("meta");
            if (gamestateFile != null && gamestateFile != null) {
                InputStream gis = zf.getInputStream(gamestateFile);
                this.gamestateString = extractSingelFileFromZip(gis);
                InputStream mis = zf.getInputStream(metaFile);
                this.metaString = extractSingelFileFromZip(mis);
                System.out.println("lade, größe gamestate:" + this.gamestateString.length());
                System.out.println("lade, größe meta:" + this.metaString.length());
            }
        } catch (IOException ex) {
            Logger.getLogger(IOSaveGameFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The step of loading a single file out of a zip savegame file
     * @param fis The file input stream
     * @return The content o the file as String.
     * @throws IOException 
     */
    private String extractSingelFileFromZip(InputStream fis) throws IOException {
        StringBuilder fileBuilder = new StringBuilder();
        Reader reader = new BufferedReader(new InputStreamReader(fis));
        int c = 0;
        while ((c = reader.read()) != -1) {
            fileBuilder.append((char) c);
        }
        return fileBuilder.toString();
    }
}
