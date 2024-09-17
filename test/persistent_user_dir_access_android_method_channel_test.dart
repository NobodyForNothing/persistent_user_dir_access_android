import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:persistent_user_dir_access_android/persistent_user_dir_access_android_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelPersistentUserDirAccessAndroid platform = MethodChannelPersistentUserDirAccessAndroid();
  const MethodChannel channel = MethodChannel('persistent_user_dir_access_android');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
