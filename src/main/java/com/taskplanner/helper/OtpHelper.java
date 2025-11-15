package com.taskplanner.helper;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class OtpHelper {
    private final Map<String, OtpData> otpStore = new HashMap<>();
    private final Random random = new Random();

    public String generateOtp() {
        return String.format("%06d", random.nextInt(1000000));
    }

    public void storeOtp(String email, String otp) {
        otpStore.put(email, new OtpData(otp, System.currentTimeMillis()));
    }

    public boolean verifyOtp(String email, String otp) {
        OtpData data = otpStore.get(email);
        if (data == null || !data.otp.equals(otp)) {
            return false;
        }
        long age = System.currentTimeMillis() - data.timestamp;
        if (age > 600000L) {  // 10 minutes
            otpStore.remove(email);
            return false;
        }
        return true;
    }

    public void removeOtp(String email) {
        otpStore.remove(email);
    }

    private static class OtpData {
        String otp;
        long timestamp;

        OtpData(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }
}
