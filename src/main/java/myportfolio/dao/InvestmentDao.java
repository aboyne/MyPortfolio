package myportfolio.dao;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.Protocol;
import com.arangodb.util.MapBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import myportfolio.entities.Investment;

import java.util.Map;

public class InvestmentDao
{

    private ArangoDatabase dbCon;

    private static final String INVESTMENT_COLLECTION = "investment";


    public InvestmentDao()
    {
        dbCon = new ArangoDB.Builder()
                .host("localhost", 8529)
                .user("josh")
                .password("password")
                .maxConnections(10)
                .useProtocol(Protocol.HTTP_VPACK)
                .build().db("portfolio");
    }

    InvestmentDao(ArangoDatabase dbCon)
    {
        this.dbCon = dbCon;
    }
    public void addInvestment(Investment investment) throws JsonProcessingException
    {
        final String json = new ObjectMapper().writeValueAsString(investment);

        dbCon.collection(INVESTMENT_COLLECTION).insertDocument(json);
    }

    public void deleteInvestment(Investment investment)
    {
        final String aql = "FOR rec IN " + INVESTMENT_COLLECTION
                + " FILTER rec._key == @UUID"
                + " REMOVE rec IN " + INVESTMENT_COLLECTION;

        final Map<String, Object> params = new MapBuilder()
                .put("UUID", investment.getInstanceId()).get();

        dbCon.query(aql, params, null, String.class);
    }

    public void getInvestment(String investmentName)
    {

    }

    public void getAllInvestments(String investmentName)
    {

    }

    public void updateInvestment(Investment investment)
    {

    }


}
