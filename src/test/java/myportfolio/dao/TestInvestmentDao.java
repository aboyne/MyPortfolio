package myportfolio.dao;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.Protocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import myportfolio.entities.Investment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInvestmentDao
{
    private ArangoDatabase dbCon;

    private InvestmentDao investmentDao;

    private static final String INVESTMENT_COLLECTION = "test_investment";

    @BeforeEach
    void setup()
    {
        dbCon = new ArangoDB.Builder()
                .host("localhost", 8529)
                .user("josh")
                .password("password")
                .maxConnections(10)
                .useProtocol(Protocol.HTTP_VPACK)
                .build().db("portfolio");

        investmentDao = new InvestmentDao(dbCon, INVESTMENT_COLLECTION);
    }

    @Test
    void verifyAbleToAddNewInvestment() throws JsonProcessingException
    {
        LocalDate now = LocalDate.now();

        final Investment doc = new Investment()
                .setPurchaseDate(now)
                .addHistoricalValue(5, 5, LocalDate.of(2010, 1, 1))
                .setInvestmentName("TestInvestment");

        final String uuid = doc.getInstanceId();

        investmentDao.addInvestment(doc);

        assertTrue(dbCon.collection(INVESTMENT_COLLECTION).documentExists(uuid));
    }


    @Test
    void verifyAbleToAddMultipleInvestmentsWithSameName() throws IOException
    {
        LocalDate now = LocalDate.now();

        final String investmentName = "TestInvestment";

        final Investment doc = new Investment()
                .setPurchaseDate(now)
                .addHistoricalValue(5, 5, LocalDate.of(2010, 1, 1))
                .setInvestmentName(investmentName);

        final Investment doc2 = new Investment()
                .setPurchaseDate(now.plusDays(1))
                .addHistoricalValue(5, 15, LocalDate.of(2010, 1, 1))
                .setInvestmentName(investmentName);


        final String investmentA = doc.getInstanceId();
        final String investmentB = doc2.getInstanceId();

        investmentDao.addInvestment(doc);
        investmentDao.addInvestment(doc2);

        assertTrue(dbCon.collection(INVESTMENT_COLLECTION).documentExists(investmentA));
        assertTrue(dbCon.collection(INVESTMENT_COLLECTION).documentExists(investmentB));

        List<Investment> investments = investmentDao.getMatchingInvestments(investmentName);

        assertEquals(2, investments.size());
        assertEquals(5, investments.get(0).getHistoricalValues().get(0).getNumberOfShares());
        assertEquals(15, investments.get(1).getHistoricalValues().get(0).getNumberOfShares());

    }

    @AfterEach
    void cleardown() throws IOException
    {
        investmentDao.getAllInvestments().forEach(investment -> investmentDao.deleteInvestment(investment));
    }
}
