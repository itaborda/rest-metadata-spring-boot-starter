package com.github.itaborda.restmetadata.client.service.impl;

import com.github.itaborda.restmetadata.ValueOptions;
import com.github.itaborda.restmetadata.client.model.DoubleValue;

import java.util.HashMap;
import java.util.Map;

public class OccupationsProviderNoService {

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