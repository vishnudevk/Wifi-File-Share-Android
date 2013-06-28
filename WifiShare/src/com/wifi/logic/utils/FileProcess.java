package com.wifi.logic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

public class FileProcess {
    
    public static byte[] getBytes( File path ) throws IOException {
        
        InputStream inStream = new FileInputStream( path );
        long length = path.length();
        byte[] file = new byte[ (int) length ];
        
        
        int offset = 0, numRead = 0;        
        while ( offset < file.length && ( numRead = inStream.read( file, offset, file.length - offset ) ) > -1 ) {
            offset += numRead;
        }
        
        if (offset < file.length) {
            throw new IOException( "Error: A problem occurs while fetching the file!" );
        }
        
        inStream.close();
        return file;
    }
    
    public static void makeFile ( String path, byte[] file ) {
        
        try {
            
            FileOutputStream outStream = new FileOutputStream( path );
            outStream.write( file );
            outStream.close();
            
        } catch ( FileNotFoundException ea ) {
            System.out.println( "Error: " + ea );
        } catch ( IOException eb ) {
            System.out.println( "Error: " + eb );
        }
    }
}
