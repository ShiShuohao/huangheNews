package cn.huangheNews.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.huangheNews.R;
import cn.huangheNews.dao.News_UserDao;
import cn.huangheNews.pojo.News_User;

public class NewsContentActivity extends AppCompatActivity {

    ImageView backImageView;
    TextView headlineTextView;
    ImageView headImageView;
    TextView petNameTextView;
    WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        backImageView = findViewById(R.id.back);
        headlineTextView = findViewById(R.id.headline);
        headImageView = findViewById(R.id.head);
        petNameTextView = findViewById(R.id.petName);
        contentWebView = findViewById(R.id.content);

        int newsId = getIntent().getIntExtra("newsId", 0);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                News_User news_usr = null;
                if (msg.what==1) {
                    news_usr = (News_User) msg.obj;
                }
                headlineTextView.setText(news_usr.getHeadline());
                if (news_usr.getSex().equals("男")) {
                    headImageView.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                }else {
                    headImageView.setImageDrawable(getResources().getDrawable(R.drawable.girl));
                }
                petNameTextView.setText(news_usr.getPetname());
                contentWebView.loadDataWithBaseURL(null,news_usr.getContent(),"text/html","utf-8",null);
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                News_User news_user = null;
                try {
                    news_user = News_UserDao.selectNews_UserById(newsId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = 1;
                message.obj = news_user;
                handler.sendMessage(message);
            }
        }.start();

    }

    //重写返回键动画
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(0,0);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}