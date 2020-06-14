package myportfolio.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import myportfolio.serializers.JsonLocalDateTimeDeserializer;
import myportfolio.serializers.JsonLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Investment extends AbstractArangoDocument
{
    @JsonProperty("instance_id")
    private String instanceId = UUID.randomUUID().toString();

    @JsonProperty("investment_name")
    private String investmentName;

    @JsonProperty("value")
    private List<HistoricalValue> historicalValue = new ArrayList<>();

    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonProperty("submission_date")
    private LocalDateTime submissionDate;



    @JsonProperty("number_of_shares")
    private double numberOfShares;

    public String getDocumentKey() {
        return this.instanceId;
    }
}
