package myportfolio.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import myportfolio.entities.Investment;
import myportfolio.models.InvestmentModel;
import myportfolio.processors.UpdateInvestments;
import myportfolio.views.InvestmentView;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Log
@ConversationScoped
@Named(value = "investmentController")
public class InvestmentController implements Serializable
{

    @Inject
    private InvestmentModel investmentModel;

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
            investmentModel.addInvestment(investment);
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
        investmentModel.persistUpdatesToInvestments();
    }


    public void refreshInvestmentTable()
    {
        investmentView.retrieveAllInvestments();
    }

    public void updateInvestmentStockPrices()
    {
        UpdateInvestments updateInvestments = new UpdateInvestments();
        try
        {
            updateInvestments.updateAllInvestmentStockPrices();
        }
        catch (IOException e)
        {
            log.severe("Unable to update investment stock prices: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to update investment stock prices", ""));
        }
    }
}
