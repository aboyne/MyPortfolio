package myportfolio.dao;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.Protocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import myportfolio.entities.Investment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        LocalDateTime now = LocalDateTime.now();

        final Investment doc = new Investment()
                .setSubmissionDate(now)
                .setNumberOfShares(5)
                .setInvestmentName("TestInvestment");

        final String uuid = doc.getInstanceId();

        investmentDao.addInvestment(doc);

        assertTrue(dbCon.collection(INVESTMENT_COLLECTION).documentExists(uuid));

        investmentDao.deleteInvestment(doc);
        assertFalse(dbCon.collection(INVESTMENT_COLLECTION).documentExists(uuid));
    }


    @Test
    void verifyAbleToAddMultipleInvestmentsWithSameName() throws IOException
    {
        LocalDateTime now = LocalDateTime.now();

        final String investmentName = "TestInvestment";

        final Investment doc = new Investment()
                .setSubmissionDate(now)
                .setNumberOfShares(5)
                .setInvestmentName(investmentName);

        final Investment doc2 = new Investment()
                .setSubmissionDate(now.plusDays(1))
                .setNumberOfShares(15)
                .setInvestmentName(investmentName);


        final String investmentA = doc.getInstanceId();
        final String investmentB = doc2.getInstanceId();

        investmentDao.addInvestment(doc);
        investmentDao.addInvestment(doc2);

        assertTrue(dbCon.collection(INVESTMENT_COLLECTION).documentExists(investmentA));
        assertTrue(dbCon.collection(INVESTMENT_COLLECTION).documentExists(investmentB));

        List<Investment> investments = investmentDao.getAllInvestments(investmentName);

        assertEquals(2, investments.size());
        assertEquals(5, investments.get(0).getNumberOfShares());
        assertEquals(15, investments.get(1).getNumberOfShares());

        investmentDao.deleteInvestment(doc);
        investmentDao.deleteInvestment(doc2);

        assertFalse(dbCon.collection(INVESTMENT_COLLECTION).documentExists(investmentA));
        assertFalse(dbCon.collection(INVESTMENT_COLLECTION).documentExists(investmentB));

    }
}
