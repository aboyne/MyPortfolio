package myportfolio.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import myportfolio.dao.InvestmentDao;
import myportfolio.entities.Investment;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    @Getter
    private List<Investment> investments = new ArrayList<>();

    public void addInvestment(LocalDate purchaseDate, String investmentName, double numberOfShares)
    {
        final Investment investment = new Investment()
                .setInvestmentName(investmentName)
                .setNumberOfShares(numberOfShares)
                .setPurchaseDate(purchaseDate);

        try
        {
            new InvestmentDao().addInvestment(investment);
        }
        catch (JsonProcessingException e)
        {
            log.severe("Unable to add investment: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to add investment!", ""));
        }
    }

    public void getAllInvestments()
    {
        try
        {
            investments = new InvestmentDao().getAllInvestments();
        }
        catch (IOException e)
        {
            log.severe("Unable to get all investments: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to get all investments.", ""));

        }
    }





}
