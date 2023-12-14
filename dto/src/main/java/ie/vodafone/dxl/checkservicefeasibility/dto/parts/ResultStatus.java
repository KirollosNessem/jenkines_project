package ie.vodafone.dxl.checkservicefeasibility.dto.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String timestamp;
    private String message;
    private String reasonCode;
    private List<Failure> failures;
}

