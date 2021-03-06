package com.example.xutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xutils.fragment.BitmapFragment;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ImageActivity extends Activity {

	@ViewInject(R.id.big_img)
	private ImageView bigImage;

	private BitmapUtils bitmapUtils;

	private BitmapDisplayConfig bigPicDisplayConfig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);

		ViewUtils.inject(this);
		String imgUrl = getIntent().getStringExtra("url");

		bitmapUtils = BitmapFragment.bitmapUtils;
		if (bitmapUtils == null) {
			bitmapUtils = BitmapHelp.getBitmapUtils(this
					.getApplicationContext());
		}
		bigPicDisplayConfig = new BitmapDisplayConfig();
		// bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用,
		// 图片太大时容易OOM。
		bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
		bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils
				.getScreenSize(this));
		BitmapLoadCallBack<ImageView> callback = new DefaultBitmapLoadCallBack<ImageView>() {
			@Override
			public void onLoadStarted(ImageView container, String uri,
					BitmapDisplayConfig config) {
				super.onLoadStarted(container, uri, config);
				Toast.makeText(getApplicationContext(), uri, 300).show();
			}

			@Override
			public void onLoadCompleted(ImageView container, String uri,
					Bitmap bitmap, BitmapDisplayConfig config,
					BitmapLoadFrom from) {
				super.onLoadCompleted(container, uri, bitmap, config, from);
				Toast.makeText(getApplicationContext(),
						bitmap.getWidth() + "*" + bitmap.getHeight(), 300)
						.show();
			}
		};

		bitmapUtils.display(bigImage, imgUrl, bigPicDisplayConfig, callback);
		// 读取assets中的图片
		// bitmapUtils.display(bigImage, "assets/img/wallpaper.jpg",
		// bigPicDisplayConfig, callback);
	}

}
