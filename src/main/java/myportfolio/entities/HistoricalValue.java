package myportfolio.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricalValue
{
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    @JsonProperty("total_value")
    private double value;

    @JsonProperty("individual_share_value")
    private double shareValue;
}
