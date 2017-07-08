package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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

    //private ImageButton [] imageButtons = new ImageButton[9];

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

        ibTopLeft = (ImageButton) findViewById(R.id.imageButton_top_left);
        ibTopCenter = (ImageButton) findViewById(R.id.imageButton_top_center);
        ibTopRight = (ImageButton) findViewById(R.id.imageButton_top_right);
        ibLeft = (ImageButton) findViewById(R.id.imageButton_left);
        ibCenter = (ImageButton) findViewById(R.id.imageButton_center);
        ibRight = (ImageButton) findViewById(R.id.imageButton_right);
        ibBottomLeft = (ImageButton) findViewById(R.id.imageButton_bottom_left);
        ibBottomCenter = (ImageButton) findViewById(R.id.imageButton_bottom_center);
        ibBottomRight = (ImageButton) findViewById(R.id.imageButton_bottom_right);


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

        setFakeModules();

        ibTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ModuleActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_edit_moules, null);

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private void setFakeModules() {
        moduleList = new ArrayList<>();
        moduleList.add(new Module(3, "time", "Heure à Tahiti", left, "Europe/Paris", null, null));
        moduleList.add(new Module(4, "weather", "Météo à paris", center, null, "Paris", "fr"));
        moduleList.add(new Module(5, "time", "Heure en inde", right, "India/Chennai", null, null));
        moduleList.add(new Module(3, "time", "Heure à Tahiti", topCenter, "Europe/Paris", null, null));
        moduleList.add(new Module(4, "weather", "Météo à paris", topLeft, null, "Paris", "fr"));
        moduleList.add(new Module(4, "weather", "Météo à paris", bottomLeft, null, "Paris", "fr"));
        moduleList.add(new Module(5, "time", "Heure en inde", bottomCenter, "India/Chennai", null, null));
        moduleList.add(new Module(5, "time", "Heure en inde", bottomRight, "India/Chennai", null, null));
        setModule(moduleList);
    }

    private void setModule(List<Module> moduleList) {

        for (Module module : moduleList) {
            Log.i("ModuleActivity", "Module : " + module.toString());

            switch (module.getPosition()) {

                case topLeft:
                    setModuleView(ibTopLeft, module.getType());
                    break;

                case topCenter:
                    setModuleView(ibTopCenter, module.getType());
                    break;

                case topRight:
                    setModuleView(ibTopRight, module.getType());
                    break;

                case left:
                    setModuleView(ibLeft, module.getType());
                    break;

                case center:
                    setModuleView(ibCenter, module.getType());
                    break;

                case right:
                    setModuleView(ibRight, module.getType());
                    break;

                case bottomLeft:
                    setModuleView(ibBottomLeft, module.getType());
                    break;

                case bottomCenter:
                    setModuleView(ibBottomCenter, module.getType());
                    break;

                case bottomRight:
                    setModuleView(ibBottomRight, module.getType());
                    break;

            }
        }
    }

    private void setModuleView(ImageButton ibPosition, String type) {
        if (type.equals(timeModule)) {
            ibPosition.setBackground(timeIconDrawable);
        } else if (type.equals(weatherModule)) {
            ibPosition.setBackground(weatherIconDrawable);
        } else {
            ibPosition.setBackground(attentionIconDrawable);
        }
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
