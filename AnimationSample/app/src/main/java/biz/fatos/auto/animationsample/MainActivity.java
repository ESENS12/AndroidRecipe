package biz.fatos.auto.animationsample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View iconView;
    private AnimationDrawableTest animTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iconView = findViewById(R.id.center_icon);
        Drawable drawable = getResources().getDrawable(R.drawable.onwifi_anim_test);

        if (drawable != null) {
            iconView.setBackground(drawable);

            AnimationDrawable anim = (AnimationDrawable) iconView.getBackground();

            if (anim != null)
            {

                animTest = new AnimationDrawableTest(anim);
                iconView.setBackground(animTest);
                animTest.setDuration(500);
                animTest.run();
            }
        }
    }
}
