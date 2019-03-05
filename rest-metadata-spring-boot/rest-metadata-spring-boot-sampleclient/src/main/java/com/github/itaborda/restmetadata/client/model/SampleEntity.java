package com.github.itaborda.restmetadata.client.model;

import com.github.itaborda.restmetadata.*;
import com.github.itaborda.restmetadata.client.service.impl.OccupationsProviderNoService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("restdata_sample")
@Data
@EqualsAndHashCode(of = "id")
public class SampleEntity {

    public enum SelectEnum {
        F, M
    }

    @Id
    String id;

    // ###

    // @InputField(template = "#divId", ....)
    @InputField(group = @Group(value = "text", label = "Simple Text", attrs = {@Attr(name = "ng-show", value = "${check1}")}), label = "#[text]", inputType = InputField.Types.TEXT, alt = "")
    @Validation(required = true, minLength = 20, maxLength = 50, pattern = "")
    private String text;

    @InputField(group = @Group("text"), label = "#[email]", inputType = InputField.Types.EMAIL, alt = "", attrs = {@Attr(name = "ng-required", value = "${check1}")})
    @Validation(required = true, message = "")
    private String email;

    @InputField(group = @Group(value = "other", label = "Other Inputs"), inputType = InputField.Types.NUMBER, mask = "999999", alt = "")
    @Validation(required = true, minValue = "5", maxValue = "30")
    private Integer number;

    @InputField(group = @Group("other"), label = "#[birthDate]", inputType = InputField.Types.DATE, mask = "#[fields.birthDate.mask]")
    @Validation(required = true, maxValue = "{{new Date()}}")
    private Date birthDate;

    // ###

    @InputField(group = @Group(value = "select", label = "Select"), label = "selectByEnum", inputType = InputField.Types.SELECT)
    @SelectField(options = {@Option(key = "Enum-1", value = "F"), @Option(key = "Enum-2", value = "M")})
    @Validation(required = true)
    private SelectEnum selectByEnum;

    @InputField(group = @Group("select"), label = "#[SelectByLocalMethod]", inputType = InputField.Types.SELECT)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.LOCAL_METHOD, id = "regions", bindLabel = "value"))
    // @BuscaEmLinha()
    // @AutoComplete()
    // @BuscaModal()
    @Validation(required = true)
    private DoubleValue selectByLocalMethod;

    @InputField(group = @Group("select"), label = "#[SelectByControllerMethod]", inputType = InputField.Types.SELECT)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.REST_RESOURCE, id = "restMetadataController.regionsByClientId", args = {"${selectByLocalMethod.id}"}, bindLabel = "value", bindValue = "id", groupBy = "group"))
    @Validation(required = true)
    private List<Integer> selectByControllerMethod = new ArrayList<Integer>();

    @InputField(group = @Group("select"), label = "#[SelectByControllerURL]", inputType = InputField.Types.SELECT)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.REST_RESOURCE, id = "url:/restmetadata/selectProvider/${selectByLocalMethod.id}/getAllRegionsById", bindLabel = "value", groupBy = "group"))
    @Validation(required = true)
    private DoubleValue selectByControllerURL;

    @InputField(group = @Group("select"), label = "#[SelectByBeanName]", inputType = InputField.Types.SELECT)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.BEAN, id = "skillService.skills"))
    @Validation(required = true)
    private List<DoubleValue> selectsByBeanName = new ArrayList<DoubleValue>();

    @InputField(group = @Group("select"), label = "SelectByBeanClass", inputType = InputField.Types.SELECT)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.CLASS, id = "occupations", impl = OccupationsProviderNoService.class, bindValue = "id"))
    @Validation(required = true)
    private Integer selectByBeanClass;

    @InputField(group = @Group("select"), label = "SelectLocalValues", inputType = InputField.Types.SELECT)
    @SelectField(options = {@Option(key = "0 - 1000", value = "1"), @Option(key = "1001 - 5000", value = "2"),
            @Option(key = "5001 - 10000", value = "3")})
    @Validation(required = true)
    private Integer selectLocalValues;

    // ### - Checkbox

    @InputField(group = @Group(value = "checkbox", label = "Checkbox"), label = "SimpleCheckbox", inputType = InputField.Types.CHECKBOX, alt = "")
    private Boolean check1;

    @InputField(group = @Group("checkbox"), label = "CheckboxByEnum", inputType = InputField.Types.CHECKBOX)
    @SelectField(options = {@Option(key = "Enum-1", value = "F"), @Option(key = "Enum-2", value = "M")})
    @Validation(required = true)
    private List<SelectEnum> checkboxByEnum = new ArrayList<SelectEnum>();

    @InputField(group = @Group("checkbox"), label = "#[CheckboxByLocalMethod]", inputType = InputField.Types.CHECKBOX)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.LOCAL_METHOD, id = "regions", bindLabel = "value"))
    @Validation(required = true)
    private List<DoubleValue> checkboxByLocalMethod = new ArrayList<DoubleValue>();

    @InputField(group = @Group("checkbox"), label = "CheckboxByLocalValues", inputType = InputField.Types.CHECKBOX)
    @SelectField(options = {@Option(key = "0 - 1000", value = "1"), @Option(key = "1001 - 5000", value = "2"),
            @Option(key = "5001 - 10000", value = "3")})
    @Validation(required = true)
    private List<Integer> checkboxByLocalValues = new ArrayList<Integer>();

    @InputField(group = @Group("checkbox"), label = "#[CheckboxByControllerURL]", inputType = InputField.Types.CHECKBOX)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.REST_RESOURCE, id = "url:/restmetadata/selectProvider/${selectByLocalMethod.id}/getRegionsByClient", bindLabel = "value", bindValue = "id"))
    @Validation(required = true)
    private List<Integer> checkboxByControllerURL = new ArrayList<Integer>();

    // ### - Radio

    @InputField(group = @Group(value = "radio", label = "Radio"), label = "RadioByEnum", inputType = InputField.Types.RADIO)
    @SelectField(options = {@Option(key = "Enum-1", value = "F"), @Option(key = "Enum-2", value = "M")})
    @Validation(required = true)
    private List<SelectEnum> radioByEnum = new ArrayList<SelectEnum>();

    @InputField(group = @Group("radio"), label = "#[RadioByLocalMethod]", inputType = InputField.Types.RADIO)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.LOCAL_METHOD, id = "regions", bindLabel = "value"))
    @Validation(required = true)
    private List<DoubleValue> radioByLocalMethod = new ArrayList<DoubleValue>();

    @InputField(group = @Group("radio"), label = "RadioByLocalValues", inputType = InputField.Types.RADIO)
    @SelectField(options = {@Option(key = "0 - 1000", value = "1"), @Option(key = "1001 - 5000", value = "2"),
            @Option(key = "5001 - 10000", value = "3")})
    @Validation(required = true)
    private List<Integer> radioByLocalValues = new ArrayList<Integer>();

    @InputField(group = @Group("radio"), label = "#[RadioByControllerURL]", inputType = InputField.Types.RADIO)
    @SelectField(provider = @OptionsProvider(type = OptionsProvider.ProviderTypes.REST_RESOURCE, id = "url:/restmetadata/selectProvider/${selectByLocalMethod.id}/getRegionsByClient", bindLabel = "value", bindValue = "id"))
    @Validation(required = true)
    private List<Integer> radioByControllerURL = new ArrayList<Integer>();

    // ###

    @ValueOptions("regions")
    public Object getRegions() {

        List<Object> map = new ArrayList<Object>();
        map.add(new DoubleValue(1, "Brasil", "São Paulo"));
        map.add(new DoubleValue(2, "United States", "California"));
        map.add(new DoubleValue(3, "Spanish", "Madrid"));
        map.add(new DoubleValue(4, "Japan", "Hong Kong"));

        return map;
    }

}
