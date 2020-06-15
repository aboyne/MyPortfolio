package myportfolio.processors;

import myportfolio.api.finnhub.StockCandle;
import myportfolio.api.services.FinnService;
import myportfolio.dao.InvestmentDao;
import myportfolio.entities.HistoricalValue;
import myportfolio.entities.Investment;

import java.io.IOException;
import java.util.List;

public class UpdateInvestments
{

    public void updateAllInvestmentStockPrices() throws IOException
    {
        InvestmentDao investmentDao = new InvestmentDao();

        List<Investment> investments = investmentDao.getAllInvestments();


        FinnService finnService = new FinnService();

        for (Investment investment : investments)
        {
            StockCandle stockCandle = finnService.getStockCandle(investment.getStockTicker());
            HistoricalValue historicalValue = investment.getHistoricalValues().get(investment.getHistoricalValues().size() - 1);

        }

    }
}
