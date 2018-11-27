package liuuu.laurence.maputility.model.google.nearbySearch

import com.google.gson.annotations.SerializedName

data class Location(@SerializedName("lng")
                    val lng: Double = 0.0,
                    @SerializedName("lat")
                    val lat: Double = 0.0)