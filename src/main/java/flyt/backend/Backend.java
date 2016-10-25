package flyt.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import flyt.common.AccessDenied;
import flyt.common.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Fredrik
 */
public class Backend {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Get single instance of Backend, not yet threadsafe
     * @return the Backend
     */

    private static Backend instance = null;

    public static Backend getInstance() {
        if ( instance == null ) {
            instance = new Backend();
        }
        return instance;
    }

    private Backend() {

    }

    /**
     * Authenticate user
     * @param username A valid username
     * @param password A valid password
     * @return returns true if access is granted
     * @throws AccessDenied when issues
     */
    public boolean authenticateUser( String username, String password ) throws AccessDenied {
        try {
            System.out.println( "blah" );
            List<User> users = mapper.readValue( new File( "data/users.json"), mapper.getTypeFactory().constructCollectionType( List.class, User.class ) );
            System.out.println( "tjo" );
            for ( User user : users ) {
                if ( user.username.equals( username ) && user.password.equals( password ) ) {
                    return true;
                }
            }
        } catch ( IOException ioe ) {
            System.out.println( ioe.getMessage() );
            throw new AccessDenied( "Unable to open data/users.json" );
        }
        throw new AccessDenied( "Username and password not valid" );
    }


}
