VerificationCodeView
-------------

正方形验证码输入框

### 示例:
--------
![image](https://github.com/JackTuoTuo/VerificationCodeView/blob/master/demo.png)  ![image](https://github.com/JackTuoTuo/VerificationCodeView/blob/master/GIF.gif)



### 特性
--------
- 支持xml文件中设置输入框个数

- 支持xml文件中设置输入框背景

- 支持xml文件中设置输入框间距

- 支持xml文件中设置输入框大小



### 原理说明
--------
该项目是一个继承于RelativeLayout的组合控价型的自定义View，在布局中文件中使用了一个透明的EditView来接受用户的输入事件，
在布局文件的LinearLayout中动态添加正方形输入框，正方形输入框其实是一个个的TextView。



### 下载安装
--------
Gradle:  
``` xml
implementation 'com.jacktuotuo.customview:verificationcodeview:1.0.0'
```



### 使用方法
--------
#### 1 xml中使用
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
#### 2 支持的自定义属性
|name|说明|format|默认值|
|:--|:--|:--|:--:|
|icv_et_number|输入框的数量|integer|```1```|
|icv_et_width|输入框的宽度|dimension|```42dp```|
|icv_et_divider_drawable|输入框之间的间隔|reference|```默认图片```|
|icv_et_text_color|输入框文字颜色|color|```Color.WHITE```|
|icv_et_text_size|输入框文字大小|dimension|```16sp```|
|icv_et_bg_focus|输入框获取焦点时边框|reference|```默认边框```|
|icv_et_bg_normal|输入框没有焦点时边框|reference|```默认边框```|



### 注意事项
--------
#### 1 在1.0.0 版本中还不支持在Java文件中动态设置输入框个数，该问题将在1.0.1版本中进行优化 。等不及的同学可fork代码自行修改，也可以修改之后再本项目提交。



## TODO
---------
#### 1 支持在Java中动态设置输入框个数
#### 2 支持密码模式


## License
---------
Copyright 2017 JackTuoTuo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressor implied.See the License for the specific language governing permissions and limitations under the License.

