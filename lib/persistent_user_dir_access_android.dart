import 'package:flutter/services.dart';

class PersistentUserDirAccessAndroid {
  static const _channel = MethodChannel('persistent_user_dir_access_android');

  /// Request picking an external directory from the system.
  ///
  /// Users are responsible for persisting Returns null when canceled or picking is not possible.
  static Future<String?> requestDirectoryUri() async {
    try {
      final uri = await _channel.invokeMethod<String>('requestDirectoryUri');
      if (uri?.isEmpty ?? true) return null;
      return uri;
    } on PlatformException catch (e) {
      // TODO: logging
      return null;
    }
  }

  static Future<bool> writeFile(String dirUri, String fileName, String mimeType, Uint8List data) async {
    try {
      final res = await _channel.invokeMethod<bool>('writeFile', <String, dynamic>{
        'dir': dirUri,
        'name': fileName,
        'mime': mimeType,
        'data': data,
      });
      return res ?? false;
    } on PlatformException catch (e) {
      // TODO: logging
      return false;
    }
  }
}
