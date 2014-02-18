package component.uppercase;
import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.IQ.Type;
import org.xmpp.packet.PacketError;
import org.xmpp.packet.PacketError.Condition;


public class UppercaseHandler extends AbstractQueryHandler {

	private final static String NAMESPACE = "uppercase";
	private boolean turnOn;
	
	
	public UppercaseHandler() {
		super(NAMESPACE);
		turnOn = true;
	}

	@Override
	public IQ handle(IQ iq) {
		
		IQ result;
		
		//IQ type set
		if (Type.set.equals(iq.getType())){
			System.out.println("Chegou IQ set");
			
			String content = iq.getElement().element("query").element("content").getText();
	        System.out.println("Chegou pacote com content "+ content);
	        
	        if (content.equalsIgnoreCase("true")){
	        	System.out.println("Setting turnOn to " + true);
	        	turnOn = true;
	        } else if (content.equalsIgnoreCase("false")){
	        	System.out.println("Setting turnOn to " + false);
	        	turnOn = false;
	        } else {
	        	return createErrorResponse(iq, "Invalid content", Condition.bad_request, org.xmpp.packet.PacketError.Type.modify);
	        }
	        
	        result = IQ.createResultIQ(iq);
	        result.getElement().addElement("query","uppercase").addElement("content").setText("OK");
			
		} else { //IQ type get
			System.out.println("Chegou IQ get");
			System.out.println("Chegou pacote!");
	        String content = iq.getElement().element("query").element("content").getText();
	        System.out.println("Chegou um pacote com content "+ content);
	        
	        if ("".equals(content)){
	        	result = createErrorResponse(iq, "Invalid text", Condition.bad_request, org.xmpp.packet.PacketError.Type.modify);
	        	return result;		
	        }
	        
	        if (turnOn){
	        	content = content.toUpperCase();	
	        }
	        
	        result = IQ.createResultIQ(iq);	      
	        result.getElement().addElement("query","uppercase").addElement("content").setText(content);	
		}
	
		return result;
	}

	private IQ createErrorResponse(IQ request, String message, Condition condition, org.xmpp.packet.PacketError.Type type) {
		IQ result = request.createCopy();
		result.setID(request.getID());
		result.setFrom(request.getTo());
		result.setTo(request.getFrom());
		
		PacketError e = new PacketError(condition, type);
		if(message != null) {
			e.setText(message);
		}
		result.setError(e);
		return result;
	}

	
}
