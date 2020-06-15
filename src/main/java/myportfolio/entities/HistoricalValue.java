package myportfolio.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import myportfolio.serializers.JsonLocalDateTimeDeserializer;
import myportfolio.serializers.JsonLocalDateTimeSerializer;

import java.time.LocalDateTime;

@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricalValue
{
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonProperty("create_time")
    private LocalDateTime creationDate;

    @JsonProperty("total_value")
    private double value;

    @JsonProperty("individual_share_value")
    private double shareValue;

    @JsonProperty("number_of_shares")
    private double numberOfShares;

    public HistoricalValue(double shareValue, double numberOfShares)
    {
        this.shareValue = shareValue;
        this.numberOfShares = numberOfShares;
        this.value = shareValue * numberOfShares;
    }
}
