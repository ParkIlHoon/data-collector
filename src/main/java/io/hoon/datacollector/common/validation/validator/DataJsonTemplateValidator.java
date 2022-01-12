package io.hoon.datacollector.common.validation.validator;

import io.hoon.datacollector.common.validation.annotation.DataJsonTemplate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * <h1>수집 데이터 JSON Template Validator</h1>
 */
public class DataJsonTemplateValidator implements ConstraintValidator<DataJsonTemplate, String> {

    private static final int ROOT_KEY_LIMIT_SIZE = 1;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject parsedObject = (JSONObject) jsonParser.parse(value);

            if (!isAllowedKeySize(parsedObject)) {
                return false;
            }

            if (!isObject(parsedObject)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * JSON root object 의 key 개수가 허용된 만큼인지 여부를 체크합니다.
     *
     * @param parsedObject JSON root object
     * @return 허용 여부
     */
    private boolean isAllowedKeySize(JSONObject parsedObject) {
        return parsedObject.keySet().size() <= ROOT_KEY_LIMIT_SIZE;
    }

    /**
     * JSON root object 의 value 가 object 인지 여부를 체크합니다.
     *
     * @param parsedObject JSON root object
     * @return object 여부
     */
    private boolean isObject(JSONObject parsedObject) {
        for (Object key : parsedObject.keySet()) {
            if (parsedObject.get(key) instanceof JSONObject == false) {
                return false;
            }
        }
        return true;
    }
}
