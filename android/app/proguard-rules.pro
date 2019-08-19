# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# For stack traces
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keep public class * extends java.lang.Exception

# Dagger
-dontwarn com.google.errorprone.annotations.*

-dontwarn org.jetbrains.annotations.**

# Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-printmapping mapping.txt

# Firebase
-keep class com.google.firebase.provider.** { *; }

-keep class android.arch.lifecycle.ProcessLifecycleOwnerInitializer

-dontwarn retrofit2.Platform$Java8

# Okio
-dontwarn okio.**

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class ca.etsmtl.applets.**$$serializer { *; }
-keepclassmembers class ca.etsmtl.applets.** {
    *** Companion;
}
-keepclasseswithmembers class ca.etsmtl.applets.** {
    kotlinx.serialization.KSerializer serializer(...);
}
