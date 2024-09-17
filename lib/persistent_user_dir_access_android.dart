import 'package:flutter/services.dart';

class PersistentUserDirAccessAndroid {
  static const _channel = MethodChannel('persistent_user_dir_access_android');

  /// Request picking an external directory from the system.
  ///
  /// Returns null when canceled or picking is not possible.
  static Future<String?> requestDirectoryUri() async {
    final uri = await _channel.invokeMethod<String>('requestDirectoryUri');
    if (uri?.isEmpty ?? true) return null;
    return uri;
  }
}
