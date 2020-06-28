package myportfolio.investment;

import myportfolio.controllers.InvestmentController;
import myportfolio.entities.Investment;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ConversationScoped
@Named(value = "investmentPresenter")
public class InvestmentPresenter implements Serializable
{
    @Inject
    private InvestmentController investmentController;

    @Inject
    public List<DisplayableInvestment> getAllInvestments()
    {
        final List<Investment> investments = investmentController.getAllInvestments();
        return convert(investments);
    }

    private List<DisplayableInvestment> convert(List<Investment> investments)
    {
        return investments.stream()
                .map(InvestmentConverter::convert)
                .collect(Collectors.toList());
    }

}
