package myportfolio.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Investment
{
    @JsonProperty("investment_name")
    private String investmentName;

    @JsonProperty("number_of_shares")
    private double numberOfShares;

    @JsonProperty("value")
    private double value;

    @JsonProperty("submission_date")
    private LocalDateTime submissionDate;
}
