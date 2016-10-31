package flyt.common;

/**
 * Created by acl on 10/31/16.
 */
public class customerFormView extends customerForm {

    public customerFormView(String cust){
        System.out.println(cust);
        serverList.setNullSelectionAllowed(false);
        customerName.setValue("Byggis Inc");
    }
}
