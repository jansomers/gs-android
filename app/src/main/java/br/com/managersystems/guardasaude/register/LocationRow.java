package br.com.managersystems.guardasaude.register;

import java.util.HashMap;
import java.util.Map;


public class LocationRow {
    private String locationID;
    private String locationName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The locationID
     */
    public String getLocationID() {
        return locationID;
    }

    /**
     *
     * @param locationID
     * The locationID
     */
    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    /**
     *
     * @return
     * The locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     *
     * @param locationName
     * The locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
