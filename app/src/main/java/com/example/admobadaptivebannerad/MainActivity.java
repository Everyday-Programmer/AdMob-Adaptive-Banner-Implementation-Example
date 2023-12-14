package com.example.admobadaptivebannerad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import android.graphics.Rect;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView adView;
    private FrameLayout adContainer;
    private boolean initialLayoutComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this);

        adContainer = findViewById(R.id.adContainer);

        adView = new AdView(MainActivity.this);
        adContainer.addView(adView);
        adContainer.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true;
                adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
                AdSize adSize = getAdSize();
                adView.setAdSize(adSize);

                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
            }
        });
    }

    private AdSize getAdSize() {
        WindowMetrics windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(MainActivity.this);
        Rect bounds = windowMetrics.getBounds();

        float adWidthPixels = adContainer.getWidth();

        if (adWidthPixels == 0f) {
            adWidthPixels = bounds.width();
        }

        float density = getResources().getDisplayMetrics().density;
        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }
}