package com.google.maps.android.utils.demo.model;

/**
 * Created by hasitha on 3/12/15.
 */
public class MyMarker {


    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker( Double latitude, Double longitude,String icon)
    {

        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
    }



    public String getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(String icon)
    {
        this.mIcon = icon;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }
}
