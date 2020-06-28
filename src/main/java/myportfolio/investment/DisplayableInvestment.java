package myportfolio.investment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import myportfolio.entities.HistoricalValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class DisplayableInvestment
{
    private String instanceId;

    private String investmentName;

    private String stockTicker;

    private List<HistoricalValue> historicalValues = new ArrayList<>();

    private LocalDate purchaseDate;

    private double percentageChange;

    public double getInitialStockPrice()
    {
        return historicalValues.get(0).getShareValue();
    }

    public double getInitialInvestmentValue()
    {
        return historicalValues.get(0).getValue();
    }

    public double getCurrentStockPrice()
    {
        final HistoricalValue recent = historicalValues.get(historicalValues.size()-1);
        return recent.getShareValue();
    }

    public double getCurrentInvestmentValue()
    {
        final HistoricalValue recent = historicalValues.get(historicalValues.size()-1);
        return recent.getValue();
    }
}
