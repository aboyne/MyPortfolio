package myportfolio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import myportfolio.serializers.JsonLocalDateDeserializer;
import myportfolio.serializers.JsonLocalDateSerializer;

import java.time.LocalDate;
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

    @JsonProperty("stock_ticker")
    private String stockTicker;

    @JsonProperty("historical_values")
    private List<HistoricalValue> historicalValues = new ArrayList<>();

    @JsonDeserialize(using = JsonLocalDateDeserializer.class)
    @JsonSerialize(using = JsonLocalDateSerializer.class)
    @JsonProperty("purchase_date")
    private LocalDate purchaseDate;

    public void addHistoricalValue(double sharePrice)
    {
        HistoricalValue lastHistoricalValue = historicalValues.get(historicalValues.size()-1);
        HistoricalValue newHistoricalValue = new HistoricalValue(sharePrice, lastHistoricalValue.getNumberOfShares());
        historicalValues.add(newHistoricalValue);
    }

    public Investment addHistoricalValue(double sharePrice, double numberOfShares, LocalDate date)
    {
        this.getHistoricalValues().add(new HistoricalValue(sharePrice, numberOfShares)
                .setCreationDate(date.atStartOfDay()));
        return this;
    }

    public String getDocumentKey() {
        return this.instanceId;
    }

    @JsonIgnore
    public double getInitialStockPrice()
    {
        return historicalValues.get(0).getShareValue();
    }

    @JsonIgnore
    public double getInitialInvestmentValue()
    {
        return historicalValues.get(0).getValue();
    }

    @JsonIgnore
    public double getCurrentStockPrice()
    {
        HistoricalValue recent = historicalValues.get(historicalValues.size()-1);
        return recent.getShareValue();
    }

    @JsonIgnore
    public double getCurrentInvestmentValue()
    {
        HistoricalValue recent = historicalValues.get(historicalValues.size()-1);
        return recent.getValue();
    }

}
