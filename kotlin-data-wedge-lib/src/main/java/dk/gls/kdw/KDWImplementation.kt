package dk.gls.kdw

import android.content.Context
import android.content.Intent
import dk.gls.kdw.configuration.ProfileConfiguration
import dk.gls.kdw.configuration.scanner.DataWedgeHardwareScanner
import dk.gls.kdw.configuration.scanner.ParityFlowScannerController
import dk.gls.kdw.configuration.scanner.ScannerController
import dk.gls.kdw.configuration.toBundle

open class KDWImplementation : IKDW {

    companion object {
        private const val EXTRA_SET_CONFIG = "com.symbol.datawedge.api.SET_CONFIG"
        private const val ACTION_DATA_WEDGE = "com.symbol.datawedge.api.ACTION"
    }

    override fun configure(
        context: Context,
        profileConfiguration: ProfileConfiguration,
        scannerController: ScannerController?
    ) {
        //Create and broadcast intent configuring scanner
        val dataWedgeIntent = Intent()
        dataWedgeIntent.action = ACTION_DATA_WEDGE
        dataWedgeIntent.putExtra(EXTRA_SET_CONFIG, profileConfiguration.toBundle())
        context.sendBroadcast(dataWedgeIntent)

        this._scannerController = scannerController ?: ParityFlowScannerController(
            DataWedgeHardwareScanner(context)
        )
    }

    private var _scannerController: ScannerController? = null

    override val scannerController: ScannerController
        @Throws(RuntimeException::class)
        get() = _scannerController
            ?: throw RuntimeException("Accessing KDW.scannerController before calling KDW.configure(...) is not allowed!")
}

object KDW : KDWImplementation()