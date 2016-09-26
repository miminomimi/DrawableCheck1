package imai.drawablecheck1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    Bitmap eri_800_1280, eri_1080_1920, eri_360_640, eri_533_853, eri_601_962;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // システムバーを非表示、内側にフリックしてView上に半透明で表示
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        // Nexus7(2012)
        eri_800_1280 = BitmapFactory.decodeResource(getResources(), R.drawable.eri_800_1280); // 元
        eri_601_962 = BitmapFactory.decodeResource(getResources(), R.drawable.eri_601_962); // ×1.33でピタリ
        eri_533_853 = BitmapFactory.decodeResource(getResources(), R.drawable.eri_533_853); // hdpiの×1.5は小さすぎ

        // Xperia sol26用
        eri_1080_1920 = BitmapFactory.decodeResource(getResources(), R.drawable.eri_1080_1920); // 元
        eri_360_640 = BitmapFactory.decodeResource(getResources(), R.drawable.eri_360_640); // xxhdpiなので×3でピタリ
        eri_533_853 = BitmapFactory.decodeResource(getResources(), R.drawable.eri_533_853); // hdpiの×1.5は小さすぎ
        EriView eriView = new EriView(this);

        // 開発者向けオプションでは切ってあるのにGPUレンダリングをどこかでしてて
        // 大きい画像がロードできないので無理やりViewに設定
        eriView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setContentView(eriView);
    }

    class EriView extends View {
        public EriView(Context cn) {
            super(cn);
            invalidate();
        }

        protected void onDraw(Canvas cs) {
            cs.drawBitmap(eri_601_962, 0, 0, null);
            //cs.drawBitmap(eri_533_853, 0, 0, null);
            //cs.drawBitmap(eri_360_640, 0, 0, null);
            //cs.drawBitmap(eri_800_1280, 0, 0, null);
            //cs.drawBitmap(eri_1080_1920, 0, 0, null);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);

            cs.drawLine(0, 0, getWidth(), getHeight(), paint);
            // 上でシステムバーの表示非表示の設定で右下隅が異なる
            // ちなみに上の画像はシステムバーを含めた領域に表示される

            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display dp = wm.getDefaultDisplay();
            Point point = new Point();
            dp.getSize(point);
            paint.setColor(Color.RED);
            cs.drawLine(0, 0, getWidth(), getHeight(), paint);
            // 上のgetWidth(),getHeight()と同じ挙動
            // なぜテキストp131ではこんなめんどうを？深い意味あり？
        }
    }
}
