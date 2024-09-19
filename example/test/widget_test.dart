import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:persistent_user_dir_access_android_example/main.dart';

void main() {
  testWidgets('Verify Platform version', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(const App());

    // TODO: test
  });
}
