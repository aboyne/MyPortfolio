package myportfolio.investment;

import lombok.Getter;
import lombok.Setter;
import myportfolio.entities.Investment;
import myportfolio.investment.InvestmentPresenter;

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
    private String investmentName;

    private String stockTicker;

    private double numberOfShares;

    private double sharePrice;

    private LocalDate purchaseDate;

    private List<DisplayableInvestment> investments;

    @Inject
    private InvestmentPresenter investmentPresenter;

    @PostConstruct
    public void init()
    {
        refreshInvestments();
    }

    public void refreshInvestments()
    {
        setInvestments(investmentPresenter.getAllInvestments());
    }

}
