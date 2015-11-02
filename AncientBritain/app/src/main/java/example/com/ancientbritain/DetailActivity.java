package example.com.ancientbritain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImage;
    private ArrayList<MainDataDef> detailData;

    private static final int MIN_DISTANCE = 150;
    private static final int OFF_PATH = 100;
    private static final int VELOCITY_THRESHOLD = 75;
    private GestureDetector detector;
    View.OnTouchListener listener;
    private int ImageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = (ImageView) findViewById(R.id.detail_image);
        detailImage.setOnTouchListener(listener);

        TextView detailName = (TextView) findViewById(R.id.detail_name);
        TextView detailDistance = (TextView) findViewById(R.id.detail_distance);
        TextView detailText = (TextView) findViewById(R.id.detail_text);
        detailText.setMovementMethod(new ScrollingMovementMethod());
        ImageView detailWebLink = (ImageView) findViewById(R.id.detail_web_link);

        int i = MainActivity.currentItem;
        Random n = new Random();
        int m = n.nextInt((600 - 20) + 1) + 20;
        setTitle(getString(R.string.app_name) + " - " + MainData.
                nameArray[i]);
        detailImage.setImageResource(MainData.detailImageArray[i]);
        detailName.setText(MainData.nameArray[i]);
        detailDistance.setText(String.valueOf(m) + " miles");
        detailText.setText(MainData.detailTextArray[i]);

        detailWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(
                        MainData.detailWebLink[MainActivity.currentItem]));
                startActivity(intent);
            }
        });

        detector = new GestureDetector(this, new GalleryGestureDetector());
        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        };

        ImageIndex = 0;
    }

    class GalleryGestureDetector implements GestureDetector.OnGestureListener {

        private int item;
        {
            item = MainActivity.currentItem;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            detailImage.setElevation(4);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            if (Math.abs(event1.getY() - event2.getY()) > OFF_PATH)
                return false;
            if (MainData.galleryArray[item].length != 0) {
                // Swipe left
                if (event1.getX() - event2.getX() > MIN_DISTANCE &&
                        Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    ImageIndex++;
                    if (ImageIndex ==
                            MainData.galleryArray[item].length)
                        ImageIndex = 0;
                    detailImage.setImageResource(MainData
                            .galleryArray[item][ImageIndex]);
                } else {
                    // Swipe right
                    if (event2.getX() - event1.getX() >
                            MIN_DISTANCE && Math.abs(velocityX) >
                            VELOCITY_THRESHOLD) {
                        ImageIndex--;
                        if (ImageIndex < 0) ImageIndex =
                                MainData.galleryArray[item].length - 1;
                        detailImage.setImageResource(MainData
                                .galleryArray[item][ImageIndex]);
                    }
                }
            }
            detailImage.setElevation(0);
            return true;
        }
    }
}
