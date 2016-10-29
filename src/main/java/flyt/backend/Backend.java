package flyt.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Lists;
import flyt.common.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fredrik
 */
public class Backend {

    public static final String DATA_DIRECTORY = "data/";

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
        mapper.registerModule( new JodaModule() );
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
            List<User> users = mapper.readValue( new File( DATA_DIRECTORY + "users.json"), mapper.getTypeFactory().constructCollectionType( List.class, User.class ) );
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

    /**
     * Get data form a datafile
     * @param dataFile name of datafile
     * @return data for matching datafile
     * @throws FlytException on error
     */
    public Data getData(String dataFile ) throws FlytException {
        try {
            Data data = mapper.readValue(new File(DATA_DIRECTORY + dataFile), Data.class);
            return data;
        } catch ( IOException ioe ) {
            throw new FlytException( "ioerror: " + ioe.getMessage() );
        }
    }

    /**
     * Get data for a specific server
     * @param serverId server/source id
     * @return a list of data belonging to server
     * @throws FlytException on error
     */
    public List<Data> getDataForServerId( String serverId ) throws FlytException {
        List<Data> validData = Lists.newArrayList();
        for ( String dataFile : getDataList() ) {
            Data data = getData( dataFile );
            if ( data.header.senderId.equals( serverId ) ) {
                validData.add( data );
            }
        }
        return validData;
    }

    /**
     * Get a list of all available data files
     * @return a list of data files, never null
     */
    public List<String> getDataList() {
        List<String> files = Lists.newArrayList();
        File dir = new File( DATA_DIRECTORY );
        for ( File file : dir.listFiles() ) {
            if ( file.getName().startsWith( "data" ) ) {
                files.add( file.getName() );
            }
        }
        return files;
    }

    /**
     * Return a list of customers
     * @return a list of customers
     * @throws FlytException on error
     */
    public List<Customer> getCustomers() throws FlytException {
        try {
            List<Customer> customers = mapper.readValue(new File(DATA_DIRECTORY + "customers.json"), mapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
            return customers;
        } catch ( IOException ioe ) {
            throw new FlytException( "ioe: " + ioe.getMessage() );
        }
    }

}
