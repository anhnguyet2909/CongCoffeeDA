package com.example.coffee;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class StaffViewPagerAdapter extends FragmentStatePagerAdapter {
    public StaffViewPagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentStaffHome();
            case 1:
                return new FragmentStaffCart();
            case 2:
                return new FragmentStaffNotification();
            case 3:
                return new FragmentAdminAccount();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "Trang chủ";
            case 1:
                return "Giỏ hàng";
            case 2:
                return "Thông báo";
            case 3:
                return "Tài khoản";
            default:
                return null;
        }
    }
}
