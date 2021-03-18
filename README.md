# 基于LWJGL库的OpenGL 3D游戏原型
一个使用Lightweight Java Game Library开发的OpenGL游戏原型，参考了[ThinMatrix的开发教程](https://www.youtube.com/playlist?list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP)。

这是一个出于练习OpenGL和熟悉图形学知识而开发的、侧重于使用OpenGL实现游戏画面实时渲染的程序，因此几乎没有游戏性方面的实现。目前已经实现的效果和功能有：

## 光照
采用Blinn-Phong光照模型（环境光 + 漫反射 + 镜面反射）

实现了逐像素光照（Phong Shading）

## 环境
基于blend map的多纹理（multitexturing)地面纹理

基于高度图（height map）的地形

昼夜变化的天空盒

## 水面效果
水面的反射与水体折射

水面的菲涅尔效应（Fresnel Effect）。当观察角度趋向于垂直水面时，将更多看到水面反射的景物；当观察角度趋向于平行于水面时，将更多看到水面下的景物

基于DuDv map的水波纹效果

基于法向量贴图（normal map）的水面高光效果

水面软边缘（soft edges）效果（水域边缘到陆地的渐变过渡）

## 玩家移动与镜头控制
按W, S，角色前进后退；按A, D，角色左右旋转。

按住鼠标左键，向左右移动镜头；按住鼠标右键，向上下移动镜头。

## 字体渲染（用于游戏UI）








		

