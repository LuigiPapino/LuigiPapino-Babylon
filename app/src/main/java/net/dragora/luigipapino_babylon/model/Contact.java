package net.dragora.luigipapino_babylon.model;

import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Contact implements Parcelable{

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    private static final String FIELD_ID = "id";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_SURNAME = "surname";
    private static final String FIELD_PHONE_NUMBER = "phone_number";
    private static final String FIELD_CREATED_AT = "createdAt";
    private static final String FIELD_ADDRESS = "address";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_UPDATED_AT = "updatedAt";
    private static SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK); //TODO verify the format is correct and in UK locale
    private static SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
    @SerializedName(FIELD_ID)
    private int mId;
    @SerializedName(FIELD_FIRST_NAME)
    private String mFirstName;
    @SerializedName(FIELD_SURNAME)
    private String mSurname;
    @SerializedName(FIELD_PHONE_NUMBER)
    private String mPhoneNumber;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_ADDRESS)
    private String mAddress;
    @SerializedName(FIELD_EMAIL)
    private String mEmail;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;

    public Contact(){

    }

    protected Contact(Parcel in) {
        this.mId = in.readInt();
        this.mFirstName = in.readString();
        this.mSurname = in.readString();
        this.mPhoneNumber = in.readString();
        this.mCreatedAt = in.readString();
        this.mAddress = in.readString();
        this.mEmail = in.readString();
        this.mUpdatedAt = in.readString();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (mId != contact.mId) return false;
        if (mFirstName != null ? !mFirstName.equals(contact.mFirstName) : contact.mFirstName != null)
            return false;
        if (mSurname != null ? !mSurname.equals(contact.mSurname) : contact.mSurname != null)
            return false;
        if (mPhoneNumber != null ? !mPhoneNumber.equals(contact.mPhoneNumber) : contact.mPhoneNumber != null)
            return false;
        if (mCreatedAt != null ? !mCreatedAt.equals(contact.mCreatedAt) : contact.mCreatedAt != null)
            return false;
        if (mAddress != null ? !mAddress.equals(contact.mAddress) : contact.mAddress != null)
            return false;
        if (mEmail != null ? !mEmail.equals(contact.mEmail) : contact.mEmail != null) return false;
        return mUpdatedAt != null ? mUpdatedAt.equals(contact.mUpdatedAt) : contact.mUpdatedAt == null;

    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + (mFirstName != null ? mFirstName.hashCode() : 0);
        result = 31 * result + (mSurname != null ? mSurname.hashCode() : 0);
        result = 31 * result + (mPhoneNumber != null ? mPhoneNumber.hashCode() : 0);
        result = 31 * result + (mCreatedAt != null ? mCreatedAt.hashCode() : 0);
        result = 31 * result + (mAddress != null ? mAddress.hashCode() : 0);
        result = 31 * result + (mEmail != null ? mEmail.hashCode() : 0);
        result = 31 * result + (mUpdatedAt != null ? mUpdatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "id = " + mId + ", firstName = " + mFirstName + ", surname = " + mSurname + ", phoneNumber = " + mPhoneNumber + ", createdAt = " + mCreatedAt + ", address = " + mAddress + ", email = " + mEmail + ", updatedAt = " + mUpdatedAt;
    }

    public String getName() {
        return String.format("%s %s", getFirstName(), getSurname());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mFirstName);
        dest.writeString(this.mSurname);
        dest.writeString(this.mPhoneNumber);
        dest.writeString(this.mCreatedAt);
        dest.writeString(this.mAddress);
        dest.writeString(this.mEmail);
        dest.writeString(this.mUpdatedAt);
    }

    @NonNull
    public String getCreatedFormatted(){
        String result = null;
        try {
            Date date = inputFormatter.parse(mCreatedAt);
            result=  outputFormatter.format(date);
        } catch (ParseException e) {
            result = mCreatedAt;
        }
        return result != null ? result : "";
    }

    @NonNull
    public String getUpdatedFormatted(){
        String result = null;
        try {
            Date date = inputFormatter.parse(mUpdatedAt);
            result=  outputFormatter.format(date);
        } catch (ParseException e) {
            result = mUpdatedAt;
        }
        return result != null ? result : "";
    }
}