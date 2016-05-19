package br.com.managersystems.guardasaude.register.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO that holds the information of a location.
 */
public class LocationRow {
    private String locationID;
    private String locationName;
    private String locationState;
    private String locationValue;

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

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
