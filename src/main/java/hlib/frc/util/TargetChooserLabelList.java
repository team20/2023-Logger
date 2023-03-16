package hlib.frc.util;

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
public class TargetChooserLabelList extends LinkedList<String> {

	/**
	 * The automatically generated serial version UID.
	 */

	private static final long serialVersionUID = -5083647150936718395L;

	/**
	 * Constructs a {@code PoseMap} by parsing the specified file.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public TargetChooserLabelList(String fileName) {
		try {
			JsonNode root = new ObjectMapper().readTree(new File(fileName));
			JsonNode poses = root.path("target choosers");
			Iterator<JsonNode> i = poses.elements();
			while (i.hasNext()) {
				JsonNode node = i.next();
				String label = node.path("label").asText();
				if (label != null)
					add(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(size() + " target chooser labels read from \"" + fileName + "\".");
	}

}
