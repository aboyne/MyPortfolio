package myportfolio.dao;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.Protocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import myportfolio.entities.Investment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInvestmentDao
{
    private ArangoDatabase dbCon;

    private InvestmentDao investmentDao;

    private static final String INVESTMENT_COLLECTION = "investment";

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

        investmentDao = new InvestmentDao(dbCon);
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

    //TODO Test able to add another investment with same stock name as existing

    //TODO Test able to retrieve a specific investment

    //TODO Test able to retrieve all instances of a specific investment

    //TODO Test able to retrieve all investments
}
