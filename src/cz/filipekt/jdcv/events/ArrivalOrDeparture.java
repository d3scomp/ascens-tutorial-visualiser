package cz.filipekt.jdcv.events;

import cz.filipekt.jdcv.network.MyLink;

/**
 * Models an event of type "departure" or "arrival", which appears in the MATSIM event log
 * 
 * @author Tomas Filipek <tom.filipek@seznam.cz>
 */
public class ArrivalOrDeparture implements MatsimEvent {
	
	public static enum LegMode {
		CAR, TRANSIT_WALK;
		
		private static final String carName = "car";
		private static final String transitName = "transit_walk";
		
		public static LegMode from(String value){
			switch (value) {
				case carName:
					return CAR;
				case transitName:
					return TRANSIT_WALK;
				default:
					return null;					
				}
		}
	}
	
	/**
	 * Specifies whether this event is an arrival or a departure.
	 */
	private final EventType type;
	
	/**
	 * Time at which the event occurred.
	 */
	private final double time;
	
	/**
	 * ID of the person that arrived (or departed).
	 */
	private final String person;
	
	/**
	 * The link through which the person arrived (or departed).
	 */
	private final MyLink link;
	
	/**
	 * Mode of the leg that started (or ended) with this departure (or arrival).
	 */
	private final LegMode legMode;

	/**
	 * {@inheritDoc}
	 * @see {@link ArrivalOrDeparture#type}
	 */
	@Override
	public EventType getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 * @see {@link ArrivalOrDeparture#time}
	 */
	@Override
	public double getTime() {
		return time;
	}

	/**
	 * @return ID of the person that arrived (or departed).
	 * @see {@link ArrivalOrDeparture#person}
	 */
	@Override
	public String getPerson() {
		return person;
	}

	/**
	 * @return The link through which the person arrived (or departed).
	 * @see {@link ArrivalOrDeparture#link}
	 */
	public MyLink getLink() {
		return link;
	}

	/**
	 * @return Mode of the leg that started (or ended) with this departure (or arrival).
	 * @see {@link ArrivalOrDeparture#legMode}
	 */
	public LegMode getLegMode() {
		return legMode;
	}

	/**
	 * @param departure If true, the event is of type "departure". If false, the event is of type "arrival". 
	 * @param time Time at which the event occurred.
	 * @param person ID of the person that arrived (or departed).
	 * @param link The link through which the person arrived (or departed).
	 * @param legMode Mode of the leg that started (or ended) with this departure (or arrival).
	 */
	public ArrivalOrDeparture(boolean departure, double time, String person, MyLink link, String legMode) {
		if (departure){
			type = EventType.DEPARTURE;
		} else {
			type = EventType.ARRIVAL;
		}		
		this.time = time;
		this.person = person;
		this.link = link;
		this.legMode = LegMode.from(legMode);
	}

}
