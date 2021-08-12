package cc.sivir.errorpagegenerator;

import lombok.Data;

/**
 * @author qingke
 **/
@Data
public class HttpStatusModel {
    private String value;
    private String reasonPhrase;
}
