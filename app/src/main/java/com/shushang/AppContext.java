package com.shushang;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class AppContext {
	private static AppContext 	instance;
	private Context mContext;
	private int					mScreenWidth, mScreenHeight;// 屏幕尺寸


	private AppContext() {}

	public static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}
	
	public void init(Context context) {
		mContext = context;
	}

	/**
	 * 获取上下文环境
	 * @return
	 */
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public int getDisplayWidth() {
		if (mScreenWidth <= 0) {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			mScreenWidth = dm.widthPixels;
		}
		return mScreenWidth;
	}

	/**
	 * 获取屏幕高度
	 * @return
	 */
	public int getDisplayHeight() {
		if (mScreenHeight <= 0) {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			mScreenHeight = dm.heightPixels;
		}
		return mScreenHeight;
	}

}
