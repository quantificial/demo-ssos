package lc.cds.ui.demo;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/** Main view with a menu (with declarative layout design) */
@DesignRoot
public class MainView extends VerticalLayout implements View {
		
	Navigator navigator;
	
    // Menu navigation button listener
    class ButtonListener implements Button.ClickListener {
        String menuitem;
        public ButtonListener(String menuitem) {
            this.menuitem = menuitem;
        }

        @Override
        public void buttonClick(ClickEvent event) {
            // Navigate to a specific state
            navigator.navigateTo("main" + "/" + menuitem);
        }
    }

    VerticalLayout menuContent;
    Panel equalPanel;
    Button logout;

    public MainView(Navigator navigator) {
        //Design.read(this);
    	//Design.read("/lc/cds/ui/view/template/MainView.html",this);
    	//Design.read("/ui/template/MainView.html",this);
    	Design.read("MainView.html",this);
        
        this.navigator = navigator;

        menuContent.addComponent(new Button("Pig", new ButtonListener("pig")));
        menuContent.addComponent(new Button("Cat", new ButtonListener("cat")));
        menuContent.addComponent(new Button("Dog", new ButtonListener("dog")));
        menuContent.addComponent(new Button("Reindeer", new ButtonListener("reindeer")));
        menuContent.addComponent(new Button("Penguin", new ButtonListener("penguin")));
        menuContent.addComponent(new Button("Sheep", new ButtonListener("sheep")));

        // Allow going back to the start
        logout.addClickListener(event ->
            navigator.navigateTo(""));
    }

    @DesignRoot
    class AnimalViewer extends VerticalLayout {
        Label watching;
        Embedded pic;
        Label back;

        public AnimalViewer(String animal) {
            Design.read("AnimalViewer.html",this);

            watching.setValue("You are currently watching a " + animal);
            pic.setSource(new ThemeResource("img/" + animal + "-128px.png"));
            back.setValue("and " + animal + " is watching you back");
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	
    	System.out.println("enter....");    	
    	
        if (event.getParameters() == null
            || event.getParameters().isEmpty()) {
            equalPanel.setContent(
                new Label("Nothing to see here, " +
                          "just pass along."));
            return;
        } else
            equalPanel.setContent(new AnimalViewer(
                event.getParameters()));
    }
}