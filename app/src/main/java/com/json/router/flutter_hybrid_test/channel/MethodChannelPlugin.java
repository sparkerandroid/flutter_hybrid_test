package com.json.router.flutter_hybrid_test.channel;

import com.json.router.flutter_hybrid_test.IShowMessage;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

//用于传递方法调用一次性通信。如Flutter调用Native拍照。(全双工通信)
public class MethodChannelPlugin implements MethodChannel.MethodCallHandler {
    private IShowMessage showMsg;
    private MethodChannel methodChannel;

    public void registerView(FlutterView flutterView, IShowMessage showMsg) {
        this.showMsg = showMsg;
        methodChannel = new MethodChannel(flutterView, "MethodChannel");
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals("getMsgFromNativeByMethodChannel")) {
            result.success(getMsgFromNativeByMethodChannel());
        } else {
            result.notImplemented();
        }
    }

    public void invokeDartMethod(String methodName, String param, MethodChannel.Result callBack) {
        methodChannel.invokeMethod(methodName, param, callBack);
    }

    private String getMsgFromNativeByMethodChannel() {
        return "Android端发来的消息：copy that";
    }
}
