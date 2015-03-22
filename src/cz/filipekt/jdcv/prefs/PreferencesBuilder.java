package cz.filipekt.jdcv.prefs;

import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import cz.filipekt.jdcv.ensembles.MembershipRelation;
import cz.filipekt.jdcv.network.MyLink;
import cz.filipekt.jdcv.network.MyNode;

/**
 * Contains methods to retrieve preferences objects for various elements of simulated situation
 * 
 * @author Tomas Filipek <tom.filipek@seznam.cz>
 */
public class PreferencesBuilder {
	
	/**
	 * Maps visual representations of nodes to the corresponding parsed link XML elements.
	 */
	private final Map<Node,MyNode> nodes;
	
	/**
	 * Maps visual representations of links to the corresponding parsed link XML elements.
	 */
	private final Map<Node,MyLink> lines;
	
	/**
	 * Maps each ensemble membership relation to the graphical representation of this relation.
	 */
	private final Map<MembershipRelation,Node> ensembleShapes;
	
	/**
	 * @return Mapping of node IDs to the preferences objects corresponding to the given node.
	 * @param logWriter Used for producing logs
	 */
	public Map<String,NodePrefs> nodePrefs(Writer logWriter){
		Map<String,NodePrefs> res = new HashMap<>();
		for (Node shape : nodes.keySet()){
			MyNode node = nodes.get(shape);
			NodePrefs prefs = new NodePrefs(node.getId(), node.getX(), node.getY(), shape, logWriter);
			res.put(node.getId(), prefs);
		}
		return res;
	}
	
	/**
	 * @return Mapping of link IDs to the preferences objects corresponding to the given link.
	 * @param logWriter Used for producing logs
	 */
	public Map<String,LinkPrefs> linkPrefs(Writer logWriter){
		Map<String,LinkPrefs> res = new HashMap<>();
		for (Node shape : lines.keySet()){
			MyLink link = lines.get(shape);
			Line line = (Line)shape;
			LinkPrefs prefs = new LinkPrefs(link.getId(), link.getFrom().getId(), 
					link.getTo().getId(), line, logWriter);
			res.put(link.getId(), prefs);
		}
		return res;
	}
	
	/**
	 * @return Collection of preferences objects associated with each ensemble membership relation
	 */
	public Set<MembershipPrefs> membershipPrefs(Writer logWriter){
		Set<MembershipPrefs> res = new HashSet<>();
		for (MembershipRelation mr : ensembleShapes.keySet()){
			Line line = (Line)ensembleShapes.get(mr);
			MembershipPrefs pref = new MembershipPrefs(mr.getEnsembleName(), mr.getCoordinator(), 
					mr.getMember(), line, logWriter);
			res.add(pref);
		}
		return res;
	}

	/**
	 * @param nodes Maps visual representations of nodes to the corresponding parsed link XML elements.
	 * @param lines Maps visual representations of links to the corresponding parsed link XML elements.
	 * @param ensembleShapes Maps each ensemble membership relation to the graphical representation 
	 * of this relation.
	 */
	public PreferencesBuilder(Map<Node,MyNode> nodes, Map<Node, MyLink> lines,
			Map<MembershipRelation, Node> ensembleShapes) {
		this.nodes = nodes;
		this.lines = lines;
		this.ensembleShapes = ensembleShapes;
	}
}