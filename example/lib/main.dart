import 'dart:convert';

import 'package:flutter/material.dart';

import 'package:persistent_user_dir_access_android/persistent_user_dir_access_android.dart';

void main() => runApp(const MaterialApp(home: App()));

class App extends StatefulWidget {
  const App({super.key});

  @override
  State<App> createState() => _AppState();
}

class _AppState extends State<App> {
  String? _uri;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User di sample app'),
      ),
      body: ListView(
        children: [
          Card(child: Text('Selected directory: $_uri')),
          ListTile(
            title: const Text('requestDirectoryUri()'),
            onTap: () async {
              final dirUri = await PersistentUserDirAccessAndroid.requestDirectoryUri();
              setState(() {
                _uri = dirUri;
              });
            },
          ),
          ListTile(
            title: const Text("writeFile(uri, 'test.txt', 'text/plain', utf8.encode('Test text'))"),
            onTap: _uri == null ? null : () async {
              await PersistentUserDirAccessAndroid.writeFile(_uri!, 'test.txt', 'text/plain', utf8.encode('Test text'));
            },
          )
        ],
      )
    );
  }
}



