package vatupassit.levelcam.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import vatupassit.levelcam.R;

public class MainFragment extends Fragment {

    public ToggleButton toggleButtonCompass;
    public ToggleButton toggleButtonLight;
    LightFragment light = new LightFragment();
    CompassFragment compass = new CompassFragment();

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        toggleButtonCompass = (ToggleButton) view.findViewById(R.id.toggleButtonCompass);
        toggleButtonLight = (ToggleButton) view.findViewById(R.id.toggleButtonLight);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        light = (LightFragment) getChildFragmentManager().findFragmentById(R.id.light_fragment);
        compass = (CompassFragment) getChildFragmentManager().findFragmentById(R.id.compass_fragment);

        toggleButtonCompass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if(isChecked) {
                    ft.show(compass);
                } else {
                    ft.hide(compass);
                }
                ft.commit();
            }
        });

        toggleButtonLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if(isChecked) {
                    ft.show(light);
                } else {
                    ft.hide(light);
                }
                ft.commit();
            }
        });
    }
}
