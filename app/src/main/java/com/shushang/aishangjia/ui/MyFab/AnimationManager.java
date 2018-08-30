package com.shushang.aishangjia.ui.MyFab;

import android.support.design.widget.FloatingActionButton;

/**
 * Created by YD on 2018/8/3.
 */

public abstract class AnimationManager {

    public SuspensionFab fabView;

    public AnimationManager(SuspensionFab fabView) {
        this.fabView = fabView;
    }

    /**
     * 给按钮添加自定义动画
     * 展开动画
     *
     * @param fab         按钮
     * @param orientation 展开的方向
     */
    public abstract void openAnimation(FloatingActionButton fab, ExpandOrientation orientation);

    /**
     * 给按钮添加自定义动画
     * 折叠动画
     *
     * @param fab         按钮
     * @param orientation 展开的方向
     */
    public abstract void closeAnimation(FloatingActionButton fab, ExpandOrientation orientation);

    /**
     * 给默认按钮添加自定义动画
     *
     * @param fab          按钮
     * @param orientation  展开的方向
     * @param currentState true 为展开状态|false为折叠状态
     */
    public abstract void defaultFabAnimation(FloatingActionButton fab, ExpandOrientation orientation,
                                             boolean currentState);

}

