package liuuu.laurence.maputility.model.google.directions

import com.google.gson.annotations.SerializedName

data class GeocodedWaypointsItem(@SerializedName("types")
                                 val types: List<String>?,
                                 @SerializedName("geocoder_status")
                                 val geocoderStatus: String = "",
                                 @SerializedName("place_id")
                                 val placeId: String = "")