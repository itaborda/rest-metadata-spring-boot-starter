package com.github.itaborda.restmetadata.client.service.impl;

import com.github.itaborda.restmetadata.client.model.DoubleValue;
import com.github.itaborda.restmetadata.client.service.SkillService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("skillService")
public class SkillServiceImpl implements SkillService {

    @Override
    // @ValueOptions("skills") - mapped in interface
    public Map<String, Object> getSkills() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Esperto", new DoubleValue(3, "Level2", "Esperto"));
        map.put("Comunicativo", new DoubleValue(4, "Level1", "Comunicativo"));
        map.put("Criativo", new DoubleValue(7, "Level1", "Criativo"));
        map.put("Dinâmico", new DoubleValue(8, "Level2", "Dinâmico"));

        return map;
    }

}
