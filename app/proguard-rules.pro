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

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

# rxjava
-dontwarn java.lang.invoke.*
-dontwarn javax.annotation.**

# support
-keep class android.support.v7.widget.** { *; }
-keep interface android.support.v7.widget.** { *; }
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.design.widget.** { *; }

# server classes
-keepclasseswithmembers class com.roix.mapchat.data.repositories.firebase.models.** {
    *;
}

-keepclasseswithmembers class .com.roix.mapchat.data.repositoriesroom.models.** {
    <fields>;
}

-keepclasseswithmembers class com.roix.mapchat.data.models** {
    <fields>;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-adaptclassstrings

-dontwarn toothpick.**


# Do not obfuscate annotation scoped classes
-keepnames @javax.inject.Singleton class *

-keepnames @com.roix.mapchat.toothpick.common.ApplicationScope class *

# Add any custom defined @Scope (e.g. @Singleton) annotations here
# because proguard does not allow annotation inheritance rules

# Do not obfuscate classes with Injected Constructors
-keepclasseswithmembernames class * {
    @javax.inject.Inject <init>(...);
}

# Do not obfuscate classes with Injected Fields
-keepclasseswithmembernames class * {
    @javax.inject.Inject <fields>;
}

# Do not obfuscate classes with Injected Methods
-keepclasseswithmembernames class * {
    @javax.inject.Inject <methods>;
}


-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**

# kotlin
-dontwarn kotlin.**
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

# The Maps API uses custom Parcelables.
# Use this rule (which is slightly broader than the standard recommended one)
# to avoid obfuscating them.

-keepclassmembers class * implements android.os.Parcelable {
    static *** CREATOR;
}

# The Maps Android API uses serialization.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }

-dontskipnonpubliclibraryclasses

# JodaTime

-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }