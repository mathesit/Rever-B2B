package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 3/25/2016.
 */
public class User {

    private String sessionToken, firstName, countryCode, city, email, userType;
    private int companyId, userId;

    public User(int id, int compId, String sessionToken, String fName, String countryCode, String city, String email, String userType) {
        this.userId = id;
        this.companyId = compId;
        this.sessionToken = sessionToken;
        this.firstName = fName;
        this.countryCode = countryCode;
        this.city = city;
        this.email = email;
        this.userType = userType;
    }

    public int getUserId(){ return userId; }

    public int getComopanyId(){ return companyId; }

    public String getSessionToken(){ return sessionToken; }

    public String getFirstName(){ return firstName; }

    public String getCountryCode(){ return countryCode; }

    public String getCity(){ return city; }

    public String getEmail(){ return email; }

    public String getUserType(){ return userType; }

    public void setUserId(int userId){ this.userId = userId; }

    public void setCompanyId(int compId){ this.companyId = compId; }

    public void setSessionToken(String token){ this.sessionToken = token; }

    public void setFirstName(String name){ this.firstName = name; }

    public void setCountryCode(String country){ this.countryCode = country; }

    public void setCity(String city){ this.city = city; }

    public void setEmail(String email){ this.email = email; }

    public void setUserType(String type){ this.userType = type; }
}
