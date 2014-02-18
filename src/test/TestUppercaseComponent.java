package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.jamppa.client.XMPPClient;
import org.jamppa.component.XMPPComponent;
import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmpp.component.ComponentException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.IQ.Type;
import org.xmpp.packet.PacketError.Condition;

import component.uppercase.UppercaseHandler;

public class TestUppercaseComponent {
		
	private static final String UPPERCASE_COMPONENT_URL = "uppercase.test.com";
	XMPPClient client;
	XMPPComponent xmppComponent;
	
//	@BeforeClass
//	public void setUpComponent(){
//		//initializing component
//		xmppComponent = new XMPPComponent("uppercase.test.com", 
//				"password", "127.0.0.1", 5347);
//		xmppComponent.setDescription("Uppercase component");
//		xmppComponent.setName("uppercase");
//		try {
//			xmppComponent.connect();
//			xmppComponent.process();
//			System.out.println("Após o run");
//			
//			UppercaseHandler uppercaseHandler = new UppercaseHandler();
//			
//			xmppComponent.addGetHandler(uppercaseHandler);
//			xmppComponent.addSetHandler(uppercaseHandler);
//			
//		} catch (ComponentException e1) {			
//			e1.printStackTrace();
//			fail("Component set up problem!");
//		}
//	}
	
	
	@Before
	public void setUp(){
		
		//initializing client
		client = new XMPPClient("user1@test.com", 
				"user1", "localhost", 5222);
		
		try {
			client.connect();
			client.login();
			client.process(false);
		} catch (XMPPException e) {
			e.printStackTrace();
			fail("Client set up problem!");
		}		
	}
	
	@Test
	public void testLowercaseContent() {
		
		String content = "hello world";
		
		IQ iq = new IQ(Type.get);
		iq.setTo(UPPERCASE_COMPONENT_URL);
		iq.getElement()
				.addElement("query", "uppercase")
				.addElement("content")
				.setText(content);
		
		IQ response;
		try {
			response = (IQ) client.syncSend(iq);
			String uppercaseReturn = response.getElement()
												.element("query")
												.element("content")
												.getText();
			
			assertEquals(content.toUpperCase(), uppercaseReturn);
			
		} catch (XMPPException e) {
			e.printStackTrace();
			fail("Response problem!");
		}		
	}

	
	@Test
	public void testUppercaseContent() {
		
		String content = "HELLO WORLD";
		
		IQ iq = new IQ(Type.get);
		iq.setTo(UPPERCASE_COMPONENT_URL);
		iq.getElement()
				.addElement("query", "uppercase")
				.addElement("content")
				.setText(content);
		
		IQ response;
		try {
			response = (IQ) client.syncSend(iq);
			String uppercaseReturn = response.getElement()
												.element("query")
												.element("content")
												.getText();
			
			assertEquals(content.toUpperCase(), uppercaseReturn);
			
		} catch (XMPPException e) {
			e.printStackTrace();
			fail("Response problem!");
		}		
	}
	
	@Test
	public void testEmptyContent() {
		
		String content = "";
		
		IQ iq = new IQ(Type.get);
		iq.setTo(UPPERCASE_COMPONENT_URL);
		iq.getElement()
				.addElement("query", "uppercase")
				.addElement("content")
				.setText(content);
		
		IQ response;
		try {			
			response = (IQ) client.syncSend(iq);
			fail("Nao lancou exception!");
		} catch (XMPPException e) {
			assertEquals(Condition.bad_request, e.getXMPPError().getCondition());
			assertEquals(org.xmpp.packet.PacketError.Type.modify, e.getXMPPError().getType());
		}
	}
	
		
	@Test
	public void testSetTurnOnFalse() {
		
		String content = "hello world";
		
		IQ iq = new IQ(Type.set);
		iq.setTo(UPPERCASE_COMPONENT_URL);
		iq.getElement()
				.addElement("query", "uppercase")
				.addElement("content")
				.setText(String.valueOf(false));
		
		IQ response;
		try {
			response = (IQ) client.syncSend(iq);
			
			System.out.println(response.toXML());
			
			String setReturn = response.getElement()
					.element("query")
					.element("content")
					.getText();
			
			assertEquals(setReturn, "OK");
			
			//uppercase not on
			iq = new IQ(Type.get);
			iq.setTo(UPPERCASE_COMPONENT_URL);
			iq.getElement()
					.addElement("query", "uppercase")
					.addElement("content")
					.setText(content);
			
			response = (IQ) client.syncSend(iq);
			
			System.out.println(response.toXML());
			
			String getReturn = response.getElement()
												.element("query")
												.element("content")
												.getText();
			
			assertEquals(content, getReturn);
			
		} catch (XMPPException e) {
			e.printStackTrace();
			fail("Response problem!");
		}		
	}
	
	
	@Test
	public void testSetTurnOnTrue() {
		
		String content = "hello world";
		
		IQ iq = new IQ(Type.set);
		iq.setTo(UPPERCASE_COMPONENT_URL);
		iq.getElement()
				.addElement("query", "uppercase")
				.addElement("content")
				.setText(String.valueOf(true));
		
		IQ response;
		try {
			response = (IQ) client.syncSend(iq);
			
			System.out.println(response.toXML());
			
			String setReturn = response.getElement()
					.element("query")
					.element("content")
					.getText();
			
			assertEquals(setReturn, "OK");
			
			//uppercase not on
			iq = new IQ(Type.get);
			iq.setTo(UPPERCASE_COMPONENT_URL);
			iq.getElement()
					.addElement("query", "uppercase")
					.addElement("content")
					.setText(content);
			
			response = (IQ) client.syncSend(iq);
			
			System.out.println(response.toXML());
			
			String getReturn = response.getElement()
												.element("query")
												.element("content")
												.getText();
			
			assertEquals(content.toUpperCase(), getReturn);
			
		} catch (XMPPException e) {
			e.printStackTrace();
			fail("Response problem!");
		}		
	}
	
	
	@Test
	public void testSetInvalidTurnOn() {
		
		String content = "hello world";
		
		IQ iq = new IQ(Type.set);
		iq.setTo(UPPERCASE_COMPONENT_URL);
		iq.getElement()
				.addElement("query", "uppercase")
				.addElement("content")
				.setText("invalid");
		
		IQ response;
		try {
			response = (IQ) client.syncSend(iq);
			fail("Aceitou turnOn invalido!");
			
		} catch (XMPPException e) {
			assertEquals(Condition.bad_request, e.getXMPPError().getCondition());
			assertEquals(org.xmpp.packet.PacketError.Type.modify, e.getXMPPError().getType());
		}		
	}
	
	
	@After
	public void tearDown(){
		client.disconnect();
	}
	
//	@AfterClass
//	public void tearDownComponent(){
//		xmppComponent.shutdown();
//	}

}
