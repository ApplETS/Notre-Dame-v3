/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.etsmtl.repos.util

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okio.Buffer
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.Arrays
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * This class is used to trust specific certificates. The certificate used by Signets is issued by
 * Comodo RSA CA. However, on older Android APIs, the certificate is missing from the "Trusted
 * credentials" list (Settings > Security > Trusted Credentials). Therefore, this classes is used
 * for allowing the app to communicate with Signets on older APIs.
 */
class SignetsTrust {
    val client: OkHttpClient

    init {
        val trustManager: X509TrustManager
        val sslSocketFactory: SSLSocketFactory
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream())
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            sslSocketFactory = sslContext.socketFactory
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }

        client = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build()
    }

    /**
     * Returns a trust manager that trusts `certificates` and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a `SSLHandshakeException`.
     *
     *
     * This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     *
     * See also [CertificatePinner], which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     *
     * Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(`in`: InputStream): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificates = certificateFactory.generateCertificates(`in`)
        if (certificates.isEmpty()) {
            throw IllegalArgumentException("expected non-empty set of trusted certificates")
        }

        // Put the certificates a key store.
        val password = "password".toCharArray() // Any password will work.
        val keyStore = newEmptyKeyStore(password)
        for ((index, certificate) in certificates.withIndex()) {
            val certificateAlias = Integer.toString(index)
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }

        // Use it to build an X509 trust manager.
        val keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        return trustManagers[0] as X509TrustManager
    }

    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val `in`: InputStream? = null // By convention, 'null' creates an empty key store.
            keyStore.load(`in`, password)
            return keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    companion object {

        /**
         * Returns an input stream containing one or more certificate PEM files. This implementation just
         * embeds the PEM files in Java strings; most applications will instead read this from a resource
         * file that gets bundled with the application.
         */
        private fun trustedCertificatesInputStream(): InputStream {
            val comodoRsaCertificationAuthority = ("" +
                    "-----BEGIN CERTIFICATE-----\n" +
                    "MIIFRzCCBC+gAwIBAgIQEisXalGtHwIXQta+Ta6mAzANBgkqhkiG9w0BAQsFADCB\n" +
                    "kDELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
                    "A1UEBxMHU2FsZm9yZDEaMBgGA1UEChMRQ09NT0RPIENBIExpbWl0ZWQxNjA0BgNV\n" +
                    "BAMTLUNPTU9ETyBSU0EgRG9tYWluIFZhbGlkYXRpb24gU2VjdXJlIFNlcnZlciBD\n" +
                    "QTAeFw0xNzExMDcwMDAwMDBaFw0yMTAxMTYyMzU5NTlaMFcxITAfBgNVBAsTGERv\n" +
                    "bWFpbiBDb250cm9sIFZhbGlkYXRlZDEcMBoGA1UECxMTQ09NT0RPIFNTTCBXaWxk\n" +
                    "Y2FyZDEUMBIGA1UEAwwLKi5ldHNtdGwuY2EwggEiMA0GCSqGSIb3DQEBAQUAA4IB\n" +
                    "DwAwggEKAoIBAQC4XwmsnApIdHleFxh2WQNBWy9l6FgW1zcslmfpFJm9lBFGI0Lx\n" +
                    "sOjoHVu06wC5CQgWadtEkm6VI2ebDujQrpPEW0V6tcxJ0AfDSAS8Uin6cNUAODC0\n" +
                    "l3O4Wb3mvN5Aej6I6tTqlfeaXVXmaKdFbMf6dbWK+PkIg/FnoPDY14PJcvXN6+iV\n" +
                    "XyISWoXdek4ee5pPqyKj2HTQp7RLxeX/Ve3ZTJqYhIc/GDrFOdPNy402oBkcd1nh\n" +
                    "Gju8P7BF1eVt/jrPKzM0Smk/sPHqEr3QBLA3iN7pPaY0SDlnmwbMxsT0UBk3j7V6\n" +
                    "n5thhjnAOUPi9K22P2YWxPLd7kYpFq2OQEw5AgMBAAGjggHTMIIBzzAfBgNVHSME\n" +
                    "GDAWgBSQr2o6lFoL2JDqElZz30O0Oija5zAdBgNVHQ4EFgQUvgstff+NmDbIlgT/\n" +
                    "HmZmV9Vo3JwwDgYDVR0PAQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYw\n" +
                    "FAYIKwYBBQUHAwEGCCsGAQUFBwMCME8GA1UdIARIMEYwOgYLKwYBBAGyMQECAgcw\n" +
                    "KzApBggrBgEFBQcCARYdaHR0cHM6Ly9zZWN1cmUuY29tb2RvLmNvbS9DUFMwCAYG\n" +
                    "Z4EMAQIBMFQGA1UdHwRNMEswSaBHoEWGQ2h0dHA6Ly9jcmwuY29tb2RvY2EuY29t\n" +
                    "L0NPTU9ET1JTQURvbWFpblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcmwwgYUG\n" +
                    "CCsGAQUFBwEBBHkwdzBPBggrBgEFBQcwAoZDaHR0cDovL2NydC5jb21vZG9jYS5j\n" +
                    "b20vQ09NT0RPUlNBRG9tYWluVmFsaWRhdGlvblNlY3VyZVNlcnZlckNBLmNydDAk\n" +
                    "BggrBgEFBQcwAYYYaHR0cDovL29jc3AuY29tb2RvY2EuY29tMCEGA1UdEQQaMBiC\n" +
                    "CyouZXRzbXRsLmNhgglldHNtdGwuY2EwDQYJKoZIhvcNAQELBQADggEBAA0+MZBG\n" +
                    "kGJvjohYnvqG/e/rdgqqIQxxMeWNKb6xIFXM+OXXnUnnwOWZSgbnfY/0KABq1E3g\n" +
                    "46HDobU19pqo/FStqjBMJJFv9PijNm4naOtHmnFPId5S3vkVMPyDlOHGvUKnzpXS\n" +
                    "RJHjYpgS4G1a1cWISi6LneT6Cvc2B08zSlwCz1z76NuLbRkM+/ZGA2ym6JoXrSRb\n" +
                    "vRdQaCcBEJvap8irsjDnG30ShRtBMR1ksPgqacd+hdPof/ws6jpNl7JOs1ldasKt\n" +
                    "i6o75gQXbbfCG9lcMXz0w2S0+/Wpf0XIYfV05H6/tQ78LTVkXlpdZzIfjGJhEjxE\n" +
                    "iE+LXE3FUVeJdC0=\n" +
                    "-----END CERTIFICATE-----\n")
            return Buffer()
                    .writeUtf8(comodoRsaCertificationAuthority)
                    .inputStream()
        }
    }
}
