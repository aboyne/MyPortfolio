package myportfolio.api.services;

import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import lombok.extern.java.Log;
import myportfolio.api.finnhub.StockCandle;
import myportfolio.utils.CloseableClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Log
public class FinnService
{
private static final String TOKEN = "brjs7snrh5r9g3ota6u0";

    private static final String URL = "https://finnhub.io/api/v1/";

    /**
     * Request all PricingModels for a customerId from a REST endpoint.
     */
    public StockCandle getStockCandle(final String stockTicker)
    {
        try (final CloseableClient client = new CloseableClient(ClientBuilder.newClient().register(JacksonFeatures.class)))
        {
            final ResteasyWebTarget webTarget = (ResteasyWebTarget) client.target(URL)
                    .path(String.format("/quote?symbol=%s&token=%s", stockTicker.toUpperCase(), TOKEN));

            log.fine(() -> String.format("Invoking REST call to retrieve stock price candle for stock ticker: [%s]", stockTicker));

            try (final Response resp = webTarget
                    .request()
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get())
            {
                if (resp.getStatus() == Response.Status.OK.getStatusCode())
                {
                    if (resp.hasEntity())
                    {
                        return resp.readEntity(StockCandle.class);
                    }
                }
            }
            catch (final Exception e)
            {
                log.log(Level.SEVERE, "Failure to retrieve stock price", e);
            }
        }
        return null;
    }
}
