package cn.huangheNews.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TypeContentActivity extends AppCompatActivity {

    ImageView backImageView;
    TextView typeNameTextView;
    RecyclerView newsListRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_content);

        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        backImageView = findViewById(R.id.back);
        typeNameTextView = findViewById(R.id.typeName);
        newsListRecycleView = findViewById(R.id.newsList);

        String typeName = getIntent().getStringExtra("typeName");

        typeNameTextView.setText(typeName);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                newsListRecycleView.setAdapter(new MyAdapter(list));
                newsListRecycleView.setLayoutManager(new LinearLayoutManager(TypeContentActivity.this));
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                List<News_User> list = new ArrayList<>();
                try {
                    list = News_UserDao.selectNews_UserByTypeName(typeName);
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
            View view = LayoutInflater.from(TypeContentActivity.this).inflate(R.layout.list_item_news, parent, false);
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
                    Intent intent = new Intent(TypeContentActivity.this, NewsContentActivity.class);
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