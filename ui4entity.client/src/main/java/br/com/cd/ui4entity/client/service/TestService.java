package br.com.cd.ui4entity.client.service;

import java.util.Map;

import br.com.cd.ui4entity.ValueOptions;

public interface TestService {

	@ValueOptions("skills")
	Map<String, Object> getValueOptions();

}