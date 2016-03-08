package base;

import java.io.StringReader;
import java.io.StringWriter;

import flexjson.JSONSerializer;
import pokerBase.Deck;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class mainClass {

	public static void main(String[] args) {
		
		Deck d = new Deck("test");
		
		JSONSerializer serializer = new JSONSerializer();

		String strDeck = serializer.include("deckCards").serialize(d);
		
		System.out.println(strDeck);
		
		String strXML = SerializeMe(d);
		
		Deck d1;
		try {
			d1 = DeserializeMe(strXML);
			System.out.println(d1.toString());
			System.out.println(SerializeMe(d1));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Deck DeserializeMe(String strXML) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Deck.class);
		Unmarshaller m = context.createUnmarshaller();
		return (Deck)m.unmarshal(new StringReader(strXML));
		
		
	}
	
	public static String SerializeMe(Deck d)
	{
	    StringWriter sw = new StringWriter();
		try
		{
		    //Write it
		    JAXBContext ctx = JAXBContext.newInstance(Deck.class);
		    Marshaller m = ctx.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    m.marshal(d, sw);
		    sw.close();			
		}
		catch (Exception ex)
		{
			
		}
    
    return sw.toString();
	}

}
