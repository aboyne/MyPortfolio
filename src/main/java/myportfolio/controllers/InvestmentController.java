package myportfolio.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import myportfolio.dao.InvestmentDao;
import myportfolio.entities.Investment;
import myportfolio.investment.DisplayableInvestment;
import myportfolio.investment.InvestmentConverter;
import myportfolio.processors.UpdateInvestments;
import myportfolio.investment.InvestmentView;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log
@SessionScoped
@Named(value = "investmentController")
public class InvestmentController implements Serializable
{
    private InvestmentDao investmentDao = new InvestmentDao();

    @Inject
    private InvestmentView investmentView;

    public void addInvestment(LocalDate purchaseDate, String investmentName, String stockTicker,  double numberOfShares, double sharePrice)
    {
        final Investment investment = new Investment()
                .setInvestmentName(investmentName)
                .setStockTicker(stockTicker)
                .setPurchaseDate(purchaseDate)
                .addHistoricalValue(sharePrice, numberOfShares, purchaseDate);

        try
        {
            investmentDao.addInvestment(investment);
            refreshInvestmentTable();
        }
        catch (JsonProcessingException e)
        {
            log.severe("Unable to add investment: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to add investment!", ""));
        }
    }

    public void saveInvestments() throws JsonProcessingException
    {
        for (DisplayableInvestment investment : investmentView.getInvestments())
        {
            investmentDao.updateInvestment(InvestmentConverter.convert(investment));
        }
    }

    public void refreshInvestmentTable()
    {
        investmentView.refreshInvestments();
    }

    public void delete(Investment investment)
    {
        investmentDao.deleteInvestment(investment);
        investmentView.refreshInvestments();
    }

    public void updateInvestmentStockPrices()
    {
        UpdateInvestments updateInvestments = new UpdateInvestments();
        try
        {
            updateInvestments.updateAllInvestmentStockPrices();
            investmentView.refreshInvestments();
        }
        catch (IOException e)
        {
            log.severe("Unable to update investment stock prices: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to update investment stock prices", ""));
        }
    }

    public List<Investment> getAllInvestments()
    {
        try
        {
            return investmentDao.getAllInvestments();
        }
        catch (IOException e)
        {
            log.severe("Unable to get all investments: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get all investments.", ""));

        }
        return new ArrayList<>();
    }
}
