package it.ccancellieri.camel.poc.onboarding.row;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

public final class RowExchangeUtils {

    @SuppressWarnings("unchecked")
    public static final List<Map<Object, Object>> getBodyAsListOfMaps(Exchange exchange) {
        if (exchange == null)
            return null;
        final Message m = exchange.getIn();
        if (m == null)
            return null;
        return m.getBody(List.class);
    }

    private RowExchangeUtils() {
    }
}
