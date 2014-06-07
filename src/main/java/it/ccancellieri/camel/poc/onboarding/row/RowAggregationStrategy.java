package it.ccancellieri.camel.poc.onboarding.row;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class RowAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        final List<Map<Object, Object>> oldList = RowExchangeUtils.getBodyAsListOfMaps(oldExchange);
        List<Map<Object, Object>> newList = RowExchangeUtils.getBodyAsListOfMaps(newExchange);
        if (oldList != null) {
            if (newList != null) {
                newList.addAll(oldList);
            } else {
                newList = oldList;
            }
        } else if (newList == null) {
            // empty response (may be we want to throw an exception?)
            newList = new ArrayList<Map<Object, Object>>(0);
        }
        // setup the output with the merged list
        newExchange.getIn().setBody(newList);

        return newExchange;
    }

}
