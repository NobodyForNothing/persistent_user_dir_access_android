import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:persistent_user_dir_access_android/persistent_user_dir_access_android.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  const MethodChannel channel = MethodChannel('persistent_user_dir_access_android');

  test('Propagates directory URI', () async {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        expect(methodCall.method, 'requestDirectoryUri');
        expect(methodCall.arguments, null);
        return 'content://sample/uri';
      },
    );
    final uri = await const PersistentUserDirAccessAndroid().requestDirectoryUri();
    expect(uri, 'content://sample/uri');
  });
  // TODO: errors, writeFile
}
