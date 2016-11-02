package flyt.backend;

import com.google.common.collect.Lists;
import flyt.common.AccessDenied;
import flyt.common.Customer;
import flyt.common.FlytException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestBackend {

    @Test
    public void testAuthenticate() {
        Backend backend = Backend.getInstance();
        try {
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

    @Test
    public void testCustomer() {
        Backend backend = Backend.getInstance();
        try {
            List<Customer> allCustomers = backend.getCustomers();
            if ( backend.contains( "test", allCustomers ) ) {
                backend.removeCustomer( "test" );
            }
            if ( backend.contains( "blah", allCustomers ) ) {
                backend.removeCustomer( "blah" );
            }
            allCustomers = backend.getCustomers();
            Assert.assertFalse( backend.contains( "test", allCustomers ) );
            Customer c = new Customer();
            c.name = "test";
            c.servers = Lists.newArrayList();
            backend.addCustomer( c );
            allCustomers = backend.getCustomers();
            Assert.assertTrue( backend.contains( "test", allCustomers ) );
            Customer d = new Customer();
            d.name = "blah";
            d.servers = Lists.newArrayList();
            backend.updateCustomer( "test", d );
            allCustomers = backend.getCustomers();
            Assert.assertTrue( backend.contains( "blah", allCustomers ) );
            backend.removeCustomer( "blah" );
        } catch ( FlytException fe ) {
            Assert.fail();
        }

    }
}

