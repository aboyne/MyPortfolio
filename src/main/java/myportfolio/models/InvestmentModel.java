package myportfolio.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import myportfolio.dao.InvestmentDao;
import myportfolio.entities.Investment;
import myportfolio.views.InvestmentView;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
@Log
public class InvestmentModel implements Serializable
{
    @Inject
    private InvestmentView investmentView;

    private InvestmentDao investmentDao = new InvestmentDao();

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

    public void addInvestment(Investment investment) throws JsonProcessingException
    {
        investmentDao.addInvestment(investment);
    }

    public void deleteInvestment(Investment investment)
    {
        investmentDao.deleteInvestment(investment);
    }

    public void persistUpdatesToInvestments() throws JsonProcessingException
    {
        for (Investment investment : investmentView.getInvestments())
        {
            investmentDao.updateInvestment(investment);
        }
    }
}
