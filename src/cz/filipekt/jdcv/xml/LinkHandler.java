package cz.filipekt.jdcv.xml;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import cz.filipekt.jdcv.exceptions.InvalidAttributeValueException;
import cz.filipekt.jdcv.exceptions.NodeNotFoundException;
import cz.filipekt.jdcv.network.MyLink;
import cz.filipekt.jdcv.network.MyNode;

/**
 * SAX handler used to parse the XML file containing the network(map) description.
 * Collects the "link" elements. 
 * Can only be used after the "node" elements have been collected.
 * 
 * @author Tomas Filipek <tom.filipek@seznam.cz>
 */
public class LinkHandler extends DefaultHandler {
	
	/**
	 * Local name of the link XML element.
	 */
	private final String linkName = "link";
	
	/**
	 * Name of the attribute containing the id of the link
	 */
	private final String idName = "id";
	
	/**
	 * Name of the attribute containing the id of the node where this link starts.
	 */
	private final String fromName = "from";
	
	/**
	 * Name of the attribute containing the id of the node where this link ends.
	 */
	private final String toName = "to";
	
	/**
	 * Name of the attribute containing the length of this link.
	 */
	private final String lengthName = "length";
	
	/**
	 * Name of the attribute containing the allowed maximum speed of the link 
	 * (for street link), the 'fixed' speed of the link (for public transport links), 
	 * the 'typical' speed of the link (for pedestrian links).
	 */
	private final String freespeedName = "freespeed";
	
	/**
	 * Name of the attribute containing the maximal capacity of this link 
	 * for a given period (specified by "links" element attribute "capperiod").
	 */
	private final String capacityName = "capacity";
	
	/**
	 * Name of the attribute containing the number of lanes of this link.
	 */
	private final String permlanesName = "permlanes";
	
	/**
	 * Name of the attribute containing the comma-separated list of 
	 * transportation modes that are allowed on this link.
	 */
	private final String modesName = "modes";
	
	/**
	 * Contains the {@link MyLink} representations of the encountered link elements.
	 */
	private final Map<String,MyLink> links = new HashMap<>();
	
	/**
	 * @return The {@link MyLink} representations of the encountered link elements.
	 * @see {@link LinkHandler#links}
	 */
	public Map<String, MyLink> getLinks() {
		return links;
	}

	/**
	 * Contains the {@link MyNode} representations of all the "node" elements.
	 */
	private final Map<String,MyNode> nodes;

	/**
	 * The link elements of the network XML source contain mandatory attributes "from" and "to",
	 * which refer to ids of some network nodes. When parsing the link elements, these node ids
	 * are immediately dereferenced into {@link MyNode} representations of nodes. Thus the node
	 * collection is required.
	 * @param nodes Presumably the result of parsing all of the "node" elements in the network XML file. 
	 * Maps the node id to the {@link MyNode} representation of the node.
	 */
	public LinkHandler(Map<String, MyNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Called by the {@link XMLReader} during the SAX parsing, when an element is entered.
	 * Makes sure that when the name of the entered element is "link", proper actions are taken.
	 * @see {@link LinkHandler#processLink(Attributes)}
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals(linkName)){
			processLink(attributes);
		}
	}
	
	/**
	 * When a "link" element is encountered, this method creates a {@link MyLink} representation 
	 * of this element and places it in the {@link LinkHandler#links} map.
	 * @param attributes The attributes of the currently encountered link element.
	 * @throws SAXException When a mandatory attribute is missing, empty or has an invalid value.
	 */
	private void processLink(Attributes attributes) throws SAXException {
		String id = attributes.getValue(idName);
		Utils.ensureNonNullAndNonEmpty(id);
		String from = attributes.getValue(fromName);
		Utils.ensureNonNullAndNonEmpty(from);
		String to = attributes.getValue(toName);
		Utils.ensureNonNullAndNonEmpty(to);
		String length = attributes.getValue(lengthName);
		Utils.ensureNonNullAndNonEmpty(length);
		String freespeed = attributes.getValue(freespeedName);
		Utils.ensureNonNullAndNonEmpty(freespeed);
		String capacity = attributes.getValue(capacityName);
		Utils.ensureNonNullAndNonEmpty(capacity);
		String permlanes = attributes.getValue(permlanesName);
		Utils.ensureNonNullAndNonEmpty(permlanes);
		String modes = attributes.getValue(modesName);
		Utils.ensureNonNullAndNonEmpty(modes);
		MyNode fromNode = nodes.get(from);
		MyNode toNode = nodes.get(to);
		if ((fromNode==null) || (toNode==null)){
			throw new SAXException(new NodeNotFoundException());
		}
		try {
			BigDecimal lengthDecimal = new BigDecimal(length);
			double freespeedDouble = Double.parseDouble(freespeed);
			double capacityDouble = Double.parseDouble(capacity);
			double permlanesDouble = Double.parseDouble(permlanes);
			String[] modesArray = modes.split(",");
			MyLink link = new MyLink(id, fromNode, toNode, lengthDecimal, freespeedDouble, capacityDouble, permlanesDouble, modesArray);
			links.put(id, link);
		} catch (NumberFormatException ex){
			throw new SAXException(new InvalidAttributeValueException());
		}
	}
	
}