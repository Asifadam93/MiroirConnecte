package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.androidclientv2.Model.Module;
import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;
import com.esgi.androidclientv2.Network.IServiceResultListener;
import com.esgi.androidclientv2.Network.RetrofitUserService;
import com.esgi.androidclientv2.Network.ServiceResult;
import com.esgi.androidclientv2.R;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class ModuleActivity extends Activity {

    private final static String topLeft = "top_left", topCenter = "top_center", topRight = "top_right",
            left = "left", center = "center", right = "right",
            bottomLeft = "bottom_left", bottomCenter = "bottom_center", bottomRight = "bottom_right",
            timeModule = "time", weatherModule = "weather";

    private RetrofitUserService retrofitUserService;
    private static String tmpToken;
    private static int tmpUserId;

    private ImageButton[] imageButtons = new ImageButton[9];

    List<Module> moduleList;

    private int positionActuel = -1;
    private int idModuleActuel = -1;

    private Drawable timeIconDrawable, weatherIconDrawable, addIconDrawable,
            attentionIconDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        // get user token model from loginFragment
        Intent intent = getIntent();
        if (intent != null) {
            TokenResponse tokenResponse = intent.getParcelableExtra("UserInfo");
            tmpToken = tokenResponse.getToken();
            tmpUserId = tokenResponse.getUser().getId();
            Log.i("UserActivity", "Token : " + tokenResponse.getToken()); // test
        } else {
            Toast.makeText(this, "Error data transmission", Toast.LENGTH_SHORT).show();
            finish();
        }

        timeIconDrawable = getDrawable(R.drawable.icons_time_50);
        weatherIconDrawable = getDrawable(R.drawable.icons_weather_50);
        addIconDrawable = getDrawable(R.drawable.icons_add_50);
        attentionIconDrawable = getDrawable(R.drawable.icons_attention_50);

        imageButtons[0] = (ImageButton) findViewById(R.id.imageButton_top_left);
        imageButtons[1] = (ImageButton) findViewById(R.id.imageButton_top_center);
        imageButtons[2] = (ImageButton) findViewById(R.id.imageButton_top_right);
        imageButtons[3] = (ImageButton) findViewById(R.id.imageButton_left);
        imageButtons[4] = (ImageButton) findViewById(R.id.imageButton_center);
        imageButtons[5] = (ImageButton) findViewById(R.id.imageButton_right);
        imageButtons[6] = (ImageButton) findViewById(R.id.imageButton_bottom_left);
        imageButtons[7] = (ImageButton) findViewById(R.id.imageButton_bottom_center);
        imageButtons[8] = (ImageButton) findViewById(R.id.imageButton_bottom_right);

        getUserModules();

        // set click listener to image buttons
        for (int i = 0; i < imageButtons.length; i++) {
            final int index = i;
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("ModuleActivity", "button clicked : " + index);
                    showModuleDialog(imageButtons[index]);
                    positionActuel = index; // save clicked position
                }
            });
        }
    }

    private void getUserModules() {

        getRetrofitUserService().getUserModules(tmpToken, tmpUserId, new IServiceResultListener<User>() {
            @Override
            public void onResult(ServiceResult<User> result) {

                User user = result.getData();

                if (user != null) {
                    moduleList = user.getModules();
                    initModules();
                    setModule(moduleList);
                    Toast.makeText(getBaseContext(), "Initialisation terminé", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initModules() {
        for (ImageButton ib : imageButtons) {
            ib.setBackground(addIconDrawable);
            ib.setTag(null);
        }
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

        String moduleType = module.getType();

        switch (moduleType) {
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

    private void showModuleDialog(ImageButton ib) {

        Module module = (Module) ib.getTag();

        if (module != null) {
            Log.i("ModuleActivity", "Dialog update/delete");
            idModuleActuel = module.getId(); //save module id to use with delete/update
            showEditModuleDialog(module, ib);
        } else {
            Log.i("ModuleActivity", "Dialog add");
            showAddModuleDialog();
        }
    }

    private void showEditModuleDialog(final Module module, final ImageButton ib) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ModuleActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_edit_module, null);

        // show welcome msg
        TextView twTitle = (TextView) mView.findViewById(R.id.module_titre);
        String titleText = "Éditer le module " + getModuleTranslatedName(module);
        twTitle.setText(titleText);

        //disable checkboxes
        RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.radioGroupe);
        radioGroup.setVisibility(View.GONE);

        // show delete button
        Button buttonDelete = (Button) mView.findViewById(R.id.module_button_delete);
        buttonDelete.setVisibility(View.VISIBLE);

        Button buttonUpdate = (Button) mView.findViewById(R.id.module_button_save);

        LinearLayout lLayoutWeather = (LinearLayout) mView.findViewById(R.id.linearLayoutWeather);

        // time entry
        final EditText etDesc = (EditText) mView.findViewById(R.id.module_description);
        final AutoCompleteTextView etTimeZone = (AutoCompleteTextView) mView.findViewById(R.id.module_time_zone);

        // auto complete textview
        String[] timeZone = getResources().getStringArray(R.array.time_zone);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,timeZone);
        etTimeZone.setAdapter(arrayAdapter);

        // weather entry
        final EditText etCity = (EditText) mView.findViewById(R.id.module_city);
        final EditText etCountry = (EditText) mView.findViewById(R.id.module_country);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // set module default values to update or delete
        switch (module.getType()) {

            case timeModule:

                etTimeZone.setVisibility(View.VISIBLE);
                lLayoutWeather.setVisibility(View.GONE);

                //set values
                etDesc.setText(module.getName());
                etTimeZone.setText(module.getTimeZone());
                break;

            case weatherModule:

                etTimeZone.setVisibility(View.GONE);
                lLayoutWeather.setVisibility(View.VISIBLE);

                //set values
                etDesc.setText(module.getName());
                etCity.setText(module.getCityName());
                etCountry.setText(module.getCountryCode());
                break;
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String desc = etDesc.getText().toString();

                switch (module.getType()) {

                    case timeModule:

                        String timeZone = etTimeZone.getText().toString();

                        if (desc.isEmpty()) {
                            etDesc.setError(getString(R.string.champ_vide));
                            return;
                        }

                        if (timeZone.isEmpty()) {
                            etTimeZone.setError(getString(R.string.champ_vide));
                            return;
                        }

                        Map<String, String> timeMap = new ArrayMap<String, String>();

                        timeMap.put("name", desc);
                        timeMap.put("position", getPosition());
                        timeMap.put("timeZone", timeZone);

                        updateTimeModule(timeMap);
                        break;

                    case weatherModule:

                        String city = etCity.getText().toString();
                        String country = etCountry.getText().toString();

                        if (desc.isEmpty()) {
                            etDesc.setError(getString(R.string.champ_vide));
                            return;
                        }

                        if (city.isEmpty()) {
                            etCity.setError(getString(R.string.champ_vide));
                            return;
                        }

                        if (country.isEmpty()) {
                            etCountry.setError(getString(R.string.champ_vide));
                            return;
                        }

                        Map<String, String> weatherMap = new ArrayMap<String, String>();

                        weatherMap.put("name", desc);
                        weatherMap.put("position", getPosition());
                        weatherMap.put("city", city);
                        weatherMap.put("country", country);

                        updateWeatherModule(weatherMap);
                        break;
                }
                dialog.cancel();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (module.getType()) {

                    case timeModule:
                        deleteTimeModule();
                        break;

                    case weatherModule:
                        deleteWeatherModule();
                        break;
                }
                dialog.cancel();
            }
        });
    }

    private String getModuleTranslatedName(Module module) {
        switch (module.getType()) {
            case timeModule:
                return "horaire";

            case weatherModule:
                return "météo";
        }
        return "";
    }

    private void showAddModuleDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ModuleActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_add_edit_module, null);

        //init views
        final RadioButton rbTime = (RadioButton) mView.findViewById(R.id.module_radio_button_time);
        final RadioButton rbWeather = (RadioButton) mView.findViewById(R.id.module_radio_button_weather);

        final LinearLayout lLayoutWeather = (LinearLayout) mView.findViewById(R.id.linearLayoutWeather);
        final AutoCompleteTextView etTimeZone = (AutoCompleteTextView) mView.findViewById(R.id.module_time_zone);
        Button buttonSave = (Button) mView.findViewById(R.id.module_button_save);

        String[] timeZone = getResources().getStringArray(R.array.time_zone);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,timeZone);
        etTimeZone.setAdapter(arrayAdapter);


        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();

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

        // add module to user
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText etDesc = (EditText) mView.findViewById(R.id.module_description);
                String desc = etDesc.getText().toString();

                // add time module
                if (rbTime.isChecked()) {

                    EditText etTimeZone = (EditText) mView.findViewById(R.id.module_time_zone);
                    String timeZone = etTimeZone.getText().toString();

                    if (desc.isEmpty()) {
                        etDesc.setError(getString(R.string.champ_vide));
                        return;
                    }

                    if (timeZone.isEmpty()) {
                        etTimeZone.setError(getString(R.string.champ_vide));
                        return;
                    }

                    Map<String, String> timeMap = new ArrayMap<String, String>();

                    timeMap.put("name", etDesc.getText().toString());
                    timeMap.put("position", getPosition());
                    timeMap.put("timeZone", etTimeZone.getText().toString());

                    addTimeModule(timeMap);

                } else if (rbWeather.isChecked()) {
                    // add weather module
                    EditText etCity = (EditText) mView.findViewById(R.id.module_city);
                    EditText etCountry = (EditText) mView.findViewById(R.id.module_country);

                    String city = etCity.getText().toString();
                    String country = etCountry.getText().toString();

                    if (desc.isEmpty()) {
                        etDesc.setError(getString(R.string.champ_vide));
                        return;
                    }

                    if (city.isEmpty()) {
                        etCity.setError(getString(R.string.champ_vide));
                        return;
                    }

                    if (country.isEmpty()) {
                        etCountry.setError(getString(R.string.champ_vide));
                        return;
                    }

                    Map<String, String> weatherMap = new ArrayMap<String, String>();

                    weatherMap.put("name", etDesc.getText().toString());
                    weatherMap.put("position", getPosition());
                    weatherMap.put("city", etCity.getText().toString());
                    weatherMap.put("country", etCountry.getText().toString());

                    addWeatherModule(weatherMap);
                }

                dialog.cancel();
            }
        });
    }

    private void addTimeModule(Map<String, String> timeMap) {

        getRetrofitUserService().addTimeModule(tmpToken, tmpUserId, timeMap,
                new IServiceResultListener<ResponseBody>() {
                    @Override
                    public void onResult(ServiceResult<ResponseBody> result) {

                        if (result.getData() != null) {
                            Toast.makeText(getBaseContext(), "Module a été bien sauvgardé", Toast.LENGTH_SHORT).show();
                            getUserModules();
                        } else {
                            Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void addWeatherModule(Map<String, String> weatherMap) {

        getRetrofitUserService().addWeatherModule(tmpToken, tmpUserId, weatherMap, new IServiceResultListener<ResponseBody>() {
            @Override
            public void onResult(ServiceResult<ResponseBody> result) {

                if (result.getData() != null) {
                    Toast.makeText(getBaseContext(), "Module a été bien sauvgardé", Toast.LENGTH_SHORT).show();
                    getUserModules();
                } else {
                    Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateTimeModule(Map<String, String> timeMap) {

        getRetrofitUserService().updateTimeModule(tmpToken, tmpUserId, idModuleActuel, timeMap,
                new IServiceResultListener<ResponseBody>() {
                    @Override
                    public void onResult(ServiceResult<ResponseBody> result) {
                        if (result.getData() != null) {
                            Toast.makeText(getBaseContext(), "Module a été bien actualisé", Toast.LENGTH_SHORT).show();
                            getUserModules();
                        } else {
                            Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateWeatherModule(Map<String, String> weatherMap) {

        getRetrofitUserService().updateWeatherModule(tmpToken, tmpUserId, idModuleActuel, weatherMap,
                new IServiceResultListener<ResponseBody>() {
                    @Override
                    public void onResult(ServiceResult<ResponseBody> result) {
                        if (result.getData() != null) {
                            Toast.makeText(getBaseContext(), "Module a été bien actualisé", Toast.LENGTH_SHORT).show();
                            getUserModules();
                        } else {
                            Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void deleteTimeModule() {

        getRetrofitUserService().deleteTimeModule(tmpToken, tmpUserId, idModuleActuel, new IServiceResultListener<Integer>() {

            @Override
            public void onResult(ServiceResult<Integer> result) {

                if (result.getData() != null) {
                    Toast.makeText(getBaseContext(), "Module a été bien supprimé", Toast.LENGTH_SHORT).show();
                    getUserModules();
                } else {
                    Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteWeatherModule() {

        getRetrofitUserService().deleteWeatherModule(tmpToken, tmpUserId, idModuleActuel, new IServiceResultListener<Integer>() {
            @Override
            public void onResult(ServiceResult<Integer> result) {

                if (result.getData() != null) {
                    Toast.makeText(getBaseContext(), "Module a été bien supprimé", Toast.LENGTH_SHORT).show();
                    getUserModules();
                } else {
                    Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public RetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitUserService();
        }
        return retrofitUserService;
    }

    private String getPosition() {

        switch (positionActuel) {
            case 0:
                return topLeft;

            case 1:
                return topCenter;

            case 2:
                return topRight;

            case 3:
                return left;

            case 4:
                return center;

            case 5:
                return right;

            case 6:
                return bottomLeft;

            case 7:
                return bottomCenter;

            case 8:
                return bottomRight;

        }
        return "Position error";
    }
}
