package liuuu.laurence.maputility

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_maps.*
import liuuu.laurence.maputility.api.RetrofitClient
import liuuu.laurence.maputility.model.google.directions.Directions
import liuuu.laurence.maputility.model.google.nearbySearch.NearbySearch
import liuuu.laurence.maputility.model.others.Route
import liuuu.laurence.maputility.model.others.Spot
import liuuu.laurence.maputility.utility.Constants
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mMapsController: MapsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        mMapsController = MapsController(this, mGoogleMap)
        mMapsController.setCustomMarker()

        zoomToggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mMapsController.animateZoomInCamera()
            } else {
                mMapsController.animateZoomOutCamera()
            }
        }

        placesToggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            progressLayout.visibility = View.VISIBLE

            if (isChecked) {
                val position = mGoogleMap.cameraPosition.target.latitude.toString() + "," + mGoogleMap.cameraPosition.target.longitude.toString()
                val placesCall = RetrofitClient.googleMethods().getNearbySearch(position, Constants.RADIUS_1000, Constants.TYPE_BAR, Constants.GOOGLE_API_KEY)
                placesCall.enqueue(object : Callback<NearbySearch> {
                    override fun onResponse(call: Call<NearbySearch>, response: Response<NearbySearch>) {
                        val nearbySearch = response.body()!!

                        if (nearbySearch.status.equals("OK")) {
                            val spotList = ArrayList<Spot>()

                            for (resultItem in nearbySearch.results!!) {
                                val spot = Spot(resultItem.name, resultItem.geometry.location?.lat, resultItem.geometry.location?.lng)
                                spotList.add(spot)
                            }

                            mMapsController.setMarkersAndZoom(spotList)
                        } else {
                            toast(nearbySearch.status)
                            placesToggleButton.isChecked = false
                        }

                        progressLayout.visibility = View.GONE
                    }

                    override fun onFailure(call: Call<NearbySearch>, t: Throwable) {
                        toast(t.toString())
                        progressLayout.visibility = View.GONE
                        placesToggleButton.isChecked = false
                    }
                })
            } else {
                progressLayout.visibility = View.GONE
                mMapsController.clearMarkers()
            }
        }

        directionsToggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            progressLayout.visibility = View.VISIBLE

            if (isChecked) {
                val directionsCall = RetrofitClient.googleMethods().getDirections(getString(R.string.time_square), getString(R.string.chelsea_market), Constants.GOOGLE_API_KEY)
                directionsCall.enqueue(object : Callback<Directions> {
                    override fun onResponse(call: Call<Directions>, response: Response<Directions>) {
                        val directions = response.body()!!

                        if (directions.status.equals("OK")) {
                            val legs = directions.routes[0].legs[0]
                            val route = Route(getString(R.string.time_square), getString(R.string.chelsea_market), legs.startLocation.lat, legs.startLocation.lng, legs.endLocation.lat, legs.endLocation.lng, directions.routes[0].overviewPolyline.points)

                            mMapsController.setMarkersAndRoute(route)
                        } else {
                            toast(directions.status)
                            directionsToggleButton.isChecked = false
                        }

                        progressLayout.visibility = View.GONE
                    }

                    override fun onFailure(call: Call<Directions>, t: Throwable) {
                        toast(t.toString())
                        progressLayout.visibility = View.GONE
                        directionsToggleButton.isChecked = false
                    }
                })
            } else {
                progressLayout.visibility = View.GONE
                mMapsController.clearMarkersAndRoute()
            }
        }
    }
}
