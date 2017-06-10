package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Defines the loading mechanism for restoring
 * a previous state of a server.
 * Created by pauliuskalv on 5/20/17.
 */
public class load {
    public load( ) {

    }

    /**
     * Defines the function, which reads a specific file, called
     * "server.ser" in a local directory "/backup".
     * @return
     * Returns an object of server type if loading from file was successful
     * Return null if there were problems restoring the server from the file
     */
    public server load ( ) {
        server serveris = null ;
        File failas = new File ( System.getProperty ( "user.dir"  ) + "/backup/server.ser" ) ;
        try {
            FileInputStream fileIn = new FileInputStream ( failas ) ;
            ObjectInputStream in = new ObjectInputStream ( fileIn ) ;
            serveris = (server) in.readObject() ;
            in.close ( ) ;
            fileIn.close ( ) ;
        } catch ( IOException e ) {
            System.out.println ( "Ivyko klaida bandant atkurti prekių inventorių!" ) ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return serveris ;
    }
}
