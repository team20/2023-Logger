package hlib.frc;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

/**
 * A {@code PoseMap} associates {@code Pose}s with identifiers.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public class AprilTagMap extends HashMap<Integer, Transform> {

	/**
	 * The automatically generated serial version UID.
	 */

	private static final long serialVersionUID = -4059710587055947756L;

	/**
	 * Constructs a {@code PoseMap} by parsing the specified file.
	 * 
	 * @param fileName
	 *                 the name of the file
	 */
	public AprilTagMap(String fileName) {
		try {
			JsonNode root = new ObjectMapper().readTree(new File(fileName));
			JsonNode poses = root.path("fiducials");
			Iterator<JsonNode> i = poses.elements();
			while (i.hasNext()) {
				JsonNode n = i.next();
				int tagID = n.path("id").asInt();
				Iterator<JsonNode> j = n.path("transform").elements();
				Affine tranform = Transform.affine(j.next().asDouble(), j.next().asDouble(), j.next().asDouble(),
						j.next().asDouble(), j.next().asDouble(), j.next().asDouble(), j.next().asDouble(),
						j.next().asDouble(), j.next().asDouble(), j.next().asDouble(), j.next().asDouble(),
						j.next().asDouble());
				put(tagID, tranform);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(size() + " tags read from \"" + fileName + "\".");
	}

}
