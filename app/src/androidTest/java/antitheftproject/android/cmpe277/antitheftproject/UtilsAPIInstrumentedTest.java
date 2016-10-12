package antitheftproject.android.cmpe277.antitheftproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import antitheftproject.android.cmpe277.antitheftproject.api.UtilsAPI;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UtilsAPIInstrumentedTest {
    @Test
    public void testDecodeGPSLocation() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        double lon = -122.057979;
        double lat = 37.402051;
        String address = UtilsAPI.decodeGPSLocation(appContext, lat, lon);
        assertTrue(address.length() > 0);
    }
}
