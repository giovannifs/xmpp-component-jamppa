package component.uppercase;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jamppa.component.XMPPComponent;
import org.xmpp.component.ComponentException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, ComponentException {
		
		XMPPComponent xmppComponent = new XMPPComponent("uppercase.test.com", "password", "127.0.0.1", 5347);
		xmppComponent.setDescription("Uppercase component");
		xmppComponent.setName("uppercase");
//		xmppComponent.setDiscoInfoIdentityCategory("Friend finder");
//		xmppComponent.setDiscoInfoIdentityCategoryType("Directory");
		xmppComponent.connect();
		xmppComponent.process();
		System.out.println("Após o run");
		
		UppercaseHandler uppercaseHandler = new UppercaseHandler();
		
		xmppComponent.addGetHandler(uppercaseHandler);
		xmppComponent.addSetHandler(uppercaseHandler);
		
		System.out.println("Última ");		
	}

}
