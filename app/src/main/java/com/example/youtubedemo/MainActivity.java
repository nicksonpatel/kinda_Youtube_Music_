package com.example.youtubedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SongListAdapter.ItemClickListener {

    private MyWebView webView;

    private Button play;

    private  Button pause;

    private Button next;

    private  Button pre;

    private YoutubeMediaController youtubeMediaController;

    ProgressBar progressBar;

    RecyclerView rlist;

    RelativeLayout rl_window;

    List<String> songUrl;

    List<String> songVideoNames;

    SongListAdapter adapter;

    RelativeLayout rlblack;

    final Handler handler = new Handler();

    int count = 0;

    ProgressBar progressBarlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        IntentFilter fliter=new IntentFilter();
        fliter.addDataScheme("http");



        webView = findViewById(R.id.W_view);
        progressBar = findViewById(R.id.progressbar);
        progressBarlist = findViewById(R.id.progressbarlist);
        progressBarlist.setVisibility(View.GONE);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        pre = findViewById(R.id.pre);
        rl_window = findViewById(R.id.rl_window);

        rlist=findViewById(R.id.Rlistview);
        rlist.setLayoutManager(new LinearLayoutManager(this));
        rlblack= findViewById(R.id.rlblack);


        webView.setWebViewClient(new Browser_home());
        webView.setWebChromeClient(new MyChrome());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
        loadWebsite("https://m.youtube.com/watch?v=Zi9To04PO78");


        youtubeMediaController = new YoutubeMediaController(webView);
        youtubeMediaController.startMediaNotification(MainActivity.this);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeMediaController.PlayVideo();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeMediaController.PauseVideo();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeMediaController.fetchvideoNamelist();
                youtubeMediaController.fetchvideoUrllist();
                progressBarlist.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        songUrl = youtubeMediaController.getVideoUrlList();
                        songVideoNames=youtubeMediaController.getVideoNameList();
                        setAdapter();
                        progressBarlist.setVisibility(View.GONE);
                    }
                },500);

//                youtubeMediaController.fetchvideoUrllist();
//                progressBarlist.setVisibility(View.VISIBLE);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        songUrl=youtubeMediaController.getVideoUrlList();
//                        setAdapter();
//                        progressBarlist.setVisibility(View.GONE);
//                    }
//                },500);
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeMediaController.PreVideo();
            }
        });
    }

    public void setAdapter() {
        if(songVideoNames != null) {
            adapter = new SongListAdapter(this, songVideoNames);
            adapter.setClickListener(this);
            rlist.setAdapter(adapter);
        }
    }

    private void loadWebsite(String url) {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            rlblack.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            webView.loadUrl(url);
        } else {
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        loadWebsite(songUrl.get(position));
    }

    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("NICK","load completed");
            setTitle(view.getTitle());
            youtubeMediaController.setfullscreen();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    rlblack.setVisibility(View.GONE);
                }
            },500);
            super.onPageFinished(view, url);
        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        /*
         *onShowCustomView method to support fullscreen in
         */
        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(rl_window.getWidth(),rl_window.getHeight()));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }
}
