package myportfolio.dao;

import myportfolio.entities.Investment;

public interface InvestmentDAOInterface
{
    void openInvestment(Investment investment);

    void closeInvestment(Investment investment);

    void getInvestment(String investmentName);
}
