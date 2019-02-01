package ca.etsmtl.applets.etsmobile.presentation.security

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.etsmtl.applets.etsmobile.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_security.*


/**
 * This fragment contains information about the security.
 */
class SecurityFragment : Fragment(), OnMapReadyCallback {

    private var mMap: MapView? = null

    companion object {
        private val etsLocation = LatLng(45.49449875, -73.56246144109338)
        private val securityBuildingALocation = LatLng(45.49511855948888, -73.56270170940309)
        private val securityBuildingBLocation = LatLng(45.495089693692194, -73.56374294991838)
        private val securityBuildingELocation = LatLng(45.49391646843658, -73.5634878349083)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap(savedInstanceState)
        setupRecyclerView()
        setUpViewListener()
        ViewCompat.setNestedScrollingEnabled(nestedScrollView, false)
    }

    private fun setUpMap(savedInstanceState: Bundle?) {
        mMap = mapView
        mMap?.onCreate(savedInstanceState)
        mMap?.getMapAsync(this)
    }

    private fun setUpViewListener() {
        viewCall.setOnClickListener {
            val uri = "tel:" + resources.getString(R.string.emergency_number)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val itemsList = resources.getStringArray(R.array.security_type)
        security_recycler_view.layoutManager = LinearLayoutManager(context)
        security_recycler_view.adapter = SecurityAdapter(itemsList, findNavController())
        security_recycler_view.setHasFixedSize(true)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(etsLocation, 17F))
        setMapMarker(googleMap)
    }

    private fun setMapMarker(googleMap: GoogleMap) {
        googleMap.addMarker(MarkerOptions().position(securityBuildingALocation).title(resources.getString(R.string.security_station)))
        googleMap.addMarker(MarkerOptions().position(securityBuildingBLocation).title(resources.getString(R.string.security_station)))
        googleMap.addMarker(MarkerOptions().position(securityBuildingELocation).title(resources.getString(R.string.security_station)))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMap?.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMap?.onPause()
    }

    override fun onStart() {
        super.onStart()
        mMap?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMap?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMap?.onDestroy()
        mMap = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMap?.onLowMemory()
    }

}
