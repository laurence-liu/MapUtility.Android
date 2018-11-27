package liuuu.laurence.maputility.model.google.directions

import com.google.gson.annotations.SerializedName

data class EndLocation(@SerializedName("lng")
                       val lng: Double = 0.0,
                       @SerializedName("lat")
                       val lat: Double = 0.0)