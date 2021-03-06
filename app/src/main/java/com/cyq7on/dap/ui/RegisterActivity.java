package com.cyq7on.dap.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.cyq7on.dap.R;
import com.cyq7on.dap.adapter.DepAndDoctorAdapter;
import com.cyq7on.dap.base.ParentWithNaviActivity;
import com.cyq7on.dap.bean.Department;
import com.cyq7on.dap.event.FinishEvent;
import com.cyq7on.dap.model.BaseModel;
import com.cyq7on.dap.model.UserModel;
import com.cyq7on.dap.util.SPUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 注册界面
 *
 * @author :smile
 * @project:RegisterActivity
 * @date :2016-01-15-18:23
 */
public class RegisterActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_register)
    Button btn_register;

    @Bind(R.id.et_password_again)
    EditText et_password_again;
    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.et_age)
    EditText etAge;
    @Bind(R.id.et_record)
    EditText etRecord;
    @Bind(R.id.et_dep)
    EditText etDep;
    @Bind(R.id.rgSex)
    RadioGroup rgSex;
    private byte role = 1;
    private byte depId;
    private byte sex = 1;


    @Override
    protected String title() {
        return "注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initNaviView();
    }

    @Override
    protected void initView() {
        super.initView();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                role = (byte) i;
                Logger.d("rg: " + i);
            }
        });
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //这一组i从3开始
                sex = (byte) (i - 3);
                Logger.d("rgSex: " + i);
            }
        });
    }

    @OnClick(R.id.et_dep)
    public void onClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("请选择科室")
                .setItems(DepAndDoctorAdapter.dep, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dep = DepAndDoctorAdapter.dep[i];
                        Department department = new Department();
                        department.setDep(dep);
                        department.setDepId(i);
                        etDep.setText(dep);
                        depId = (byte) i;
                        /*department.save(getApplicationContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Logger.d("dep success");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Logger.d(i + s);
                            }
                        });*/
                    }
                }).show();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view) {
        LogInListener logInListener = new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    toast("注册成功");
                    String user = et_username.getText().toString();
                    String pwd = et_password.getText().toString();
                    SPUtil.putAndApply(getApplicationContext(), "pwd", pwd);
                    UserModel.getInstance().login(user, pwd, new LogInListener() {
                        @Override
                        public void done(Object o, BmobException e) {
                            if (e == null) {
                                EventBus.getDefault().post(new FinishEvent());
                                startActivity(MainActivity.class, null, true);
                            } else {
                                toast("登录失败");
                                finish();
                            }
                        }
                    });

                } else {
                    if (e.getErrorCode() == BaseModel.CODE_NOT_EQUAL) {
                        et_password_again.setText("");
                    }
                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        };
        switch (role) {
            case 2:
                UserModel.getInstance().registerDoctor(et_username.getText().toString(),
                        et_password.getText().toString(), et_password_again.getText().toString(),
                        etAge.getText().toString(), sex, etDep.getText().toString()
                        , depId,logInListener);
                break;
            default:
                UserModel.getInstance().registerPatient(et_username.getText().toString(),
                        et_password.getText().toString(), et_password_again.getText().toString(),
                        etAge.getText().toString(), sex, etRecord.getText().toString()
                        , logInListener);
                break;
        }

    }

}
