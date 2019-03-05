package com.github.itaborda.restmetadata.client.service;

import com.github.itaborda.restmetadata.ValueOptions;

import java.util.Map;

public interface SkillService {

    @ValueOptions("skills")
    Map<String, Object> getSkills();

}