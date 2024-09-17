import 'package:flutter/material.dart';

import 'package:persistent_user_dir_access_android/persistent_user_dir_access_android.dart';

void main() {
  runApp(MaterialApp(
    home: Scaffold(
      appBar: AppBar(
        title: const Text('User di sample app'),
      ),
      body: ListTile(
        title: const Text('requestDirectoryUri()'),
        onTap: () async {
          print(await PersistentUserDirAccessAndroid.requestDirectoryUri());
        },
      )
    ),
  ));
}


