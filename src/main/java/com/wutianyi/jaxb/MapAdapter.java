package com.wutianyi.jaxb;

import com.google.api.client.util.Maps;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Map;

/**
 * Created by hanjiewu on 2016/3/22.
 */
public class MapAdapter extends XmlAdapter<MapElements[], Map<String, String>> {
	@Override
	public Map<String, String> unmarshal(MapElements[] v) throws Exception {
		Map<String, String> ret = Maps.newHashMap();
		for (MapElements mapElements : v) {
			ret.put(mapElements.key, mapElements.value);
		}
		return ret;
	}

	@Override
	public MapElements[] marshal(Map<String, String> v) throws Exception {
		MapElements[] mapElementses = new MapElements[v.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : v.entrySet()) {
			mapElementses[i] = new MapElements(entry.getKey(), entry.getValue());
			++i;
		}
		return mapElementses;
	}
}
