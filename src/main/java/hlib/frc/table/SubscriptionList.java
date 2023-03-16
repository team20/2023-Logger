package hlib.frc.table;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A {@code SubscriptionList} contains information about subscriptions to {@code NetworkTables}.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public class SubscriptionList extends LinkedList<SubscriptionDefinition> {

	/**
	 * The automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -1397870910159622975L;

	/**
	 * Constructs a {@code PoseMap} by parsing the specified file.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public SubscriptionList(String fileName) {
		try {
			JsonNode root = new ObjectMapper().readTree(new File(fileName));
			JsonNode poses = root.path("subscriptions");
			Iterator<JsonNode> i = poses.elements();
			while (i.hasNext()) {
				SubscriptionDefinition s = SubscriptionDefinition.createSubscriptionDefinition(i.next());
				if (s != null)
					add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(size() + " subscriptions read from \"" + fileName + "\".");
	}

}
