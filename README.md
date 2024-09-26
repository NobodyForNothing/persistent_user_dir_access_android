# persistent_user_dir_access_android

A lightweight, 0-dependency (except flutter) package to let android-users choose
a directory for writing data between app restarts. All code is fully tested and 
documented.

## Getting Started

Using this package is very straight forward, let me demonstrate:
```dart
Future<void> saveText(String text) async {
  // Requiring a class instance allows for easy debugging.
  final userDirs = userDirs = const PersistentUserDirAccessAndroid();
  
  // This shows the OS dir-picker to the users.
  final String selectedDirUri = await userDirs.requestDirectoryUri();
  
  // You can always save a dir-URI for later use, even after app restarts.
  this.selectedUri = selectedDirUri;
  
  // Pass your URI to the `writeFile` method to create a new file:
  await userDirs.writeFile(
    selectedDirUri,
    'test.txt', // filename passed to android. The OS automatically avoids overriding files
    'text/plain', // specify mime-type
    utf8.encode(text), // Pass binary data
  );
}

```

For more usage info, check out the `example` directory.

## Contributing
PRs and issues are welcome. Check out [CONTRIBUTING.md](https://github.com/derdilla/persistent_user_dir_access_android/blob/main/CONTRIBUTING.md) for more info.
