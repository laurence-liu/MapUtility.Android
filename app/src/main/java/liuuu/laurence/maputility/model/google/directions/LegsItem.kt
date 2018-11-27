package liuuu.laurence.maputility.model.google.directions

import com.google.gson.annotations.SerializedName

data class LegsItem(@SerializedName("duration")
                    val duration: Duration,
                    @SerializedName("start_location")
                    val startLocation: StartLocation,
                    @SerializedName("distance")
                    val distance: Distance,
                    @SerializedName("start_address")
                    val startAddress: String = "",
                    @SerializedName("end_location")
                    val endLocation: EndLocation,
                    @SerializedName("end_address")
                    val endAddress: String = "",
                    @SerializedName("steps")
                    val steps: List<StepsItem>?)