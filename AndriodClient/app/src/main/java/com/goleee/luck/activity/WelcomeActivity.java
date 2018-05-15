package com.goleee.luck.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goleee.luck.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends AppCompatActivity
{
    private static ViewPager viewPager;
    private RadioGroup group;
    private TextView protocalLinkText;
    private Button startAppButton;
    //图片资源，实际项目需要从网络获取
    private int[] imageIds = {R.drawable.luck_welcome_slide_1, R.drawable.luck_welcome_slide_2, R.drawable.luck_welcome_slide_3};
    //存放图片的数组
    private List<ImageView> mList;
    //当前索引位置以及上一个索引位置
    private static int index = 0, preIndex = 0;
    //是否需要轮播标志
    private boolean isContinue = true;
    //定时器，用于实现轮播
    private Timer timer = new Timer();
    private LoopPlayHandler mHandler;


    /**
     * 循环播放UI上的图片
     */
    public static class LoopPlayHandler extends Handler
    {
        private WeakReference<WelcomeActivity> weakReference;


        public LoopPlayHandler(WelcomeActivity activity)
        {
            weakReference = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg)
        {
            if(weakReference.get() != null)
            {
                index++;
                viewPager.setCurrentItem(index);
            }
            super.handleMessage(msg);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initData();
        addListener();
        //让当前图片位于中间某个位置，目的就是为了开始能够左滑
        viewPager.setCurrentItem(imageIds.length * 100);
        initRadioButton(imageIds.length);//注意这句和上面那句顺序不能写反，否则会出现第一个圆点无法显示选中状态
        startSwitch();
    }


    /**
     * 初始化控件
     */
    public void initView()
    {
        viewPager = findViewById(R.id.viewPager_img);
        group = findViewById(R.id.radiogroup_switch);
        startAppButton = findViewById(R.id.buttom_start);
        protocalLinkText = findViewById(R.id.text_agree);
    }


    /**
     * 初始化数据
     */
    public void initData()
    {
        mList = new ArrayList<>();
        viewPager.setAdapter(pagerAdapter);
        mHandler = new LoopPlayHandler(this);
    }


    /**
     * 添加监听
     */
    public void addListener()
    {
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setOnTouchListener(onTouchListener);
        protocalLinkText.setMovementMethod(LinkMovementMethod.getInstance());
        protocalLinkText.setText(getClickableSpan());
        startAppButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, LoginPhoneActivity.class);
            WelcomeActivity.this.startActivity(intent);
        });
    }


    /**
     * 协议文字点击类
     */
    private class Clickable extends ClickableSpan implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, WelcomeProtocolActivity.class);
            WelcomeActivity.this.startActivity(intent);
        }


        @Override
        public void updateDrawState(TextPaint ds)
        {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }


    private SpannableString getClickableSpan()
    {
        String strContent = getResources().getString(R.string.luck_welcome_agree_protocal);
        SpannableString spanableInfo = new SpannableString(strContent);
        int start = 5;  //超链接起始位置
        int end = 11;   //超链接结束位置

        //可以为多部分设置超链接
        spanableInfo.setSpan(new Clickable(), start, end, Spanned.SPAN_MARK_MARK);

        return spanableInfo;
    }


    /**
     * 进行图片轮播
     */
    public void startSwitch()
    {
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if(isContinue)
                {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }, 3000, 3500);//延迟3秒，每隔3.5秒发一次消息;
    }


    /**
     * 根据图片个数初始化按钮
     *
     * @param length
     */
    private void initRadioButton(int length)
    {
        for(int i = 0; i < length; i++)
        {
            ImageView imageview = new ImageView(this);
            imageview.setImageResource(R.drawable.luck_welcome_rg_selector);//设置背景选择器
            imageview.setPadding(20, 0, 0, 0);//设置每个按钮之间的间距
            //将按钮依次添加到RadioGroup中
            group.addView(imageview, ViewGroup.LayoutParams.WRAP_CONTENT,
                          ViewGroup.LayoutParams.WRAP_CONTENT);
            //默认选中第一个按钮，因为默认显示第一张图片
            group.getChildAt(0).setEnabled(false);
        }
    }


    /**
     * 根据当前触摸事件判断是否要轮播
     */
    View.OnTouchListener onTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            switch(event.getAction())
            {
                //手指按下和划动的时候停止图片的轮播
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                default:
                    isContinue = true;
            }
            return false;//注意这里只能返回false,如果返回true，Dwon就会消费掉事件，MOVE无法获得事件，
            // 导致图片无法滑动
        }
    };


    /**
     * 根据当前选中的页面设置按钮的选中
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }


        @Override
        public void onPageSelected(int position)
        {
            index = position;//当前位置赋值给索引
            setCurrentDot(index % imageIds.length);//因为只有四个按钮，所以我们在此要对长度区域，保证范围在0到4
        }


        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };


    /**
     * 设置对应位置按钮的状态
     *
     * @param i 当前位置
     */
    private void setCurrentDot(int i)
    {
        if(group.getChildAt(i) != null)
        {
            group.getChildAt(i).setEnabled(false);//当前按钮选中,显示蓝色
        }
        if(group.getChildAt(preIndex) != null)
        {
            group.getChildAt(preIndex).setEnabled(true);//上一个取消选中。显示灰色
            preIndex = i;//当前位置变为上一个，继续下次轮播
        }
    }


    PagerAdapter pagerAdapter = new PagerAdapter()
    {
        @Override
        public int getCount()
        {
            //返回一个比较大的值，目的是为了实现无限轮播
            return Integer.MAX_VALUE;
        }


        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            position = position % imageIds.length;//因为position非常大，而我们需要的position不能大于图片集合长度
            //所以在此取余
            ImageView imageView = new ImageView(WelcomeActivity.this);
            imageView.setImageResource(imageIds[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            mList.add(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            //注意在此不要做任何操作，因为我们需要实现向左滑动，否则会产生IndexOutOfBoundsException
        }
    };


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //页面销毁的时候取消定时器
        if(timer != null)
        {
            preIndex = 0;
            timer.cancel();
        }
    }
}
