package hlib.frc;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A {@code PathMap} associates {@code Target}s with possible paths.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public class PathMap extends LinkedHashMap<String, LinkedList<LinkedList<String>>> {

	/**
	 * The automatically generated serial version UID.
	 */

	private static final long serialVersionUID = 6702805015898052922L;

	/**
	 * Constructs a {@code PathMap} by parsing the specified file.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public PathMap(String fileName) {
		try {
			JsonNode root = new ObjectMapper().readTree(new File(fileName));
			JsonNode targets = root.path("paths");
			Iterator<JsonNode> i = targets.elements();
			while (i.hasNext())
				update(i.next());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Possible paths to " + size() + " targets read from \"" + fileName + "\".");
	}

	/**
	 * Updates this {@code PathMap} based on the specified {@code JsonNode}.
	 * 
	 * @param n
	 *            a {@code JsonNode}
	 */
	protected void update(JsonNode n) {
		Iterator<JsonNode> i = n.path("paths").elements();
		LinkedList<LinkedList<String>> paths = new LinkedList<LinkedList<String>>();
		while (i.hasNext()) {
			JsonNode path = i.next();
			LinkedList<String> l = new LinkedList<String>();
			Iterator<JsonNode> j = path.path("path").elements();
			while (j.hasNext())
				l.add(j.next().asText());
			paths.add(l);
		}
		i = n.path("targets").elements();
		while (i.hasNext())
			put(i.next().asText(), paths);
	}

}
