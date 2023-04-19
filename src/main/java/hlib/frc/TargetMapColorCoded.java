package hlib.frc;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.JsonNode;

import javafx.scene.paint.Color;

/**
 * A {@code TargetMap} associates {@code Target}s with identifiers.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 * @author Andrew Hwang (u.andrew.h@gmail.com)
 */
public class TargetMapColorCoded extends TargetMap {

	/**
	 * The automatically generated serial version UID.
	 */
	private static final long serialVersionUID = 7623985188504091348L;

	public static Color RED = Color.RED.interpolate(Color.WHITE, 0.8);

	public static Color BLUE = Color.BLUE.interpolate(Color.WHITE, 0.8);

	LinkedHashMap<String, Color> colors;

	/**
	 * Constructs a {@code TargetMap} by parsing the specified file.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public TargetMapColorCoded(String fileName) {
		super(fileName);
	}

	@Override
	protected String update(JsonNode n) {
		String targetID = super.update(n);
		if (colors == null)
			colors = new LinkedHashMap<String, Color>();
		colors.put(targetID, "RED".equals(n.path("color").asText()) ? RED : BLUE);
		return targetID;
	}

	public Color color(String poseID) {
		return colors.get(poseID);
	}

}
