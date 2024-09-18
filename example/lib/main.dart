import 'dart:convert';

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
          var x = await PersistentUserDirAccessAndroid.requestDirectoryUri();
          print(x);
          await PersistentUserDirAccessAndroid.writeFile(x!, 'tst', '*', utf8.encode('Test text'));
        },
      )
    ),
  ));
}


