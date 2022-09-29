package com.example.chambit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView wView;      //webview
    ProgressBar pBar;   //loading bar
    EditText urlEt;     //input address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wView = findViewById(R.id.wView);       // 웹뷰
        pBar = findViewById(R.id.pBar);         // 로딩바
        pBar.setVisibility(View.GONE);          // 호딩바 가리기 (로딩 때만 보여주기)

        initWebView();

        urlEt = findViewById(R.id.urlEt);
        urlEt.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    wView.loadUrl("https://"+urlEt.getText().toString()+"");
                }
                return false;
            }
        });
    }

    public void initWebView() {
        // 1. 웹뷰클라이언트 연결 (로딩 시작/끝 받아오기)
        wView.setWebViewClient(new WebViewClient(){
            @Override                                       // 1) 로딩 시작
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                pBar.setVisibility(View.GONE);              // 로딩이 시작되면 로딩바 보이기
            }
            @Override                                       // 로딩 끝
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                pBar.setVisibility(View.GONE);              // 3) 웹브라우저가 아닌 웹뷰 자체에서 url 호출
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        // 2. WebSettigs : 웹뷰의 각종 설정을 정할 수 있다.
        WebSettings ws = wView.getSettings();
        ws.setJavaScriptEnabled(true); // 자바스크립트 사용 허가

        // 3. 웹페이지 호출
        wView.loadUrl("128.134.49.18:5000/");
    }

    // 뒤로가기 동작 컨트롤
    @Override
    public void onBackPressed(){
        if(wView.canGoBack()){      // 이전 페이지가 존재하면
            wView.goBack();         // 이전 페이지로 돌아가고
        }
        else{
            super.onBackPressed();  // 없으면 앱 종료
        }
    }
}