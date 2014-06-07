package it.ccancellieri.camel.poc.onboarding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author carlo cancellieri - PayBay
 */
public class CSVmarshaller {

    public CSVmarshaller() {
    }

    // Some comments here
    public byte[] doHandleCsvAsListOfLists(List<List<String>> csvData) {
        List<String> ret = new ArrayList<String>();
        for (List<String> row : csvData) {
            ret.add(Arrays.toString(row.toArray()) + "\n");
        }
        return Arrays.toString(ret.toArray()).getBytes();
    }

    // Some comments here
    public byte[] doHandleCsvAsListOfMaps(List<Map<Object, Object>> csvData) {
        final StringBuffer buf = new StringBuffer("-------------------------\n");
        
        for (Map<Object, Object> row : csvData) {
            buf.append("ACTION:"+row.get("ACTION_CD").toString()).append("\t");
            for (Object k : row.keySet()) {
                buf.append(row.get(k).toString() + ", ");
            }
            buf.append("\n");
        }
        return buf.toString().getBytes();
    }

}
