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
package ca.etsmtl.applets.repository.util

import android.content.Context
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
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
 * Original file: https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/CustomTrust.java
 */

internal abstract class CustomTrust(context: Context) {
    val client: OkHttpClient by lazy {
        val trustManager: X509TrustManager
        val sslSocketFactory: SSLSocketFactory
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream(context))
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            sslSocketFactory = sslContext.socketFactory
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }

        OkHttpClient.Builder()
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

    /**
     * Returns an [InputStream] containing one or more certificate files
     */
    protected abstract fun trustedCertificatesInputStream(context: Context): InputStream
}