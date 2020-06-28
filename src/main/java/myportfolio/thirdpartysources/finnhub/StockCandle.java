package myportfolio.thirdpartysources.finnhub;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockCandle
{
    @JsonProperty("o")
    private double openPriceOfDay;

    @JsonProperty("h")
    private double highPriceOfDay;

    @JsonProperty("l")
    private double lowPriceOfDay;

    @JsonProperty("c")
    private double currentPrice;

    @JsonProperty("t")
    private long timestamp;

    @JsonProperty("pc")
    private double previousClosePrice;
}
