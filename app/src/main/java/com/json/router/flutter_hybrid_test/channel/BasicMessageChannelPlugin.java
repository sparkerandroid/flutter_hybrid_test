package com.json.router.flutter_hybrid_test.channel;

import com.json.router.flutter_hybrid_test.Constant;
import com.json.router.flutter_hybrid_test.IShowMessage;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;
import io.flutter.view.FlutterView;

//用于传递字符串和半结构化的信息，持续通信，收到信息后可以回复此次信息。（全双工通信）
public class BasicMessageChannelPlugin implements BasicMessageChannel.MessageHandler<String>, BasicMessageChannel.Reply<String> {

    private BasicMessageChannel<String> basicMessageChannel;
    private IShowMessage showMsg;

    public void registerView(FlutterView flutterView, IShowMessage showMsg) {
        basicMessageChannel = new BasicMessageChannel<String>(flutterView, Constant.BASIC_MESSAGE_CHANNEL, StringCodec.INSTANCE);
        basicMessageChannel.setMessageHandler(this);
        this.showMsg = showMsg;
    }

    // 收到Dart端的消息及回复Dart端
    @Override
    public void onMessage(String s, BasicMessageChannel.Reply<String> reply) {
        showMsg.showMessage(s);
    }

    public void sendMessageToDart(String msg) {
        basicMessageChannel.send(msg, this);
    }

    // 来自Dart端的应答
    @Override
    public void reply(String s) {
        showMsg.showMessage(s);
    }
}
