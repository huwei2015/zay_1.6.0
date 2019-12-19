package com.example.administrator.zahbzayxy.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
public class AboutUsActivity extends BaseActivity {
    TextView aboutUs_tv;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        img_back = (ImageView) findViewById(R.id.exam_archives_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        aboutUs_tv= (TextView) findViewById(R.id.aboutUs_tv);
        aboutUs_tv.setText("中安云教育APP：\n" +
                "中安华邦（chipont.com.cn）旗下安全生产教育培训专业平台，通过提供高质量的安全生产专业课程、系统化的安全培训解决方案和创新的在线学习体验，与权威专家、优秀讲师、专业机构、知名院校合作，共同致力于打造安全生产领域系统、全面、专业的资源共享与知识交流平台。\n" +
                "包括：岗位安全资格培训、安全执业资格培训、安题库、资源中心等模块。\n");
    }
}
