相信大家都看像搜索附近、周边人或事物的雷达扩散效果吧，那这个效果是这样实现的呢，怎样以一种高效的方式来实现呢？好了，先看看效果吧
![image](https://github.com/qtqt/raderdemo/tree/master/images/image.gif)

## 使用：
在布局中增加如下代码：
 ```
 <com.qt.raderdemo.RadarView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/radarView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:radar_view_space="150"
        app:radar_view_color="#FF89F2C7"
        app:radar_view_speed="10"
        app:radar_view_is_alpha="true"
        app:radar_view_center_image="@mipmap/ic_search_bluetooth"
        />
 ```
