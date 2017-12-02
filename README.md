


**更多请移步至**：[我的CSDN博客](http://blog.csdn.net/qq_33553515/article/details/73344155)  QQ:839539179   WEIXIN:tuojie003　　　　


VerificationCodeView
-------------

正方形验证码输入框

### 示例:
--------
![动图演示](https://github.com/JackTuoTuo/VerificationCodeView/blob/master/GIF.gif)

apk演示：[点击下载](https://github.com/JackTuoTuo/VerificationCodeView/blob/master/app-debug.apk?raw=true) 

### 特性
--------
#### 自定义属性
|name|说明|format|默认值|
|:--|:--|:--|:--:|
|icv_et_number|输入框的数量|integer|```1```|
|icv_et_width|输入框的宽度|dimension|```42dp```|
|icv_et_divider_drawable|输入框之间的间隔|reference|```默认图片```|
|icv_et_text_color|输入框文字颜色|color|```Color.WHITE```|
|icv_et_text_size|输入框文字大小|dimension|```16sp```|
|icv_et_bg_focus|输入框获取焦点时边框|reference|```默认边框```|
|icv_et_bg_normal|输入框没有焦点时边框|reference|```默认边框```|

#### 可使用方法
|method_name|description|return_type|
|:--|:--|:--|
|getInputContent|获取输入内容|String|
|clearInputContent|清空输入内容|Void|
|setEtNumber(int etNumber)|设置输入框个数|Void|
|getEtNumber|获取输入框个数|int|
|setInputCompleteListener(InputCompleteListener istener) |设置输入和删除时的监听|Void|







### 原理说明
--------
该项目是一个继承于RelativeLayout的组合控价型的自定义View，在布局中文件中使用了一个透明的EditView来接受用户的输入事件，
在布局文件的LinearLayout中动态添加正方形输入框，正方形输入框其实是一个个的TextView。


### 使用方法
--------

#### 1 Gradle引用
``` xml
implementation 'com.jacktuotuo.customview:verificationcodeview:1.0.0'
```

#### 2 xml中使用
- 简单配置

``` xml
<com.tuo.customview.VerificationCodeView
        android:id="@+id/icv_1"
        app:icv_et_number="5"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
- 个性化配置
``` xml
<com.tuo.customview.VerificationCodeView
        android:id="@+id/icv"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_centerHorizontal="true"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="26dp"
        app:icv_et_bg_focus="@drawable/shape_icv_et_bg_focus"
        app:icv_et_bg_normal="@drawable/shape_icv_et_bg_normal"
        app:icv_et_divider_drawable="@drawable/shape_divider_identifying"
        app:icv_et_number="6"
        app:icv_et_text_color="#000000"
        app:icv_et_width="50dp" />
```

#### 3 java代码中使用
``` xml
VerificationCodeView codeView = new VerificationCodeView(context);
codeView.setEtNumber(number);
```

### Update Log
--------
#### version 1.0.1
- 支持Java代码中动态设置输入框个数
- 支持xml中只配置宽高，输入框个数默认为1



### TODO
---------
#### version-1.0.1
 - 支持在Java中动态设置输入框个数
#### version-1.0.2
 - 支持密码模式


### License
---------
Copyright 2017 JackTuoTuo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressor implied.See the License for the specific language governing permissions and limitations under the License.

