package com.json.router.flutter_hybrid_test.channel;

import java.util.ArrayList;

import io.flutter.plugin.common.EventChannel;
import io.flutter.view.FlutterView;

//用于数据流的通信，持续通信，收到信息后无法回复此次信息，通常用于Native向Dart的通信。（全双工通信）
public class EventChannelPlugin implements EventChannel.StreamHandler {
    private EventChannel eventChannel;
    private ArrayList<EventChannel.EventSink> eventSinks;

    public void registerView(FlutterView flutterView) {
        eventChannel = new EventChannel(flutterView, "EventChannel");
        eventChannel.setStreamHandler(this);
        eventSinks = new ArrayList<>();
    }

    //发送信息给Dart端
    public void sendEvents(Object msg) {
        for (int i = 0; i < eventSinks.size(); i++) {
            eventSinks.get(i).success(msg);// success为native回调dart端的函数
        }
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {//dart端会注册监听，在native端就是一个EventSink
        eventSinks.add(eventSink);
    }

    @Override
    public void onCancel(Object o) {

    }
}