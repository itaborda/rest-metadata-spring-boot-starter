package br.com.cd.ui4entity.client.service.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.cd.ui4entity.ValueOptions;
import br.com.cd.ui4entity.client.model.DoubleValue;

public class TestOccupationsProvider {

	@ValueOptions("occupations")
	public Map<String, DoubleValue> getValueOptions() {

		Map<String, DoubleValue> map = new HashMap<String, DoubleValue>();

		map.put("Gerente", new DoubleValue(7, "Ocioso", "Gerente"));
		map.put("Analista", new DoubleValue(3, "Ocioso", "Analista"));
		map.put("Arquiteto", new DoubleValue(1, "Top", "Arquiteto"));
		map.put("Programador", new DoubleValue(4, "Top", "Programador"));
		map.put("Web Design", new DoubleValue(6, "Boiola", "Web Design"));

		return map;
	}
}