package edu.iu.habahram.remotecontroller.repository;

import edu.iu.habahram.remotecontroller.model.DeviceData;
import edu.iu.habahram.remotecontroller.model.Light;
import edu.iu.habahram.remotecontroller.model.RemoteControl;
import edu.iu.habahram.remotecontroller.model.Stereo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// make this class thread safe using the singleton pattern and volatile keyword




public class RemoteLoader implements  IRemoteLoader{

    private volatile static RemoteLoader instance;
    HashMap<Integer, RemoteControl> remoteControls = new HashMap<>();

    private RemoteLoader() {
    }

    public static RemoteLoader getInstance() {
        if (instance == null) {
            synchronized (RemoteLoader.class) {
                if (instance == null) {
                    instance = new RemoteLoader();
                }
            }
        }
        return instance;
    }

    @Override
    public void setup(int id, List<DeviceData> devices) {
        RemoteControl remoteControl = new RemoteControl(devices.size());
        for(DeviceData device : devices) {
            switch (device.type()) {
                case "light":
                    Light light = new Light(device.location());
                    remoteControl.setCommand(device.slot(), light::on, light::off);
                    break;
                case "stereo":
                    // add stereo
                    Stereo stereo = new Stereo(device.location());
                    remoteControl.setCommand(device.slot(), stereo::on, stereo::off);
                    break;
            }
        }
        remoteControls.put(id, remoteControl);
        System.out.println(remoteControl.toString());
    }

    @Override
    public String onButtonWasPushed(int id, int slot) {
         return remoteControls.get(id).onButtonWasPushed(slot);
    }

    @Override
    public String offButtonWasPushed(int id, int slot) {
        return remoteControls.get(id).offButtonWasPushed(slot);

    }
}
