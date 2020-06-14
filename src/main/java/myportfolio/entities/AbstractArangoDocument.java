package myportfolio.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbstractArangoDocument
{
    @JsonProperty("_id")
    private String documentID;
    @JsonProperty("_key")
    private String documentKey;
}
