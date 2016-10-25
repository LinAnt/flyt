package flyt.backend;

/**
 * @author Fredrik
 */
public class Backend {

    /**
     * Get single instance of Backend, not threadsafe yet
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
     */
    public boolean authenticateUser( String username, String password ) {
        return true;
    }


}
