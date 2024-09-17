package com.derdilla.persistent_user_dir_access_android

import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat.startActivityForResult

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

/** PersistentUserDirAccessAndroidPlugin */
class PersistentUserDirAccessAndroidPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /** The MethodChannel that will the communication between Flutter and native Android */
  private lateinit var channel : MethodChannel

  /** The activity from which to start the intend */
  private var activity: ActivityPluginBinding? = null

  /**
   * Upwards counting code for intents
   * ```kt
   * lastReqCode++;
   * val code = lastReqCode
   * ```
   */
  private var lastReqCode: Int = 1;

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "persistent_user_dir_access_android")
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding
  }

  override fun onDetachedFromActivity() {
    activity = null
  }

  override fun onDetachedFromActivityForConfigChanges() = onDetachedFromActivity()
  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) = onAttachedToActivity(binding)

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "requestDirectoryUri") {
      val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
      if (activity != null) {
        lastReqCode++;
        val code = lastReqCode
        activity!!.addActivityResultListener { requestCode, resultCode, data ->
          if (requestCode == code
            && activity != null
            && resultCode == Activity.RESULT_OK && data?.data != null) {
            activity!!.activity.contentResolver.takePersistableUriPermission(
              data.data!!,
              Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            result.success(data.data!!.toString())
          } else {
            result.success(null)
          }
          true
        }
        activity!!.activity.startActivityForResult(intent, code)
      } else {
        result.error("NoAct", "No active android activity", null)
      }
    } else {
      result.notImplemented()
    }
  }
}
