package myportfolio.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import myportfolio.thirdpartysources.finnhub.StockCandle;
import myportfolio.thirdpartysources.finnhub.FinnService;
import myportfolio.dao.InvestmentDao;
import myportfolio.entities.Investment;

import java.io.IOException;
import java.util.List;

@Log
public class UpdateInvestments
{
    private final FinnService finnService = new FinnService();
    private final InvestmentDao investmentDao = new InvestmentDao();

    public void updateAllInvestmentStockPrices() throws IOException
    {
        final List<Investment> investments = investmentDao.getAllInvestments();

        investments.stream()
                .filter(investment ->  investment.getStockTicker() != null || !investment.getStockTicker().isEmpty())
                .forEach(this::updateInvestment);
    }

    private void updateInvestment(Investment investment)
    {
        StockCandle stockCandle = finnService.getStockCandle(investment.getStockTicker());
        investment.addHistoricalValue(stockCandle.getCurrentPrice());

        try
        {
            investmentDao.updateInvestment(investment);
        }
        catch (JsonProcessingException e)
        {
            log.warning(() -> String.format("Unable to update investment [%s], Exception: [%s]", investment.getStockTicker(), e.getMessage()));
        }
    }

}
