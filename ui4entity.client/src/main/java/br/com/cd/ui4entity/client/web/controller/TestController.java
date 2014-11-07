package br.com.cd.ui4entity.client.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cd.ui4entity.UI4Entity;
import br.com.cd.ui4entity.ValueOptions;
import br.com.cd.ui4entity.client.model.DoubleValue;
import br.com.cd.ui4entity.client.model.TestEntity;
import br.com.cd.ui4entity.client.model.TestEntity.SelectEnum;

@Controller("testController")
@RequestMapping(value = "/test")
public class TestController {

	@RequestMapping(method = RequestMethod.GET)
	public String test(ModelMap modelMap, HttpServletRequest request) {

		return "ui4entity";
	}

	@RequestMapping(value = "/getEntityNoUI.json/{id}", method = RequestMethod.GET)
	public @ResponseBody TestEntity getEntityNoIU(@PathVariable("id") Long id, ModelMap modelMap) {

		return getEntity(id, modelMap);
	}

	@RequestMapping(value = "/getEntity.json/{id}", method = RequestMethod.GET)
	@UI4Entity
	public @ResponseBody TestEntity getEntity(@PathVariable("id") Long id, ModelMap modelMap) {

		TestEntity course = new TestEntity();
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

	@RequestMapping(value = "/entity/{id}/getRegionsByClient.json", method = RequestMethod.GET)
	@ValueOptions("regionsByClientId")
	public @ResponseBody List<DoubleValue> getRegionsByClient(@PathVariable("id") Long id) {

		List<DoubleValue> list = new ArrayList<DoubleValue>();
		list.add(new DoubleValue(2, "Brasil-" + id, "São Paulo-" + id));
		list.add(new DoubleValue(3, "United States-" + id, "California-" + id));
		list.add(new DoubleValue(5, "Spanish-" + id, "Madrid-" + id));
		list.add(new DoubleValue(7, "Japan-" + id, "Hong Kong-" + id));

		return list;
	}

	@RequestMapping(value = "/entity/{id}/getAllRegionsById.json", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getAllRegionsById(@PathVariable("id") Long id) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Brasil-" + id, new DoubleValue(3, "Brasil-" + id, "São Paulo-" + id));
		map.put("United States-" + id, new DoubleValue(4, "United States-" + id, "California-" + id));
		map.put("Spanish-" + id, new DoubleValue(7, "Spanish-" + id, "Madrid-" + id));
		map.put("Japan-" + id, new DoubleValue(8, "Japan-" + id, "Hong Kong-" + id));

		return map;
	}

	@RequestMapping(value = "/entity/{id}/getAllRegionsById_List.json", method = RequestMethod.GET)
	public @ResponseBody List<DoubleValue> getAllRegionsById_List(@PathVariable("id") Long id) {

		List<DoubleValue> list = new ArrayList<DoubleValue>();
		list.add(new DoubleValue(2, "Brasil-" + id, "São Paulo-" + id));
		list.add(new DoubleValue(3, "United States-" + id, "California-" + id));
		list.add(new DoubleValue(5, "Spanish-" + id, "Madrid-" + id));
		list.add(new DoubleValue(7, "Japan-" + id, "Hong Kong-" + id));

		return list;
	}
}