package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.esgi.androidclientv2.Model.Module;
import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;
import com.esgi.androidclientv2.Network.IServiceResultListener;
import com.esgi.androidclientv2.Network.RetrofitUserService;
import com.esgi.androidclientv2.Network.ServiceResult;
import com.esgi.androidclientv2.R;

import java.util.ArrayList;
import java.util.List;

public class ModuleActivity extends Activity {

    private final static String topLeft = "topLeft", topCenter = "topCenter", topRight = "topRight",
            left = "left", center = "center", right = "right",
            bottomLeft = "bottomLeft", bottomCenter = "bottomCenter", bottomRight = "bottomRight",
            timeModule = "time", weatherModule = "weather";

    private RetrofitUserService retrofitUserService;
    private TokenResponse tokenResponse;
    private User user;
    private final static String tmpToken = "TK3CnQfTJTm4sgMwap88epEHMz9y6laUgZctsea+jA/WOyYB9UQuo6+rB0Q/Z0EXa0k=";
    private final static int tmpUserId = 10;

    private ImageButton ibTopLeft, ibTopCenter, ibTopRight,
            ibLeft, ibCenter, ibRight,
            ibBottomLeft, ibBottomCenter, ibBottomRight;

    private ImageButton[] imageButtons = new ImageButton[9];

    List<Module> moduleList;

    private Drawable timeIconDrawable, weatherIconDrawable, addIconDrawable,
            attentionIconDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        timeIconDrawable = getDrawable(R.drawable.icons_time_50);
        weatherIconDrawable = getDrawable(R.drawable.icons_weather_50);
        addIconDrawable = getDrawable(R.drawable.icons_add_50);
        attentionIconDrawable = getDrawable(R.drawable.icons_attention_50);

        /*ibTopLeft = (ImageButton) findViewById(R.id.imageButton_top_left);
        ibTopCenter = (ImageButton) findViewById(R.id.imageButton_top_center);
        ibTopRight = (ImageButton) findViewById(R.id.imageButton_top_right);
        ibLeft = (ImageButton) findViewById(R.id.imageButton_left);
        ibCenter = (ImageButton) findViewById(R.id.imageButton_center);
        ibRight = (ImageButton) findViewById(R.id.imageButton_right);
        ibBottomLeft = (ImageButton) findViewById(R.id.imageButton_bottom_left);
        ibBottomCenter = (ImageButton) findViewById(R.id.imageButton_bottom_center);
        ibBottomRight = (ImageButton) findViewById(R.id.imageButton_bottom_right);*/


        imageButtons[0] = (ImageButton) findViewById(R.id.imageButton_top_left);
        imageButtons[1] = (ImageButton) findViewById(R.id.imageButton_top_center);
        imageButtons[2] = (ImageButton) findViewById(R.id.imageButton_top_right);
        imageButtons[3] = (ImageButton) findViewById(R.id.imageButton_left);
        imageButtons[4] = (ImageButton) findViewById(R.id.imageButton_center);
        imageButtons[5] = (ImageButton) findViewById(R.id.imageButton_right);
        imageButtons[6] = (ImageButton) findViewById(R.id.imageButton_bottom_left);
        imageButtons[7] = (ImageButton) findViewById(R.id.imageButton_bottom_center);
        imageButtons[8] = (ImageButton) findViewById(R.id.imageButton_bottom_right);

        setFakeModules();
        setModule(moduleList);

        // set click listener to image buttons
        for (int i = 0; i < imageButtons.length; i++) {
            final int index = i;
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("ModuleActivity", "button clicked : "+index);
                    showModuleDialog(imageButtons[index]);
                }
            });
        }


        /*// get user token model from loginFragment
        Intent intent = getIntent();
        if (intent != null) {
            tokenResponse = intent.getParcelableExtra("UserInfo");
            user = tokenResponse.getUserModules();
            Log.i("UserActivity", "Token : " + tokenResponse.getToken()); // test
        } else {
            Log.i("UserActivity", "Error data transmission");
            return;
        }*/

        //getUserModules();

        //setFakeModules();
        //setModule(moduleList);

        /*ibTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    private void showModuleDialog(ImageButton ib) {

        Module module = (Module) ib.getTag();

        if(module != null){
            Log.i("ModuleActivity", "Dialog update/delete");
        } else {
            Log.i("ModuleActivity", "Dialog add");
            showAddModuleDialog();
        }
    }

    private void showAddModuleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModuleActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_edit_moules, null);

        //init views
        RadioButton rbTime = (RadioButton)  mView.findViewById(R.id.module_radio_button_time);
        RadioButton rbWeather = (RadioButton)  mView.findViewById(R.id.module_radio_button_weather);

        final LinearLayout lLayoutWeather = (LinearLayout) mView.findViewById(R.id.linearLayoutWeather);
        final EditText etTimeZone = (EditText) mView.findViewById(R.id.module_time_zone);

        rbTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etTimeZone.setVisibility(View.VISIBLE);
                lLayoutWeather.setVisibility(View.GONE);
            }
        });

        rbWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etTimeZone.setVisibility(View.GONE);
                lLayoutWeather.setVisibility(View.VISIBLE);
            }
        });

        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addTimeModule(){

    }

    private void setFakeModules() {
        moduleList = new ArrayList<>();
        moduleList.add(new Module(4, "weather", "Météo à paris", topLeft, null, "Paris", "fr"));
        moduleList.add(new Module(5, "time", "Heure en inde", center, "India/Chennai", null, null));
        moduleList.add(new Module(5, "time", "Heure en inde", bottomRight, "India/Chennai", null, null));
    }

    private void setModule(List<Module> moduleList) {

        if (moduleList != null) {
            for (Module module : moduleList) {
                Log.i("ModuleActivity", "Module : " + module.toString());

                switch (module.getPosition()) {

                    case topLeft:
                        setModuleView(imageButtons[0], module);
                        break;

                    case topCenter:
                        setModuleView(imageButtons[1], module);
                        break;

                    case topRight:
                        setModuleView(imageButtons[2], module);
                        break;

                    case left:
                        setModuleView(imageButtons[3], module);
                        break;

                    case center:
                        setModuleView(imageButtons[4], module);
                        break;

                    case right:
                        setModuleView(imageButtons[5], module);
                        break;

                    case bottomLeft:
                        setModuleView(imageButtons[6], module);
                        break;

                    case bottomCenter:
                        setModuleView(imageButtons[7], module);
                        break;

                    case bottomRight:
                        setModuleView(imageButtons[8], module);
                        break;

                }
            }
        }
    }

    private void setModuleView(ImageButton ibPosition, Module module) {

        String modulePosition = module.getPosition();

        switch (modulePosition) {
            case timeModule:
                ibPosition.setBackground(timeIconDrawable);
                break;
            case weatherModule:
                ibPosition.setBackground(weatherIconDrawable);
                break;
            default:
                ibPosition.setBackground(attentionIconDrawable);
                break;
        }

        ibPosition.setTag(module);
    }

    private void getUserModules() {

        getRetrofitUserService().getUserModules(tmpToken, tmpUserId, new IServiceResultListener<User>() {
            @Override
            public void onResult(ServiceResult<User> result) {

                List<Module> moduleList = result.getData().getModules();

                if (moduleList != null) {
                    //sortModules(moduleList);
                } else {
                    Log.i("ModuleActivity", result.getErrorMsg());
                }
            }
        });
    }

    private void sortModules(List<Module> moduleList) {


    }


    public RetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitUserService();
        }
        return retrofitUserService;
    }
}
