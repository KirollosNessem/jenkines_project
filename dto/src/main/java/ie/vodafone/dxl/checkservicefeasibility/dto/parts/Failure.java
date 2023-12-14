package ie.vodafone.dxl.checkservicefeasibility.dto.parts;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class Failure implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pathName;
    private String pathValueText;
    private String code;
    private String text;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty(pathName)) {
            stringBuilder.append("{ PathName: ").append(pathName);
        }

        if (StringUtils.isNotEmpty(pathValueText)) {
            stringBuilder.append(", PathValueText: ").append(pathValueText);
        }

        if (StringUtils.isNotEmpty(code)) {
            stringBuilder.append(", Code: ").append(code);
        }

        if (StringUtils.isNotEmpty(text)) {
            stringBuilder.append(", Text: ").append(text).append(" }");
        }
        return stringBuilder.toString();
    }
}
