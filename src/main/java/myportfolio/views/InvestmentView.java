package myportfolio.views;

import lombok.Getter;
import lombok.Setter;
import myportfolio.entities.Investment;
import myportfolio.models.InvestmentModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ConversationScoped
@Named(value = "investmentView")
public class InvestmentView implements Serializable
{
    @Inject
    private InvestmentModel investmentModel;

    private String investmentName;

    private double numberOfShares;

    private double sharePrice;

    private LocalDate purchaseDate;

    private List<Investment> investments;

    @PostConstruct
    public void init()
    {
        retrieveAllInvestments();
    }

    public void retrieveAllInvestments()
    {
        investments = investmentModel.getAllInvestments();

    }
}
