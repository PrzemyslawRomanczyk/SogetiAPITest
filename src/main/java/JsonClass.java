import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class JsonClass {

    @SerializedName("country")
    private String mCountry;
    @SerializedName("country abbreviation")
    private String mCountryAbbreviation;
    @SerializedName("place name")
    private String mPlaceName;
    @SerializedName("places")
    private List<Place> mPlaces;
    @SerializedName("state")
    private String mState;
    @SerializedName("state abbreviation")
    private String mStateAbbreviation;



    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCountryAbbreviation() {
        return mCountryAbbreviation;
    }

    public void setCountryAbbreviation(String countryAbbreviation) {
        mCountryAbbreviation = countryAbbreviation;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String placeName) {
        mPlaceName = placeName;
    }

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public void setPlaces(List<Place> places) {
        mPlaces = places;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getStateAbbreviation() {
        return mStateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        mStateAbbreviation = stateAbbreviation;
    }

    /*@JsonProperty("places")
    private void umarshallNested(Map<String, Object> places) {
        Map<String, String> inside = (Map<String, String>) places.get("0");
        this.mPlaceName = inside.get("place name");


    }

     */


}
