package myportfolio.investment;

import myportfolio.entities.Investment;

public class InvestmentConverter
{
    public static DisplayableInvestment convert(Investment investment)
    {
        final DisplayableInvestment displayableInvestment =  new DisplayableInvestment()
                .setInstanceId(investment.getInstanceId())
                .setHistoricalValues(investment.getHistoricalValues())
                .setInvestmentName(investment.getInvestmentName())
                .setPurchaseDate(investment.getPurchaseDate())
                .setStockTicker(investment.getStockTicker());

        displayableInvestment.setPercentageChange(calculatePercentageChange(displayableInvestment));
        return displayableInvestment;
    }

    private static double calculatePercentageChange(final DisplayableInvestment investment)
    {
        double initialInvestment = investment.getInitialInvestmentValue();
        return (investment.getCurrentInvestmentValue() - initialInvestment)
                / initialInvestment
                * 100;
    }

    public static Investment convert(DisplayableInvestment displayableInvestment)
    {

        final Investment investment =  new Investment()
                .setInstanceId(displayableInvestment.getInstanceId())
                .setHistoricalValues(displayableInvestment.getHistoricalValues())
                .setInvestmentName(displayableInvestment.getInvestmentName())
                .setPurchaseDate(displayableInvestment.getPurchaseDate())
                .setStockTicker(displayableInvestment.getStockTicker());

        return investment;
    }

}
