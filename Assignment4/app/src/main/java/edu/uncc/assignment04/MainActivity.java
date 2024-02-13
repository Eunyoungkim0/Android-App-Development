package edu.uncc.assignment04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.uncc.assignment04.fragments.DemographicFragment;
import edu.uncc.assignment04.fragments.IdentificationFragment;
import edu.uncc.assignment04.fragments.MainFragment;
import edu.uncc.assignment04.fragments.ProfileFragment;
import edu.uncc.assignment04.fragments.SelectEducationFragment;
import edu.uncc.assignment04.fragments.SelectIncomeFragment;
import edu.uncc.assignment04.fragments.SelectLivingStatusFragment;
import edu.uncc.assignment04.fragments.SelectMaritalStatusFragment;

public class MainActivity extends AppCompatActivity
        implements MainFragment.MainFragmentListener,
        IdentificationFragment.IdentificationFragmentListener,
        DemographicFragment.DemographicFragmentListener,
        SelectEducationFragment.SelectEducationFragmentListener,
        SelectMaritalStatusFragment.SelectMaritalStatusFragmentListener,
        SelectLivingStatusFragment.SelectLivingStatusFragmentListener,
        SelectIncomeFragment.SelectIncomeFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new MainFragment(), "main-fragment")
                .addToBackStack(null)
                .commit();
    }

    public void gotoIdentification(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new IdentificationFragment(), "identification-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoDemographic(Identification identification) {

        IdentificationFragment fragment = (IdentificationFragment) getSupportFragmentManager().findFragmentByTag("identification-fragment");
        if(fragment != null){
            fragment.setIdentification(identification);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DemographicFragment.newInstance(identification), "demographic-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectEducation() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectEducationFragment(), "select-education-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectMaritalStatus() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectMaritalStatusFragment(), "select-maritalstatus-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectLivingStatus() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectLivingStatusFragment(), "select-livingstatus-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectIncome() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectIncomeFragment(), "select-income-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(Profile profile) {

        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setProfile(profile);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile), "profile-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void closeFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitEducation(String str_education) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectEducation(str_education);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitMaritalStatus(String str_marital_status) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectMaritalStatus(str_marital_status);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitLivingStatus(String str_living_status) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectLivingStatus(str_living_status);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitIncome(String str_income) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectIncome(str_income);
        }
        getSupportFragmentManager().popBackStack();
    }
}