package com.cloud.cipher.app;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.KeyGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fullname;
    @Column(unique = true)
    private String email;
    private String phone;
    private String country;
    private byte[] cipherHash;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getCipherHash() {
        return cipherHash;
    }

    public void setCipherHash(byte[] cipherHash) {
        this.cipherHash = cipherHash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.fullname);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.phone);
        hash = 67 * hash + Objects.hashCode(this.country);
        hash = 67 * hash + Arrays.hashCode(this.cipherHash);
        hash = 67 * hash + Objects.hashCode(this.password);
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.fullname, other.fullname)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Arrays.equals(this.cipherHash, other.cipherHash)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fullname=" + fullname + ", email=" + email + ", phone=" + phone + ", country=" + country + ", cipherHash=" + cipherHash + ", password=" + password + '}';
    }
}
