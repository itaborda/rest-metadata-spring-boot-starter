package com.github.itaborda.restmetadata.client.controller;

import com.github.itaborda.restmetadata.MetadataEntity;
import com.github.itaborda.restmetadata.ValueOptions;
import com.github.itaborda.restmetadata.client.model.DoubleValue;
import com.github.itaborda.restmetadata.client.model.SampleEntity;
import com.github.itaborda.restmetadata.client.model.SampleEntity.SelectEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller("restMetadataController")
@RequestMapping("/restmetadata")
public class RestMetadataClientController {

    @GetMapping
    public String index(Model model) {

        model.addAttribute("message", "RestMetadata");
        return "index";
    }

    @GetMapping("/get/{id}")
    public @ResponseBody
    SampleEntity getEntityNoIU(@PathVariable("id") Long id, ModelMap modelMap) {

        return getEntity(id, modelMap);
    }

    @GetMapping("/get/{id}/metadata")
    @MetadataEntity
    public @ResponseBody
    SampleEntity getEntity(@PathVariable("id") Long id, ModelMap modelMap) {

        SampleEntity course = new SampleEntity();
        course.setNumber(10);
        course.setBirthDate(new Date());
        course.setEmail("test@mail.com");
        course.setSelectByEnum(SelectEnum.F);
        course.setText("teste");
        course.setSelectLocalValues(3);
        course.setSelectByLocalMethod(new DoubleValue(3, "Spanish", "Madrid"));
        course.setSelectByBeanClass(4);

        return course;
    }

    @GetMapping("/selectProvider/{id}/getRegionsByClient")
    @ValueOptions("regionsByClientId")
    public @ResponseBody
    List<DoubleValue> getRegionsByClient(@PathVariable("id") Long id) {

        List<DoubleValue> list = new ArrayList<DoubleValue>();
        list.add(new DoubleValue(2, "Brasil-" + id, "São Paulo-" + id));
        list.add(new DoubleValue(3, "United States-" + id, "California-" + id));
        list.add(new DoubleValue(5, "Spanish-" + id, "Madrid-" + id));
        list.add(new DoubleValue(7, "Japan-" + id, "Hong Kong-" + id));

        return list;
    }

    @GetMapping("/selectProvider/{id}/getAllRegionsById")
    public @ResponseBody
    Map<String, Object> getAllRegionsById(@PathVariable("id") Long id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Brasil-" + id, new DoubleValue(3, "Brasil-" + id, "São Paulo-" + id));
        map.put("United States-" + id, new DoubleValue(4, "United States-" + id, "California-" + id));
        map.put("Spanish-" + id, new DoubleValue(7, "Spanish-" + id, "Madrid-" + id));
        map.put("Japan-" + id, new DoubleValue(8, "Japan-" + id, "Hong Kong-" + id));

        return map;
    }

    @GetMapping("/selectProvider/{id}/getAllRegionsById_List")
    public @ResponseBody
    List<DoubleValue> getAllRegionsById_List(@PathVariable("id") Long id) {

        List<DoubleValue> list = new ArrayList<DoubleValue>();
        list.add(new DoubleValue(2, "Brasil-" + id, "São Paulo-" + id));
        list.add(new DoubleValue(3, "United States-" + id, "California-" + id));
        list.add(new DoubleValue(5, "Spanish-" + id, "Madrid-" + id));
        list.add(new DoubleValue(7, "Japan-" + id, "Hong Kong-" + id));

        return list;
    }
}