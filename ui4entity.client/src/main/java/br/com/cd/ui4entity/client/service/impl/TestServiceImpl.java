package br.com.cd.ui4entity.client.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.cd.ui4entity.client.model.DoubleValue;
import br.com.cd.ui4entity.client.service.TestService;

@Component("testService")
public class TestServiceImpl implements TestService {

	@Override
	// @ValueOptions("skills") - mapped in interface
	public Map<String, Object> getValueOptions() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Esperto", new DoubleValue(3, "Level2", "Esperto"));
		map.put("Comunicativo", new DoubleValue(4, "Level1", "Comunicativo"));
		map.put("Criativo", new DoubleValue(7, "Level1", "Criativo"));
		map.put("Dinâmico", new DoubleValue(8, "Level2", "Dinâmico"));

		return map;
	}

}
