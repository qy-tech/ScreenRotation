# Screen Rotation Demo Code

## Framework

Since IWindowManager is a hide api, we can only use reflection or add framework.jar to call it, we directly add framework.jar to the project

## System Signed

Since the freezeRotation requires SET_ORIENTATION permission , we add permission and used system signature

You should add the following code for system signature

```
android:sharedUserId="android.uid.system"
```

## Demo apk

We have added the compiled [app](https://github.com/qy-tech/ScreenRotation/releases)
, you can use it directly



