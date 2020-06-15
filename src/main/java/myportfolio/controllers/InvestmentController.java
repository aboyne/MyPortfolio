package myportfolio.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.extern.java.Log;
import myportfolio.dao.InvestmentDao;
import myportfolio.entities.Investment;
import myportfolio.models.InvestmentModel;
import myportfolio.views.InvestmentView;

import javax.enterprise.context.ConversationScoped;
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
@ConversationScoped
@Named(value = "investmentController")
public class InvestmentController implements Serializable
{

    @Inject
    private InvestmentModel investmentModel;

    @Inject
    private InvestmentView investmentView;


    @Getter
    private List<Investment> investments = new ArrayList<>();


    public void addInvestment(LocalDate purchaseDate, String investmentName, double numberOfShares, double sharePrice)
    {
        final Investment investment = new Investment()
                .setInvestmentName(investmentName)
                .setPurchaseDate(purchaseDate)
                .setHistoricalValueAtPurchase(sharePrice, numberOfShares);

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

    public void refreshInvestmentTable()
    {
        investmentView.retrieveAllInvestments();
    }
}
