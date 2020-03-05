package biz.fatos.auto.rstpplayer;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RtspPlayView extends SurfaceView implements SurfaceHolder.Callback {


    private SurfaceHolder mHolder;
    private NDKAdapter mPlayerNdkAdapter;

    public RtspPlayView(Context context, String uri){
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);

        mPlayerNdkAdapter = new NDKAdapter();
        mPlayerNdkAdapter.setDataSource(uri);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        new Thread(new Runnable() {
            @Override public void run() {
                mPlayerNdkAdapter.play(mHolder.getSurface());
            }
        }).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
