package ca.etsmtl.applets.etsmobile.presentation.security

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.setVisible
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.appBarLayout
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.fragment_security.mapView
import kotlinx.android.synthetic.main.fragment_security.rvSecurity
import kotlinx.android.synthetic.main.fragment_security.viewCallEmergency

/**
 * This fragment contains information about the security.
 */
class SecurityFragment : Fragment(), OnMapReadyCallback {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialActivityState()
        setMap(savedInstanceState)
        setRecyclerView()
        setViewListener()
    }

    private fun setMap(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun setViewListener() {
        viewCallEmergency.setOnClickListener {
            val uri = "tel:" + resources.getString(R.string.emergency_number)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

    private fun setRecyclerView() {
        val itemsList = resources.getStringArray(R.array.array_security_type)
        rvSecurity.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = SecurityAdapter(itemsList, findNavController())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val etsLocation = LatLng(45.49449875, -73.56246144109338)
        googleMap.let {
            it.uiSettings.isMapToolbarEnabled = false
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(etsLocation, 17F))
            setMapMarker(it)
        }
    }

    private fun setMapMarker(googleMap: GoogleMap) {
        val securityBuildingALocation = LatLng(45.49511855948888, -73.56270170940309)
        val securityBuildingBLocation = LatLng(45.495089693692194, -73.56374294991838)
        val securityBuildingELocation = LatLng(45.49391646843658, -73.5634878349083)
        googleMap.addMarker(
            MarkerOptions().position(securityBuildingALocation).title(
                resources.getString(
                    R.string.security_station
                )
            )
        )
        googleMap.addMarker(
            MarkerOptions().position(securityBuildingBLocation).title(
                resources.getString(
                    R.string.security_station
                )
            )
        )
        googleMap.addMarker(
            MarkerOptions().position(securityBuildingELocation).title(
                resources.getString(
                    R.string.security_station
                )
            )
        )
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        mapView?.onDestroy()
        super.onDestroyView()
    }

    override fun onDestroy() {
        restoreActivityState()

        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun setInitialActivityState() = (activity as? MainActivity)?.apply {
        bottomNavigationView?.setVisible(false)
        appBarLayout?.setExpanded(true, true)
    }

    private fun restoreActivityState() {
        (activity as? MainActivity)?.apply {
            bottomNavigationView?.setVisible(true)
            appBarLayout?.setExpanded(true, true)
        }
    }
}
