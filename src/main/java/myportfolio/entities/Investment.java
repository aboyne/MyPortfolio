package myportfolio.entities;

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

    public Investment setHistoricalValueAtPurchase(double sharePrice, double numberOfShares)
    {
        this.getHistoricalValues().add(new HistoricalValue(sharePrice, numberOfShares)
                .setCreationDate(purchaseDate.atStartOfDay()));
        return this;
    }

    public String getDocumentKey() {
        return this.instanceId;
    }
}
