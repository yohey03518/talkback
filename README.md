# Introduction

This repository contains source code for Google's TalkBack, which is a screen
reader for blind and visually-impaired users of Android. For usage instructions,
see
[TalkBack User Guide](https://support.google.com/accessibility/android/answer/6283677?hl=en).

### How to Build

In a Linux environment (WSL in not recommended, it would take more than one hour to build):

Install Java 8 and Java 11
```
sudo apt-get install openjdk-8-jdk
sudo apt-get install openjdk-11-jdk
```

Download older Android NDK r20b version from [here](https://github.com/android/ndk/wiki/Unsupported-Downloads)

Unzip the file.

Set up the variables in `build.sh`
```
export ANDROID_SDK='/home/erwin/Android/Sdk'
# the path to the downloaded NDK
export ANDROID_NDK='/home/erwin/Android/Sdk/ndk/android-ndk-r20b'
export JAVA_HOME='/usr/lib/jvm/java-8-openjdk-amd64'
```

To build TalkBack, run ./build.sh, which will produce an apk file.

### How to Install

Install the apk onto your Android device in the usual manner using adb.

### How to Run

With the apk now installed on the device, the TalkBack service should now be
present under Settings -> Accessibility, and will be off by default. To turn it
on, toggle the switch preference to the on position.
