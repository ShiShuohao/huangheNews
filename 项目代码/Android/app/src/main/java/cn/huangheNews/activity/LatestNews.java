package cn.huangheNews.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.huangheNews.R;
import cn.huangheNews.dao.NewsDao;
import cn.huangheNews.dao.News_UserDao;
import cn.huangheNews.pojo.News_User;

import java.util.ArrayList;
import java.util.List;

public class LatestNews extends AppCompatActivity {

    TextView latestTextView;
    TextView hotTextView;
    TextView typeTextView;
    RecyclerView listRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        latestTextView = findViewById(R.id.latest);
        hotTextView = findViewById(R.id.hot);
        typeTextView = findViewById(R.id.type);
        listRecycleView = findViewById(R.id.list);

        latestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LatestNews.this, LatestNews.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        hotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LatestNews.this, HotActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        typeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LatestNews.this, AllTypeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        final Handler handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                List<News_User> list = new ArrayList<>();
                if (msg.what==1) {
                    list = (List<News_User>) msg.obj;
                }
                listRecycleView.setAdapter(new MyAdapter(list));
                LinearLayoutManager layoutManager = new LinearLayoutManager(LatestNews.this);
                listRecycleView.setLayoutManager(layoutManager);
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                List<News_User> list = new ArrayList<>();
                try {
                    list = News_UserDao.selectNews_UserForLatest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = 1;
                message.obj = list;
                handler.sendMessage(message);
            }
        }.start();

    }

    //重写适配器
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<News_User> list = new ArrayList<>();  //新闻列表

        public MyAdapter(List<News_User> list) {
            super();
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //引入布局文件
            View view = LayoutInflater.from(LatestNews.this).inflate(R.layout.list_item_news, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        //position 代表list里的第position个元素
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.headlineTextView.setText(list.get(position).getHeadline());
            holder.petnameTextView.setText(list.get(position).getPetname());
            holder.dateTextView.setText(list.get(position).getDate().toString());
            holder.hitsTextView.setText(list.get(position).getClick()+" 浏览");
            holder.commentsTextView.setText(list.get(position).getComment()+" 评论");
            if (list.get(position).getSex().equals("男")) {
                Drawable boy = getResources().getDrawable(R.drawable.boy);
                holder.headImageView.setImageDrawable(boy);
            }else {
                Drawable girl = getResources().getDrawable(R.drawable.girl);
                holder.headImageView.setImageDrawable(girl);
            }
            holder.list_news_itemLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击量增加 1
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                NewsDao.updateNewsForClickIncreaseById(list.get(position).getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    Intent intent = new Intent(LatestNews.this, NewsContentActivity.class);
                    //Intent 传参
                    intent.putExtra("newsId",list.get(position).getId());
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout list_news_itemLinearLayout;
        TextView headlineTextView;
        ImageView headImageView;
        TextView petnameTextView;
        TextView dateTextView;
        TextView hitsTextView;
        TextView commentsTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_news_itemLinearLayout = itemView.findViewById(R.id.list_news_item);
            headlineTextView = itemView.findViewById(R.id.headline);
            headImageView = itemView.findViewById(R.id.head);
            petnameTextView = itemView.findViewById(R.id.petname);
            dateTextView = itemView.findViewById(R.id.date);
            hitsTextView = itemView.findViewById(R.id.hits);
            commentsTextView = itemView.findViewById(R.id.comments);
        }
    }

    //屏蔽返回键，即调转到该页面后按返回键无法返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}