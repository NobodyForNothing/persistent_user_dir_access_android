package com.derdilla.persistent_user_dir_access_android

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.eq
import kotlin.test.Test
import org.mockito.Mockito

/*
 * Once you have built the plugin's example app, you can run these tests from the command
 * line by running `./gradlew testDebugUnitTest` in the `example/android/` directory, or
 * you can run them directly from IDEs that support JUnit such as Android Studio.
 */

internal class PersistentUserDirAccessAndroidPluginTest {
  @Test
  fun onMethodCall_requestDirectoryUri_reportsNoActivity() {
    val plugin = PersistentUserDirAccessAndroidPlugin()

    val call = MethodCall("requestDirectoryUri", null)
    val mockResult: MethodChannel.Result = Mockito.mock(MethodChannel.Result::class.java)
    plugin.onMethodCall(call, mockResult)

    Mockito.verify(mockResult).error("NoAct", "No active android activity", null)
  }

  @Test
  fun onMethodCall_requestDirectoryUri_returnsCorrectValue() {
    // setup
    val listenerCaptor = ArgumentCaptor.forClass(PluginRegistry.ActivityResultListener::class.java)
    val reqCodeCaptor = ArgumentCaptor.forClass(Int::class.java)

    val mockActivityPlugin = Mockito.mock(ActivityPluginBinding::class.java)
    Mockito.doNothing().`when`(mockActivityPlugin).addActivityResultListener(listenerCaptor.capture())
    val mockActivity = Mockito.mock(Activity::class.java)
    Mockito.doReturn(mockActivity).`when`(mockActivityPlugin).activity
    val mockContentResolver = Mockito.mock(ContentResolver::class.java)
    Mockito.doReturn(mockContentResolver).`when`(mockActivity).contentResolver

    val mockUri = Mockito.mock(Uri::class.java)
    Mockito.doReturn("content://mockUri").`when`(mockUri).toString()
    val mockIntent = Mockito.mock(Intent::class.java)
    Mockito.doReturn(mockUri).`when`(mockIntent).data

    val plugin = PersistentUserDirAccessAndroidPlugin()
    plugin.onAttachedToActivity(mockActivityPlugin)

    val mockResult: MethodChannel.Result = Mockito.mock(MethodChannel.Result::class.java)

    // test
    val call = MethodCall("requestDirectoryUri", null)
    plugin.onMethodCall(call, mockResult)
    Mockito.verify(mockActivityPlugin).activity
    Mockito.verify(mockActivity).startActivityForResult(any(Intent::class.java), reqCodeCaptor.capture())

    listenerCaptor.value.onActivityResult(reqCodeCaptor.value, Activity.RESULT_OK, mockIntent)
    Mockito.verify(mockContentResolver).takePersistableUriPermission(
      any(),
      eq(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION),
    )
    Mockito.verify(mockResult).success("content://mockUri")
  }

  @Test
  fun onMethodCall_requestDirectoryUri_returnsNullOnNotPicket() {
    // setup
    val listenerCaptor = ArgumentCaptor.forClass(PluginRegistry.ActivityResultListener::class.java)
    val reqCodeCaptor = ArgumentCaptor.forClass(Int::class.java)

    val mockActivityPlugin = Mockito.mock(ActivityPluginBinding::class.java)
    Mockito.doNothing().`when`(mockActivityPlugin).addActivityResultListener(listenerCaptor.capture())
    val mockActivity = Mockito.mock(Activity::class.java)
    Mockito.doReturn(mockActivity).`when`(mockActivityPlugin).activity

    val plugin = PersistentUserDirAccessAndroidPlugin()
    plugin.onAttachedToActivity(mockActivityPlugin)

    val mockResult: MethodChannel.Result = Mockito.mock(MethodChannel.Result::class.java)

    // test
    val call = MethodCall("requestDirectoryUri", null)
    plugin.onMethodCall(call, mockResult)
    Mockito.verify(mockActivityPlugin).activity
    Mockito.verify(mockActivity).startActivityForResult(any(Intent::class.java), reqCodeCaptor.capture())

    listenerCaptor.value.onActivityResult(reqCodeCaptor.value, Activity.RESULT_CANCELED, null)
    Mockito.verifyNoMoreInteractions(mockActivity)
    Mockito.verify(mockResult).success(null)
  }

}
