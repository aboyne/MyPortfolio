package myportfolio.dao;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.Protocol;
import com.arangodb.util.MapBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import myportfolio.entities.Investment;
import myportfolio.utils.JsonUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvestmentDao
{

    private ArangoDatabase dbCon;

    private String investmentCollection = "investment";

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

    InvestmentDao(final ArangoDatabase dbCon, final String investmentCollection)
    {
        this.dbCon = dbCon;
        this.investmentCollection = investmentCollection;

    }

    public void addInvestment(Investment investment) throws JsonProcessingException
    {
        final String json = new ObjectMapper().writeValueAsString(investment);

        dbCon.collection(investmentCollection).insertDocument(json);
    }

    public void deleteInvestment(Investment investment)
    {
        final String aql = "FOR rec IN " + investmentCollection
                + " FILTER rec._key == @UUID"
                + " REMOVE rec IN " + investmentCollection;

        final Map<String, Object> params = new MapBuilder()
                .put("UUID", investment.getInstanceId()).get();

        dbCon.query(aql, params, null, String.class);
    }

    public Optional<Investment> getInvestment(String instanceId) throws IOException
    {
        final String aql = "FOR rec IN " + investmentCollection +
                " FILTER rec._key == @UUID" +
                " RETURN rec";

        final Map<String, Object> params = new MapBuilder()
                .put("UUID", instanceId).get();

        try (final ArangoCursor<String> cursor = dbCon.query(aql, params, null, String.class))
        {
            if (cursor.hasNext())
            {
                return Optional.of(JsonUtils.readValue(cursor.next(), Investment.class));
            }
        }
        return Optional.empty();
    }

    public List<Investment> getMatchingInvestments(String investmentName) throws IOException
    {
        final String aql = "FOR rec IN " + investmentCollection +
                " FILTER rec.investment_name == @INVESTMENT_NAME" +
                " RETURN rec";

        final Map<String, Object> params = new MapBuilder()
                .put("INVESTMENT_NAME", investmentName).get();

        try (final ArangoCursor<String> cursor = dbCon.query(aql, params, null, String.class))
        {
            return cursor.asListRemaining()
                    .stream()
                    .map(s -> JsonUtils.readValue(s, Investment.class))
                    .collect(Collectors.toList());
        }
    }

    public List<Investment> getAllInvestments() throws IOException
    {
        final String aql = "FOR rec IN " + investmentCollection +
                " RETURN rec";

        try (final ArangoCursor<String> cursor = dbCon.query(aql, null, null, String.class))
        {
            return cursor.asListRemaining()
                    .stream()
                    .map(s -> JsonUtils.readValue(s, Investment.class))
                    .collect(Collectors.toList());
        }
    }

    public void updateInvestment(Investment investment) throws JsonProcessingException
    {
        if (dbCon.collection(investmentCollection).documentExists(investment.getDocumentKey()))
        {
            dbCon.collection(investmentCollection).updateDocument(investment.getDocumentKey(), new ObjectMapper().writeValueAsString(investment));
        }
    }


}
