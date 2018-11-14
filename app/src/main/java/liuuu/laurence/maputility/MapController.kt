package liuuu.laurence.maputility

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapController(context: Context, googleMap: GoogleMap) {

    private val mContext: Context
    private val mGoogleMap: GoogleMap

    init {
        this.mContext = context
        this.mGoogleMap = googleMap
    }

    fun setCustomMarker() {
        val timeSquare = LatLng(40.758895, -73.985131)

        val blackMarkerIcon : BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_custom_marker)
        val markerOptions : MarkerOptions = MarkerOptions().position(timeSquare).title(mContext.getString(R.string.time_square)).snippet(mContext.getString(R.string.i_am_snippet)).icon(blackMarkerIcon)
        mGoogleMap.addMarker(markerOptions)
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(timeSquare))
    }

    fun animateCamera() {
        val timeSquare = LatLng(40.758895, -73.985131)

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(timeSquare, 15f))
    }

}