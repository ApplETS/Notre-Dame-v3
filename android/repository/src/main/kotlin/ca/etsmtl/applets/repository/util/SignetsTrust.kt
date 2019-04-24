package ca.etsmtl.applets.repository.util

import android.content.Context
import ca.etsmtl.applets.repository.R
import java.io.InputStream

/**
 * This class is used to trust specific certificates. The certificate used by Signets is issued by
 * Comodo RSA CA. However, on older Android APIs, the certificate is missing from the "Trusted
 * credentials" list (Settings > Security > Trusted Credentials). Therefore, this classes is used
 * for allowing the app to communicate with Signets on older APIs.
 *
 * Created by Sonphil on 24-04-19.
 */
internal class SignetsTrust(context: Context) : CustomTrust(context) {
    override fun trustedCertificatesInputStream(context: Context): InputStream {
        val comodoRsaCertificationAuthority = context
            .resources
            .openRawResource(R.raw.signets_cert)

        return comodoRsaCertificationAuthority
    }
}
