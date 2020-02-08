package com.example.youtubedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.ValueCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class YoutubeMediaController {

    private static MyWebView YoutubeWeb;

    private boolean isVideoPlaying;

    private static final String TAG = "YoutubeMediaController";

    private NotificationProvider notification;

    private List<String> VideoList = null;

    private List<String> VideoNameList = null;

    final Handler handler = new Handler();

    public YoutubeMediaController(MyWebView youtubeWeb) {
        YoutubeWeb = youtubeWeb;
    }

    public void PauseVideo() {
        YoutubeWeb.goBack();
       // Log.d(TAG, "PauseVideo: "+isVideoPlaying());
        // isVideoPlaying getting updated late TODO
      //  isVideoPlaying();
        if (true) {
            YoutubeWeb.evaluateJavascript("function pauseVideo() {\n" +
                    "  var ele=document.getElementsByTagName(\"video\")[0];\n" +
                    "  ele.pause();\t\n" +
                    "}\n" +
                    "pauseVideo();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //
                }
            });
        } else {
            Log.e(TAG, "PauseVideo: YoutubeWeb is Null ");
        }
    }

    public void PlayVideo() {
       // isVideoPlaying();
        // isVideoPlaying getting updated late TODO
        if (true) {
            YoutubeWeb.evaluateJavascript("function playVideo() {\n" +
                    "  var ele=document.getElementsByTagName(\"video\")[0];\n" +
                    "  ele.play();\t\n" +
                    "}\n" +
                    "playVideo();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //
                }
            });
        } else {
            Log.e(TAG, "PlayVideo: YoutubeWeb is Null ");
        }
    }

    public void NextVideo() {
      //  isVideoPlaying();
        if (true) {
            YoutubeWeb.evaluateJavascript("function NextVideo() {\n" +
                    " \tvar arr_elms = [];\n" +
                    "\tarr_elms = document.body.getElementsByTagName(\"button\");\n" +
                    "\tvar elms_len = arr_elms.length;\n" +
                    "\t\n" +
                    "\tfor (var i = 0; i < elms_len; i++) {\n" +
                    "\t  if(arr_elms[i].getAttribute(\"aria-label\") != null) {  \n" +
                    "\t\tif(arr_elms[i].getAttribute(\"aria-label\") == \"Next\" || arr_elms[i].getAttribute(\"aria-label\") == \"Next video\") {\n" +
                    "\t\tarr_elms[i].click();\n" +
                    "\t\t}\n" +
                    "\t   }\n" +
                    "\t}\t\n" +
                    "\n" +
                    "}\n" +
                    "NextVideo();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.d(TAG, value);
                }
            });
            //setVideoInformationToNotification();
        } else {
            Log.d(TAG, "video is not playing");
        }
    }

    public void PreVideo() {
      //  isVideoPlaying();
        if (true) {
            YoutubeWeb.evaluateJavascript("function NextVideo() {\n" +
                    " \tvar arr_elms = [];\n" +
                    "\tarr_elms = document.body.getElementsByTagName(\"button\");\n" +
                    "\tvar elms_len = arr_elms.length;\n" +
                    "\t\n" +
                    "\tfor (var i = 0; i < elms_len; i++) {\n" +
                    "\t  if(arr_elms[i].getAttribute(\"aria-label\") != null) {  \n" +
                    "\t\tif(arr_elms[i].getAttribute(\"aria-label\") == \"Previous\" || arr_elms[i].getAttribute(\"aria-label\") == \"Previous video\") {\n" +
                    "\t\tarr_elms[i].click();\n" +
                    "\t\t}\n" +
                    "\t   }\n" +
                    "\t}\t\n" +
                    "\n" +
                    "}\n" +
                    "NextVideo();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.d(TAG, value);
                }
            });
            //setVideoInformationToNotification();
        } else {
            Log.d(TAG, "video is not playing");
        }
    }

    public boolean isVideoPlaying() {
        YoutubeWeb.evaluateJavascript("function isVideoPlaying() {\n" +
                "    var myVideo=document.getElementsByTagName(\"video\")[0];\n" +
                "    if (myVideo.paused) {\n" +
                "  \treturn false; \n" +
                "\t}\n" +
                "     else { \n" +
                "  \treturn true; \n" +
                "\t} \n" +
                "}\n" +
                "isVideoPlaying();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                if (value.equalsIgnoreCase("true")) {
                    Log.d(TAG, "onReceiveValue: " + true);
                    isVideoPlaying = true;
                } else {
                    Log.d(TAG, "onReceiveValue: " + false);
                    isVideoPlaying = false;
                }
            }
        });
        return isVideoPlaying;
    }

    public void setfullscreen() {
        YoutubeWeb.evaluateJavascript("function Fullscreen() {\n" +
                " \tvar arr_elms = [];\n" +
                "\tarr_elms = document.body.getElementsByTagName(\"button\");\n" +
                "\tvar elms_len = arr_elms.length;\n" +
                "\t\n" +
                "\tfor (var i = 0; i < elms_len; i++) {\n" +
                "\t  if(arr_elms[i].getAttribute(\"aria-label\") != null) {  \n" +
                "\t\tif(arr_elms[i].getAttribute(\"aria-label\") == \"Full screen\") {\n" +
                "\t\tarr_elms[i].click();\n" +
                "\t\t}\n" +
                "\t   }\n" +
                "\t}\t\n" +
                "\n" +
                "}\n" +
                "Fullscreen();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });
    }

    public void startMediaNotification(final Activity activity) {
            notification=new NotificationProvider(activity.getApplicationContext(),activity);
            notification.createNotificationChannel("PAUSE_101","PAUSE_NOTIFICATION");
            notification.setupNotification(101,R.drawable.ic_android_black_24dp,"PAUSE","clicked on PAUSE");
            notification.setAlwaysOnNotification(true);
            notification.sendNotification();
            Intent intent=new Intent(activity, NotificationService.class);
            intent.putExtra("noti_id", notification.getNOTIFICATION_ID());
            activity.startService(intent);
         }

    private void setVideoInformationToNotification() {
        YoutubeWeb.evaluateJavascript("function GetVideoInformation() {\n" +
                " var ghead = document.getElementsByTagName('h2')[0];\n" +
                " return ghead.innerText; \n" +
                "}\n" +
                "GetVideoInformation();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                notification.updateNotification("SONG",value);
                notification.sendNotification();
            }
        });
    }

    public List<String> fetchvideoUrllist() {
        YoutubeWeb.evaluateJavascript("function fetchVideoListUrl() {\n" +
                "\tvar arr_elms = [];\n" +
                "\tvar videourl = [];\n" +
                "\tvar index = 0;\n" +
                "\tarr_elms = document.body.getElementsByTagName(\"a\");\n" +
                "\tvar elms_len = arr_elms.length;\n" +
                "\tfor (var i = 0; i < elms_len; i++) {\n" +
                "\t\t\n" +
                " \tif(arr_elms[i].getAttribute(\"class\") != null) {  \n" +
                "\t   if(arr_elms[i].getAttribute(\"class\") == \"compact-media-item-image\") {\n" +
                "\t\tvar url = arr_elms[i].href;\n" +
                "\t\tvideourl.push(url);\n" +
                "\t\t}\n" +
                "\t   }\n" +
                "\t}\n" +
                "\treturn videourl;\t\n" +
                "}\n" +
                "fetchVideoListUrl();\n", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                VideoList = Arrays.asList(value.replace("[","").replace("]","").replace("\"","").split(","));
                for(int i=0;i < VideoList.size();i++) {
                  //  Log.d(TAG, "onReceiveValue: "+ VideoList.get(i));
                }
            }
        });
       // Log.d(TAG, "VideListSize "+ VideoList.size());
        return VideoList;

    }

    public void fetchvideoNamelist() {
        YoutubeWeb.evaluateJavascript("function fetchVideoNameList() {\n" +
                "\tvar arr_elms = [];\n" +
                "\tvar videoName = [];\n" +
                "\tarr_elms = document.body.getElementsByTagName(\"h4\");\n" +
                "\tvar elms_len = arr_elms.length;\n" +
                "\tfor (var i = 0; i < elms_len; i++) {\n" +
                "\t\t\n" +
                "\t\tif(arr_elms[i].getAttribute(\"class\") != null) {  \n" +
                "\t\tif(arr_elms[i].getAttribute(\"class\") == \"compact-media-item-headline\") {\n" +
                "\t\t\tvar Vname = arr_elms[i].querySelector(\"span\").getAttribute(\"aria-label\");\n" +
                "\t\t\tvideoName.push(Vname);\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t\treturn videoName;\n" +
                "}\n" +
                "fetchVideoNameList();\n", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
               // Log.d(TAG, "onReceiveValue: "+value);
                VideoNameList = Arrays.asList(value.replace("[","").replace("]","").split("\",\""));
                for(int i=0;i < VideoNameList.size();i++) {
                    Log.d(TAG, "onReceiveValue: "+ VideoNameList.get(i));
                }
            }
        });
    }

    public List<String> getVideoUrlList() {
        return VideoList;
    }

    public List<String> getVideoNameList() {
        return  VideoNameList;
    }
}
