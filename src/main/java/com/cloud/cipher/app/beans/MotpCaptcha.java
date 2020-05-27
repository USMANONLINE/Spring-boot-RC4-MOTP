package com.cloud.cipher.app.beans;

import java.util.Objects;
public class MotpCaptcha {
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.otp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MotpCaptcha other = (MotpCaptcha) obj;
        if (!Objects.equals(this.otp, other.otp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MotpCaptcha{" + "otp=" + otp + '}';
    }
    
}
