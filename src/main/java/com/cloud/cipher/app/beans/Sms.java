package com.cloud.cipher.app.beans;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

public class Sms {
    private String api_token;
    private String from;
    private String to;
    private String body;

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.api_token);
        hash = 31 * hash + Objects.hashCode(this.from);
        hash = 31 * hash + Objects.hashCode(this.to);
        hash = 31 * hash + Objects.hashCode(this.body);
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
        final Sms other = (Sms) obj;
        if (!Objects.equals(this.api_token, other.api_token)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sms{" + "api_token=" + api_token + ", from=" + from + ", to=" + to + ", body=" + body + '}';
    }
}
