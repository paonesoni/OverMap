# OverMapUI
This library draw route with caps ans shadows


[![Watch the video](https://github.com/paonesoni/OverMapUI/blob/master/video/video.gif)]


Gradle
------
```
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin'
}

dependencies {
    ...
    implementation 'com.github.paonesoni:OverMap:v1.0'

}
```

Project Gradle
--------------
```
repositories {
    google()
    mavenCentral()
         maven { url 'https://jitpack.io' }
}

```

```xml

 <com.paonesoni.overmap.view.OverMap
        android:id="@+id/customizeMap"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:pathColor="@color/purple_200"
        app:pathShadowColor="@color/black"
        app:capStartColor="@color/black"
        app:capEndColor="@color/black"
        app:pathWidth="5dp"
        app:capSize="10dp"
        app:pathShadowWidth="10dp"
        />

```


Quickstart
----------
```kotlin

     private var points = ArrayList<PathData>()
     private lateinit var customizeMap : OverMap
     
```

Inside the onCreate
```kotlin

 override fun onCreate(savedInstanceState: Bundle?) {
     ...
     customizeMap = findViewById(R.id.customizeMap)
 }
 
```

Setup Style
```kotlin

        val styleOne = PathStyle(pathColor = Color.YELLOW, pathShadowColor = Color.GRAY,
            capStartColor = Color.RED, capEndColor = Color.RED, pathWidth = 10f, 
            pathShadowWidth = 10f, capSize = 20f)

        val styleTwo = PathStyle(pathColor = ContextCompat.getColor(this,R.color.blue),
            pathShadowColor = ContextCompat.getColor(this,R.color.blue_light), 
            capStartColor = ContextCompat.getColor(this,R.color.black),
            capEndColor = ContextCompat.getColor(this,R.color.black),
            pathWidth = 5f, pathShadowWidth = 15f, capSize = 15f)

```



For single Archview
```kotlin

override fun onMapReady(googleMap: GoogleMap) {
        ...
        mMap.setOnCameraMoveListener {
            customizeMap.arch(PathData(LatLng(19.007606, 72.817380), LatLng(19.069756, 72.875916)), mMap)
        }

    }

```


For multiple Archview
```kotlin

fun setUp() {

        val styleOne = PathStyle(pathColor = Color.YELLOW, pathShadowColor = Color.GRAY,
            capStartColor = Color.RED, capEndColor = Color.RED, pathWidth = 10f, 
            pathShadowWidth = 10f, capSize = 20f)

        val styleTwo = PathStyle(pathColor = ContextCompat.getColor(this,R.color.blue),
            pathShadowColor = ContextCompat.getColor(this,R.color.blue_light), 
            capStartColor = ContextCompat.getColor(this,R.color.black),
            capEndColor = ContextCompat.getColor(this,R.color.black),
            pathWidth = 5f, pathShadowWidth = 15f, capSize = 15f)


        points.add(PathData(LatLng(19.007606, 72.817380), LatLng(18.994460, 72.845875)))
        points.add(PathData(LatLng(19.041524, 72.865273), LatLng(19.035682, 72.930333), styleOne ))
        points.add(PathData(LatLng(19.085006, 72.848965), LatLng(19.058399, 72.874199), styleTwo))


}

override fun onMapReady(googleMap: GoogleMap) {
        ...
        mMap.setOnCameraMoveListener {
           customizeMap.arch(points, googleMap)
        }

    }

```




License
--------

    Copyright 2022 paonesoni

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
