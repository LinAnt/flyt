package flyt.backend;

import flyt.common.AccessDenied;
import org.junit.Assert;
import org.junit.Test;

public class TestBackend {

    @Test
    public void testAuthenticate() {
        Backend backend = Backend.getInstance();
        try {
            Assert.assertTrue( backend.authenticateUser( "admin", "admin") );
            Assert.assertTrue( backend.authenticateUser( "user", "user") );
        } catch ( AccessDenied ad ) {
            System.out.println( ad.getMessage() );
            Assert.fail();
        }
        try {
            backend.authenticateUser( "foo", "bar" );
            Assert.fail();
        } catch ( AccessDenied ad ) {
            // should happen
        }
    }
}

