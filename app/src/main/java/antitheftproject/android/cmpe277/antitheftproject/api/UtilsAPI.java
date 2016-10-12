package antitheftproject.android.cmpe277.antitheftproject.api;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;

/**
 * Created by sdthai on 10/11/2016.
 */

public class UtilsAPI {
    /*public static boolean isAppUninstalled(Intent intent) {
        String [] packageNames =  intent.getStringArrayExtra("android.intent.extra.PACKAGES");

        if (packageNames != null) {
            for (String packageName: packageNames) {
                if (packageName != null && packageName.equals(Constant.APP_PACKAGE_NAME)) {
                    return true;
                }
            }
        }

        return false;
    }*/

    public static String decodeGPSLocation(Context context, double lon, double lat) {
        String addr = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lon,1);
            if (addressList != null) {
                Address address = addressList.get(0);
                StringBuilder fullAddress = new StringBuilder("");
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    fullAddress.append(address.getAddressLine(i)).append("\n");
                }

                addr = fullAddress.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  addr;
    }
}
