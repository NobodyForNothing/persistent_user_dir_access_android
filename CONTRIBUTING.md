# Contributing
If you notice issues: reports, debugging or patches are highly appreciated. This is a tightly scoped plugin so new features are only likely to get accepted if they are directly related to performing actions in directories provided by [`requestDirectoryUri`](TODO://link). Examples of such features would be directory listing, file reading and deletion. Changes to the API or refactors are not intended. The code is intentionally this simple and if you need another API, please write a wrapping package or fork this package.

All code in this repository should be formated, documented, tested and if applicable showcased in the example app. Since this is a tiny plugin I don't expect many contributions too you don't need to do any of it yourself to contribute but those are highly appreciated. If you are stuck somewhere in your contribution feel free to reach out.

## Setup
1. `git clone https://github.com/derdilla/persistent_user_dir_access_android.git`
2. Flutter [setup](https://docs.flutter.dev/get-started/install)
3. Add your Code and `cd persistent_user_dir_access_android/example && flutter run`

## Testing
- Android (native) code is tested in `android/src/test/kotlin/com/derdilla/persistent_user_dir_access_android/PersistentUserDirAccessAndroidPluginTest.kt`
  - To run them execute `gradle --project-dir ./example/android [--no-daemon] test`
- The wrapping dart-code is tested as well (see `test/persistent_user_dir_access_android_test.dart`)
  - `flutter test`
- Same for the example app:
  - `cd example && flutter test`
 
All of those will be run automatically performed by the CI.
