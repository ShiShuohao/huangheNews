package cn.huangheNews.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.huangheNews.R;
import cn.huangheNews.dao.TypeDao;
import cn.huangheNews.pojo.Type;

import java.util.ArrayList;
import java.util.List;

public class AllTypeActivity extends AppCompatActivity {

    TextView latestTextView;
    TextView hotTextView;
    TextView typeTextView;
    RecyclerView listRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_type);

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
                Intent intent = new Intent(AllTypeActivity.this, LatestNews.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        hotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllTypeActivity.this, HotActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        typeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllTypeActivity.this, AllTypeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        final Handler handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                List<Type> list = new ArrayList<>();
                if (msg.what==1) {
                    list = (List<Type>) msg.obj;
                }
                listRecycleView.setAdapter(new MyAdapter(list));
                LinearLayoutManager layoutManager = new LinearLayoutManager(AllTypeActivity.this);
                listRecycleView.setLayoutManager(layoutManager);
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                List<Type> list = new ArrayList<>();
                try {
                    list = TypeDao.selectType();
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

        List<Type> list = new ArrayList<>();  //新闻列表

        public MyAdapter(List<Type> list) {
            super();
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //引入布局文件
            View view = LayoutInflater.from(AllTypeActivity.this).inflate(R.layout.list_item_all_type, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        //position 代表list里的第position个元素
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.typeTextView.setText(list.get(position).getName());
            holder.typeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AllTypeActivity.this, TypeContentActivity.class);
                    //Intent 传参
                    intent.putExtra("typeName",list.get(position).getName());
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

        TextView typeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.type);
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