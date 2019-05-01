<------ Flutter与Native混合式开发Demo ------>
三种全双工通信实例
 -  BasicMessageChannel
 -  MethodChannel
 -  EventChannel
 ------------------------------------------------------------------------
Flutter混合式开发步骤：
1、使用如下命令创建flutter module:
flutter create -t module flutterModuleName
最后的flutterModuleName是我们创建的flutter module的名字
2、配置Android项目(已存在的或新建的原生Android项目都可以)的setting.gradle
setBinding(new Binding([gradle: this]))                                 // new
evaluate(new File(                                                      // new
        settingsDir.parentFile,                                                // new
        'flutterModuleName/.android/include_flutter.groovy'// 注意：flutterModuleName是和我们的原生Android项目是平级的。否则，需要更改路径
))
3、配置Android项目的build.gradle(app)的java 编译选项为1.8、同时添加flutter模块的依赖
  3.1、java 编译选项
  compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
  3.2、添加flutter模块的依赖
  implementation project(':flutter')
  flutter模块是什么？完成上述配置之后，重新build当前项目，会在Android原生项目下生成一个flutter模块。