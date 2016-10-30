package flyt.common;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class MainDesign extends HorizontalLayout {
    protected HorizontalLayout menuTitle;
    protected Label menuTitleLabel;
    protected Button menuToggle;
    protected CssLayout menuItems;
    protected Button userButton;
    protected Button statusButton;
    protected Button logoutButton;
    protected VerticalLayout content;
    protected Label linechart_container;

    public MainDesign() {
        Design.read(this);
    }
}
