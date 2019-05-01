package com.json.router.flutter_hybrid_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.json.router.flutter_hybrid_test.channel.BasicMessageChannelPlugin;
import com.json.router.flutter_hybrid_test.channel.EventChannelPlugin;
import com.json.router.flutter_hybrid_test.channel.MethodChannelPlugin;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class MainActivity extends AppCompatActivity implements IShowMessage {

    private FrameLayout flContainer;
    private TextView tvMessageFromDart;

    private BasicMessageChannelPlugin basicMessageChannelPlugin;
    private MethodChannelPlugin methodChannelPlugin;
    private EventChannelPlugin eventChannelPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tvMessageFromDart = findViewById(R.id.tv_message_from_dart);
        flContainer = findViewById(R.id.fl_container);
        RadioGroup rgNative = findViewById(R.id.rg_native);
        rgNative.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_basic:
                        basicMessageChannelPlugin.sendMessageToDart("Native端发送：basicMessageChannel msg");
                        break;
                    case R.id.rb_method:
                        methodChannelPlugin.invokeDartMethod("responseToNative", "", new MethodChannel.Result() {
                            @Override
                            public void success(@Nullable Object o) {
                                tvMessageFromDart.setText((CharSequence) o);
                            }

                            @Override
                            public void error(String s, @Nullable String s1, @Nullable Object o) {

                            }

                            @Override
                            public void notImplemented() {

                            }
                        });
                        break;
                    case R.id.rb_event:
                        eventChannelPlugin.sendEvents("native端发送：eventChannel msg");
                        break;
                }
            }
        });
        basicMessageChannelPlugin = new BasicMessageChannelPlugin();
        // 实现方式1
        FlutterView flutterView = Flutter.createView(this, getLifecycle(), "");
        flContainer.addView(flutterView);
        basicMessageChannelPlugin.registerView(flutterView, this);

        // 实现方式2
//        FlutterFragment flutterFragment = Flutter.createFragment("");
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, flutterFragment).commit();
//        basicMessageChannelPlugin.registerView((FlutterView) flutterFragment.getView(), this);

        methodChannelPlugin = new MethodChannelPlugin();
        methodChannelPlugin.registerView(flutterView, this);
        eventChannelPlugin = new EventChannelPlugin();
        eventChannelPlugin.registerView(flutterView);
    }


    @Override
    public void showMessage(String msg) {
        tvMessageFromDart.setText(msg);
    }
}
