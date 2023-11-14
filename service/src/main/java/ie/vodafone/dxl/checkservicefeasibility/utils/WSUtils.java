package ie.vodafone.dxl.checkservicefeasibility.utils;


import com.vodafone.group.schema.common.v1.BaseComponentType;
import com.vodafone.group.schema.common.v1.CharacteristicsType;
import com.vodafone.group.schema.common.v1.GeographicLocationType;
import com.vodafone.group.schema.common.v1.InfoComponentType;
import com.vodafone.group.schema.common.v1.PostalAddressWithLocationType;
import com.vodafone.group.schema.common.v1.QueryExpressionType;
import com.vodafone.group.schema.common.v1.QueryOperatorCodeType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.common.v1.ValidityPeriodType;
import com.vodafone.group.schema.common.v1.ValueExpressionType;
import ie.vodafone.dxl.utils.common.StringUtils;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.AmountType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.CodeType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.DateTimeType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.DateType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IDType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IndicatorType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.MeasureType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.NumericType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.TextType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WSUtils {

    public static void addCriteriaIfExist(QueryExpressionType queryExpressionType, String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            ValueExpressionType expressionValue = buildValueExpression(path, value);
            queryExpressionType.getValueExpression().add(expressionValue);
        }

    }

    public static ValueExpressionType buildValueExpression(String path, String value) {
        ValueExpressionType valueExpression = new ValueExpressionType();
        valueExpression.setPath(path);
        TextType textValue = new TextType();
        textValue.setValue(value);
        valueExpression.setQueryOperatorCode(QueryOperatorCodeType.EQUALS);
        valueExpression.getValue().add(textValue);
        return valueExpression;
    }

    public static TextType createTextType(String value) {
        TextType text = new TextType();
        text.setValue(value);
        return text;
    }

    public static CodeType createCodeType(String value) {
        CodeType codeType = new CodeType();
        codeType.setValue(value);
        return codeType;
    }

    public static IDType createID(String value, String name) {
        IDType id = new IDType();
        id.setValue(value);
        if (StringUtils.isNotBlank(name)) {
            id.setSchemeName(name);
        }
        return id;
    }

    public static ValidityPeriodType createValidityPeriodType(String fromDate, String toDate) {
        if (StringUtils.isNotBlank(fromDate) || StringUtils.isNotBlank(toDate)) {
            ValidityPeriodType validityPeriodType = new ValidityPeriodType();

            if (StringUtils.isNotBlank(fromDate)) {
                validityPeriodType.setFromDate(WSUtils.convertToDateType(fromDate));
            }

            if (StringUtils.isNotBlank(toDate)) {
                validityPeriodType.setToDate(WSUtils.convertToDateType(toDate));
            }

            return validityPeriodType;
        }

        return null;
    }

    public static SpecificationType.CharacteristicsValue createSpecificationCharacteristic(String value, String name) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        SpecificationType.CharacteristicsValue characteristicsValueType = new SpecificationType.CharacteristicsValue();
        characteristicsValueType.setCharacteristicName(name);
        characteristicsValueType.setValue(WSUtils.createTextType(value));
        return characteristicsValueType;
    }

    public static String getSpecificationCharacteristicValue(SpecificationType.CharacteristicsValue characteristic) {
        return characteristic.getValue() != null && StringUtils.isNotBlank(characteristic.getValue().getValue()) ? characteristic.getValue().getValue() : null;
    }

    public static CharacteristicsType.CharacteristicsValue createCharacteristic(String value, String name) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        CharacteristicsType.CharacteristicsValue characteristicsValueType = new CharacteristicsType.CharacteristicsValue();
        characteristicsValueType.setCharacteristicName(name);
        characteristicsValueType.setValue(WSUtils.createTextType(value));
        return characteristicsValueType;
    }

    public static String getCharacteristicValue(CharacteristicsType.CharacteristicsValue characteristic) {
        return characteristic.getValue() != null && StringUtils.isNotBlank(characteristic.getValue().getValue()) ? characteristic.getValue().getValue() : null;
    }

    public static DateType convertToDateType(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        DateType dateType = new DateType();
        DateType.DateString dateString = new DateType.DateString();
        dateString.setValue(date);
        dateType.setDateString(dateString);

        return dateType;
    }

    public static DateTimeType convertStringToDateTimeType(String value, String format) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(value);

        if (StringUtils.isNotBlank(format)) {
            dateTimeType.setFormat(format);
        }

        return dateTimeType;
    }

    public static String convertDateTimeTypeToString(DateTimeType dateTimeType) {
        return dateTimeType != null && StringUtils.isNotBlank(dateTimeType.getValue()) ? dateTimeType.getValue() : null;
    }

    public static String getValidityPeriodFromDate(ValidityPeriodType validityPeriodType) {
        return validityPeriodType != null && validityPeriodType.getFromDate() != null && validityPeriodType.getFromDate().getDateString() != null ?
                validityPeriodType.getFromDate().getDateString().getValue() : null;
    }

    public static String getValidityPeriodToDate(ValidityPeriodType validityPeriodType) {
        return validityPeriodType != null && validityPeriodType.getToDate() != null && validityPeriodType.getToDate().getDateString() != null ?
                validityPeriodType.getToDate().getDateString().getValue() : null;
    }

    public static String getValueFromTextType(TextType textType) {
        return textType != null && StringUtils.isNotBlank(textType.getValue()) ? textType.getValue() : null;
    }

    public static String getValueFromCodeType(CodeType codeType) {
        return codeType != null && StringUtils.isNotBlank(codeType.getValue()) ? codeType.getValue() : null;
    }

    public static String getValueFromListNameCodeType(CodeType codeType) {
        return codeType != null && StringUtils.isNotBlank(codeType.getListName()) ? codeType.getListName() : null;
    }

    public static NumericType createNumericType(String value) {
        NumericType numericType = new NumericType();
        numericType.setValue(value);
        return numericType;
    }

    public static BigDecimal getValueFromMeasureType(MeasureType latitudeMeasure) {
        return latitudeMeasure != null && latitudeMeasure.getValue() != null ? latitudeMeasure.getValue() : null;
    }

    public static void addCharacteristic(List<CharacteristicsType.CharacteristicsValue> characteristicsValues, String value, String name) {
        if (StringUtils.isNotBlank(value)) {
            characteristicsValues.add(WSUtils.createCharacteristic(value, name));
        }
    }

    public static GeographicLocationType createGeographicLocation(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null && longitude == null) {
            return null;
        }

        GeographicLocationType geographicLocationType = new GeographicLocationType();
        geographicLocationType.setLatitudeMeasure(createMeasureType(latitude));
        geographicLocationType.setLongitudeMeasure(createMeasureType(longitude));

        return geographicLocationType;
    }

    private static MeasureType createMeasureType(BigDecimal value) {
        if (value == null) {
            return null;
        }
        MeasureType measureType = new MeasureType();
        measureType.setValue(value);
        return measureType;
    }


    public static DateTimeType createDateTimeType(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(value);

        return dateTimeType;
    }

    public static BaseComponentType.Categories.Category createCategory(String value, String name, String type) {
        BaseComponentType.Categories.Category category = new BaseComponentType.Categories.Category();
        category.setValue(value);
        if (Constants.Category.CATEGORY_NAME.equals(type)) {
            category.setName(name);
        } else if (Constants.Category.CATEGORY_LISTNAME.equals(type)) {
            category.setListName(name);
        } else if (Constants.Category.CATEGORY_LISTAGENCYNAME.equals(type)) {
            category.setListAgencyName(name);
        }

        return category;
    }

    public static String getValueFromDateType(DateTimeType date) {
        return date != null && StringUtils.isNotBlank(date.getValue()) ? date.getValue() : null;
    }

    public static void addSpecificationCharacteristicValueIfExists(List<SpecificationType.CharacteristicsValue> characteristicsValues, String value, String name) {
        if (StringUtils.isNotBlank(value)) {
            if (characteristicsValues == null) {
                characteristicsValues = new ArrayList<>();
            }

            characteristicsValues.add(WSUtils.createSpecificationCharacteristic(value, name));
        }
    }

    public static void addIdIfExists(InfoComponentType.IDs iDs, String value, String name) {
        if (StringUtils.isNotBlank(value)) {
            if (iDs == null) {
                iDs = new InfoComponentType.IDs();
            }

            iDs.getID().add(WSUtils.createID(value, name));
        }
    }

    public static void addIdIfExists(PostalAddressWithLocationType.IDs iDs, String value, String name) {
        if (StringUtils.isNotBlank(value)) {
            if (iDs == null) {
                iDs = new PostalAddressWithLocationType.IDs();
            }

            iDs.getID().add(WSUtils.createID(value, name));
        }
    }

    public static void addCategoryIfExists(BaseComponentType.Categories categories, String value, String name, String type) {
        if (StringUtils.isNotBlank(value)) {
            if (categories == null) {
                categories = new BaseComponentType.Categories();
            }

            categories.getCategory().add(WSUtils.createCategory(value, name, type));
        }
    }

    public static String getValueFromIDType(IDType idType) {
        return idType != null && StringUtils.isNotBlank(idType.getValue()) ? idType.getValue() : null;
    }

    public static IndicatorType addIndicatorTypeValue(Boolean value) {
        IndicatorType indicatorType = new IndicatorType();
        indicatorType.setIndicator(value);
        return indicatorType;
    }

    public static IndicatorType addIndicatorTypeStringValue(String value) {
        IndicatorType indicatorType = new IndicatorType();
        IndicatorType.IndicatorString indicatorString = new IndicatorType.IndicatorString();
        indicatorString.setValue(value);
        indicatorType.setIndicatorString(indicatorString);
        return indicatorType;
    }

    public static AmountType createAmountType(BigDecimal price) {
        AmountType amountType = new AmountType();
        amountType.setValue(price);

        return amountType;
    }


}
