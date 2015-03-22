package cz.filipekt.jdcv.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cz.filipekt.jdcv.events.EnsembleEvent;
import cz.filipekt.jdcv.exceptions.InvalidAttributeValueException;

/**
 * SAX handler used to parse the XML file containing the ensemble events.
 * Collects the "event" elements. 
 * 
 * @author Tomas Filipek <tom.filipek@seznam.cz>
 */
public class EnsembleHandler extends DefaultHandler {

	/**
	 * Local name of the event element
	 */
	private final String eventName = "event";
	
	/**
	 * Name of the coordinator attribute of the event element
	 */
	private final String coordinatorName = "coordinator";
	
	/**
	 * Name of the member attribute of the event element
	 */
	private final String memberName = "member";
	
	/**
	 * Name of the membership attribute of the event element
	 */
	private final String membershipName = "membership";
	
	/**
	 * Name of the ensemble attribute of the event element
	 */
	private final String ensembleName = "ensemble";
	
	/**
	 * Name of the time attribute of the event element
	 */
	private final String timeName = "time";
	
	/**
	 * Storage for the parsed event elements 
	 */
	private final List<EnsembleEvent> events = new ArrayList<>();
	
	/**
	 * @return The parsed event elements
	 * @see {@link EnsembleHandler#events} 
	 */
	public List<EnsembleEvent> getEvents() {
		return events;
	}
	
	/**
	 * If true, only the events starting after time {@link EnsembleHandler#startAtLimit}
	 * are taken into account 
	 */
	private final boolean startAtConstraint;
	
	/**
	 * If {@link EnsembleHandler#startAtConstraint} holds, only events starting from
	 * this time on are taken into account
	 */
	private final int startAtLimit;
	
	/**
	 * If true, only the events ending before time {@link EnsembleHandler#endAtLimit}
	 * are taken into account
	 */
	private final boolean endAtConstraint;
	
	/**
	 * If {@link EnsembleHandler#endAtConstraint} holds, only the events ending before this
	 * time are taken into account
	 */
	private final int endAtLimit;
	
	/**
	 * @param startAt Only events starting from this time on are taken into account. If null,
	 * no such constraint is applied.
	 * @param endAt Only the events ending before this time are taken into account. If null,
	 * no such constraint is applied.
	 */
	public EnsembleHandler(Integer startAt, Integer endAt){
		if (startAt == null){
			startAtConstraint = false;
			startAtLimit = -1;
		} else {
			startAtConstraint = true;
			startAtLimit = startAt;
		}
		if (endAt == null){
			endAtConstraint = false;
			endAtLimit = -1;
		} else {
			endAtConstraint = true;
			endAtLimit = endAt;
		}
	}

	/**
	 * Makes sure that when an ensemble event element is encountered, it contains all the 
	 * required attributes with values in correct format. Afterwards, event element is stored
	 * in the parsed form in the {@link EnsembleHandler#events} storage.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals(eventName)){
			String timeVal = attributes.getValue(timeName);
			Utils.ensureNonNullAndNonEmpty(timeVal);
			double time;
			try {
				time = Double.parseDouble(timeVal);
			} catch (NumberFormatException ex){
				throw new SAXException(new InvalidAttributeValueException());
			}
			if (startAtConstraint && (startAtLimit > time)){
				return;
			}
			if (endAtConstraint && (endAtLimit < time)){
				return;
			}
			String coordinatorVal = attributes.getValue(coordinatorName);
			Utils.ensureNonNullAndNonEmpty(coordinatorVal);
			String memberVal = attributes.getValue(memberName);
			Utils.ensureNonNullAndNonEmpty(memberVal);
			String membershipVal = attributes.getValue(membershipName);
			Utils.ensureNonNullAndNonEmpty(membershipVal);
			String ensembleVal = attributes.getValue(ensembleName);
			Utils.ensureNonNullAndNonEmpty(ensembleVal);
			boolean membership;
			switch(membershipVal){
				case "true":
					membership = true;
					break;
				case "false":
					membership = false;
					break;
				default:
					throw new SAXException(new InvalidAttributeValueException());
			}
			EnsembleEvent eev = new EnsembleEvent(coordinatorVal, memberVal, membership, ensembleVal, time);
			events.add(eev);
		}
	}
	
}
