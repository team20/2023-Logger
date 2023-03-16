package hlib.frc.table;

import java.util.HashMap;

public class SubscriptionMap extends HashMap<String, HashMap<String, SubscriptionDefinition>> {

	private static final long serialVersionUID = -7820959133861547069L;

	public SubscriptionMap(Iterable<SubscriptionDefinition> schemas) {
		for (SubscriptionDefinition schema : schemas) {
			HashMap<String, SubscriptionDefinition> e = get(schema.tableName());
			if (e == null) {
				e = new HashMap<String, SubscriptionDefinition>();
				put(schema.tableName(), e);
			}
			e.put(schema.topicName(), schema);
		}
	}
	
	public SubscriptionDefinition get(String tableName, String topicName) {
		HashMap<String, SubscriptionDefinition> m = get(tableName);
		if (m == null)
			return null;
		return m.get(topicName);
	}


}

