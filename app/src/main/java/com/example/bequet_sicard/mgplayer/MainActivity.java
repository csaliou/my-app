package com.example.bequet_sicard.mgplayer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    /*
     * Private attributes declaration :
     * - string for video file
     * - VideoView for video view
     * - MediaController for media controller
     * - int for current position playback
     * - int for current position chapter
     * - string for playback extra
     * - string for chapter extra
     * - spinner for chapter list
     * - array adapter for string chapter
     * - int for video duration
     * - webview for web page
     * - string for urls
     */
    private static final String myVideo = "https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4";
    private VideoView myVideoView;
    private MediaController myMediaController;
    private int myCurrentPlayback = 0;
    private int myCurrentChapter = 0;
    private static final String myPlayback = "my_playback";
    private static final String myChapter = "my_chapter";
    private Spinner mySpinner;
    private ArrayAdapter<CharSequence> myAdapter;
    private int myVideoDuration = 0;
    private videoViewModel myViewModel;
    private Boolean mySpinnerFirstCall = true;
    private WebView infoView;
    private String[] urls = {
            "https://en.wikipedia.org/wiki/Big_Buck_Bunny",
            "https://en.wikipedia.org/wiki/Big_Buck_Bunny#Plot",
            "https://en.wikipedia.org/wiki/Big_Buck_Bunny#Production_history",
            "https://en.wikipedia.org/wiki/Big_Buck_Bunny#Release",
            "https://en.wikipedia.org/wiki/Big_Buck_Bunny#Characters",
            "https://en.wikipedia.org/wiki/Big_Buck_Bunny#See_also"
    };

    /*
     * Override onCreate with bundle instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
         * super.onCreate with instance and setContentView
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * init myCurrentPosition with saved position
         */
        if(savedInstanceState != null) {
            myCurrentPlayback = savedInstanceState.getInt(myPlayback);
            myCurrentChapter = savedInstanceState.getInt(myChapter);
        }

        /*
         * Define VideoView and MediaController
         */
        myVideoView = findViewById(R.id.my_video_view);
        myMediaController = new MediaController(this);
        myVideoView.setMediaController(myMediaController);

        /*
         * Define Spinner
         */
        mySpinner = findViewById(R.id.my_spinner);
        myAdapter = ArrayAdapter.createFromResource(this, R.array.chapter_array, R.layout.spinner_item); // android.R.layout.simple_spinner_item
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setVisibility(View.GONE);

        /*
         * Define the ViewModel.
         */
        myViewModel = ViewModelProviders.of(this).get(videoViewModel.class);

        /*
         * Define the WebView
         */
        infoView = findViewById(R.id.webview);
        infoView.getSettings().setJavaScriptEnabled(true);
        infoView.setWebViewClient(new WebViewClient());
        infoView.loadUrl(urls[0]);
    }

    /*
     * Private method to initialize player
     */
    private void initPlayer(){

        /*
         * Create the observer (update UI)
         */
        final Observer<Integer> positionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newPosition) {
                /*
                 * Update UI (Spinner item)
                 */
                try{
                    mySpinnerFirstCall = false;
                    mySpinner.setSelection(myViewModel.getCurrentPosition().getValue());
                    infoView.loadUrl(urls[myViewModel.getCurrentPosition().getValue()]);
                }catch(java.lang.NullPointerException e){
                    Log.i("MGPLayer",e.toString());
                }
            }
        };

        /*
         * Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
         */
        myViewModel.getCurrentPosition().observe(this, positionObserver);

        /*
        public static void main(String[] args) {
            final Runnable task = new Runnable() {

                @Override
                public void run() {
                    System.out.println("exécuté toutes les secondes");
                }
            };
            final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
            */

        /*
         * Get and set Uri for VideoView
         */
        Uri myVideoUri = Uri.parse(myVideo);
        myVideoView.setVideoURI(myVideoUri);

        /*
         * Prepare video view
         */
        myVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mediaPlayer){

                        /*
                         * Start video and update media controller
                         */
                        myVideoDuration = myVideoView.getDuration();
                        myMediaController.setAnchorView(myVideoView);
                        myVideoView.start();

                        /*
                         * Restore position if exist
                         */
                        if (myCurrentPlayback > 0){
                            myVideoView.seekTo(myCurrentPlayback);
                        } else {
                            myVideoView.seekTo(1);
                        }

                        /*
                         * Re-set anchor when Video Screen change size
                         */
                        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                myMediaController.setAnchorView(myVideoView);
                            }
                        });

                        /*
                         * Listen on Spinner
                         */
                        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if(mySpinnerFirstCall){

                                    // Get item selected
                                    Object item = parent.getItemAtPosition(position);

                                    // Test if item is not null and make toast
                                    if (item != null) {
                                        int chapter_position = (int)(((float)position/(float)myAdapter.getCount())*(float)myVideoDuration);
                                        if(myCurrentChapter != chapter_position) {
                                            Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
                                            myCurrentPlayback = chapter_position;
                                            myCurrentChapter = position;
                                            infoView.loadUrl(urls[myCurrentChapter]);
                                            myVideoView.seekTo(myCurrentPlayback);
                                        }
                                    }
                                }
                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                                // Nothing is selected
                                Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                            }
                        });


                        /*
                         * Listen on MediaPlayer
                         */
                        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                            @Override
                            public void onSeekComplete(MediaPlayer mp) {
                                int seek_position = (int)(((float)mediaPlayer.getCurrentPosition()/(float)myVideoDuration)*(float)myAdapter.getCount());

                                if(myCurrentChapter > seek_position) {
                                    seek_position = myCurrentChapter;
                                }

                                myViewModel.getCurrentPosition().setValue(seek_position);
                            }
                        });
                    }
                });
    }

    /*
     * Override onTouchEvent
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        myMediaController.show();
        mySpinner.setVisibility(View.VISIBLE);

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mySpinnerFirstCall = true;
            }
        },1000);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mySpinner.setVisibility(View.GONE);
            }
        },3000);

        return false;
    }

    /*
     * Private method to stop player
     */
    private void relPlayer(){
        myVideoView.stopPlayback();
    }

    /*
     * Override method onStart and add initPlayer
     */
    @Override
    protected void onStart(){
        super.onStart();
        initPlayer();
    }

    /*
     * Override method onStop and add relPlayer
     */
    @Override
    protected void onStop() {
        super.onStop();
        relPlayer();
    }

    /*
     * Override method onPause
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
     * override onSaveInstanceState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /*
         * Save current playback position
         */
        outState.putInt(myPlayback, myVideoView.getCurrentPosition());
        outState.putInt(myChapter, myCurrentChapter);
    }

    /*
     * override onRestoreInstanceState
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        /*
         * Get current playback position and chapter position
         */
        myCurrentPlayback = savedInstanceState.getInt(myPlayback);
        myCurrentChapter = savedInstanceState.getInt(myChapter);
        //myVideoView.seekTo(myCurrentPlayback);
    }
}
