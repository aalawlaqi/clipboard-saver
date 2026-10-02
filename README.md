# Clipboard Saver Phase 1

Two independent local components. Android IME records clipboard text locally; PWA keeps its own local history and is Sliplane-ready. No backend, database, account, or cloud sync.

## Android
Open `android/` in Android Studio. Build/install the APK, enable Clipboard Saver under Android keyboard/input settings, and select it. The IME appends timestamped text to its private `clipboard.txt`. Use Export to save/share the file.

Android privacy rules limit clipboard access. This implementation uses an InputMethodService rather than Accessibility tricks.

## PWA
`pwa/` is an installable static PWA. It uses IndexedDB locally, has Paste Clipboard, search, copy, delete, clear, and TXT export. It cannot silently monitor clipboard changes from other Android apps; the user invokes Paste Clipboard.

For Sliplane, deploy `pwa/` as the service root. The included Dockerfile exposes port 80 through Nginx.
