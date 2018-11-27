package liuuu.laurence.maputility

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import liuuu.laurence.maputility.model.others.Spot
import com.google.android.gms.maps.model.*
import liuuu.laurence.maputility.model.others.Route
import com.google.maps.android.PolyUtil
import com.google.android.gms.maps.model.LatLng

class MapsController(context: Context, googleMap: GoogleMap) {

    private val mContext: Context = context
    private val mGoogleMap: GoogleMap = googleMap

    private val mTimeSquare = LatLng(40.758895, -73.985131)

    private var mSpotMarkerList = ArrayList<Marker>()

    private var mRouteMarkerList = ArrayList<Marker>()
    private lateinit var mRoutePolyline: Polyline

    fun setCustomMarker() {
        val blackMarkerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_custom_marker)
        val markerOptions: MarkerOptions = MarkerOptions().position(mTimeSquare).title(mContext.getString(R.string.time_square)).snippet(mContext.getString(R.string.i_am_snippet)).icon(blackMarkerIcon)
        mGoogleMap.addMarker(markerOptions)
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mTimeSquare))
    }

    fun animateZoomInCamera() {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mTimeSquare, 15f))
    }

    fun animateZoomOutCamera() {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mTimeSquare, 10f))
    }

    fun setMarkersAndZoom(spotList: List<Spot>) {
        val spotBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_spot_marker)

        for (spot in spotList) {
            val name = spot.name
            val latitude = spot.lat
            val longitude = spot.lng
            val latLng = LatLng(latitude!!, longitude!!)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng).title(name).icon(spotBitmap)

            val marker = mGoogleMap.addMarker(markerOptions)
            mSpotMarkerList.add(marker)
        }

        mGoogleMap.animateCamera(MapsFactory.autoZoomLevel(mSpotMarkerList))
    }

    fun clearMarkers() {
        for (marker in mSpotMarkerList) {
            marker.remove()
        }
        mSpotMarkerList.clear()
    }

    fun setMarkersAndRoute(route: Route) {
        val startLatLng = LatLng(route.startLat!!, route.startLng!!)
        val startMarkerOptions: MarkerOptions = MarkerOptions().position(startLatLng).title(route.startName).icon(BitmapDescriptorFactory.fromBitmap(MapsFactory.drawMarker(mContext, "S")))
        val endLatLng = LatLng(route.endLat!!, route.endLng!!)
        val endMarkerOptions: MarkerOptions = MarkerOptions().position(endLatLng).title(route.endName).icon(BitmapDescriptorFactory.fromBitmap(MapsFactory.drawMarker(mContext, "E")))
        val startMarker = mGoogleMap.addMarker(startMarkerOptions)
        val endMarker = mGoogleMap.addMarker(endMarkerOptions)
        mRouteMarkerList.add(startMarker)
        mRouteMarkerList.add(endMarker)

        val polylineOptions = MapsFactory.drawRoute(mContext)
        val pointsList = PolyUtil.decode(route.overviewPolyline)
        for (point in pointsList) {
            polylineOptions.add(point)
        }

        mRoutePolyline = mGoogleMap.addPolyline(polylineOptions)

        mGoogleMap.animateCamera(MapsFactory.autoZoomLevel(mRouteMarkerList))
    }

    fun clearMarkersAndRoute() {
        for (marker in mRouteMarkerList) {
            marker.remove()
        }
        mRouteMarkerList.clear()

        if (::mRoutePolyline.isInitialized) {
            mRoutePolyline.remove()
        }
    }
}