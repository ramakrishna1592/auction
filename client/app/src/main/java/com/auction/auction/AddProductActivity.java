package com.auction.auction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auction.auction.data.models.AddProductRequestModel;
import com.auction.auction.data.models.AddProductResponseModel;
import com.auction.auction.utils.GetRequestUtils;
import com.auction.auction.utils.PostRequestUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    private static final String ADD_PRODUCT_URL = "http://android-auction.herokuapp.com/api/categories/5699ccb82307b58816f64b2a/products";

    private AddProductTask mAddProductTask = null;
    private EditText mNameView;
    private EditText mPriceView;
    private EditText mRealPriceView;
    private EditText mImgURLView;
    private EditText mDescriptionView;
//    private View mProgressView;
//    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mNameView = (EditText) findViewById(R.id.inputProductName);
        mPriceView = (EditText) findViewById(R.id.inputProductPrice);
        mRealPriceView = (EditText) findViewById(R.id.inputRealPrice);
        mImgURLView = (EditText) findViewById(R.id.inputImageURL);
        mDescriptionView = (EditText) findViewById(R.id.inputDescription);

        Button addProductButton = (Button) findViewById(R.id.productAddProduct);
        addProductButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });


    }

    private void addProduct() {
        if (mAddProductTask != null) {
            return;
        }

        // Reset errors.
//        mUsernameView.setError(null);
//        mPasswordView.setError(null);

        String name = mNameView.getText().toString();
        String price = mPriceView.getText().toString();
        String realPrice = mRealPriceView.getText().toString();
        String imgUrl = mImgURLView.getText().toString();
        String description = mDescriptionView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(price)) {
            mPriceView.setError(getString(R.string.error_field_required));
            focusView = mPriceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(realPrice)) {
            mRealPriceView.setError(getString(R.string.error_field_required));
            focusView = mRealPriceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(imgUrl)) {
            mImgURLView.setError(getString(R.string.error_field_required));
            focusView = mImgURLView;
            cancel = true;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Show a progress and start a background task to perform the user login attempt.
//            showProgress(true);
            mAddProductTask = new AddProductTask(new AddProductRequestModel(name, price, realPrice, imgUrl, description));
            mAddProductTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the register form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

    public class AddProductTask extends AsyncTask<Void, Void, Boolean> {
        private final AddProductRequestModel mModel;

        AddProductTask(AddProductRequestModel model) {
            mModel = model;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String plainToken = String.format("%s:%s", mModel.name, mModel.price, mModel.realPrice, mModel.imageUrl, mModel.description);
            String encodedToken = Base64.encodeToString(plainToken.getBytes(), Base64.DEFAULT);


            // TODO: To implement
            if (isProductAddedSuccessfully(mModel, encodedToken)) {
                return true;
            }

            return false;
        }

//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//            if (success) {
//                finish();
//            } else {
//                Toast.makeText(getApplicationContext(), "There's a network issue or problem with the server.", Toast.LENGTH_SHORT).show();
//            }
//        }

//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }

//        private void saveBasicAuthTokenToSharedPref(String encodedToken) {
//            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString(getString(R.string.auth_token_pref_key), encodedToken);
//            editor.apply();
//        }
//
        private boolean isProductAddedSuccessfully(AddProductRequestModel model, String encodedToken) {
            Map<String, String> requestHeaders = new HashMap<String, String>();
            requestHeaders.put("Authorization", "Basic " + encodedToken);
            String addProductResul = GetRequestUtils.make(AddProductActivity.ADD_PRODUCT_URL, requestHeaders);
            Log.d("", addProductResul);
            if (addProductResul.equals("Authorized")) {
                return true;
            }

            return false;
        }
//
//        private boolean isRegistrationSuccessful(RegisterLoginRequestModel model) {
//            String registerRequestResult = PostRequestUtils.make(RegisterActivity.REGISTER_REQUEST_URL, model, new HashMap<String, String>());
//            Log.d("", registerRequestResult);
//            Gson gson = new Gson();
//            RegisterResponseModel responseModel = gson.fromJson(registerRequestResult, RegisterResponseModel.class);
//            if (responseModel.username != null && responseModel._id != null && responseModel.username.equals(model.username)) {
//                return true;
//            }
//
//            return false;
//        }
    }
}