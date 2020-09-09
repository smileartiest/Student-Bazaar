package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.iceteck.silicompressorr.SiliCompressor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Tech_News_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.FileUtil;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Tech_News_model;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Tech_News extends AppCompatActivity {
    List<Tech_News_model> tech_news_models = null;
    SpotsDialog spotsDialog;
    RecyclerView memeview;
    Tech_News_Adapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Bitmap profilePicture;
    private static int RESULT_LOAD_IMAGE = 1;
    String profileimg, encoded;
    ImageView postmeme;
    String UID;
    Toolbar toolbar;
    String epost = "0";
    Dialog dialog;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_news_page);

        spotsDialog = new SpotsDialog(this);
        memeview = findViewById(R.id.newsview);
        swipeRefreshLayout = findViewById(R.id.swipeToRefreshnews);
        new Controller(this);
        UID = Controller.getUID();
        toolbar = (Toolbar) findViewById(R.id.toolbarnews);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);
        }
        loadData();
        memeview.setHasFixedSize(true);
        memeview.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(Tech_News.this, HomeActivity.class);
        finish();
    }

    private void loadData() {
        spotsDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_NEWS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.message().toString());

                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);

                    assert response.body() != null;
                    tech_news_models = response.body().getTech_News_model();

                    Log.d("RESPONSE2", tech_news_models.toString());
                    spotsDialog.dismiss();
                    if (tech_news_models.size() == 0) {
                    } else {
                        mAdapter = new Tech_News_Adapter(Tech_News.this, tech_news_models);
                        memeview.setAdapter(mAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();

                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.item1);
        menu.findItem(R.id.item2).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.profile).setVisible(false);
        menu.findItem(R.id.shareitem).setVisible(false);
        if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.VISITOR)) {
            shareItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.shareitem:
                try {
                    new Monitor(this).sharetowhatsapp();
                } catch (Exception e) {

                }
                return true;
            case R.id.item1:
                addJob();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addJob() {
        dialog = new Dialog(Tech_News.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.technews_design);
        TextView post = dialog.findViewById(R.id.addnewstv);
        final EditText ednews = dialog.findViewById(R.id.addnewsed);
        postmeme = dialog.findViewById(R.id.addnewsiv);
        Button postbtn = dialog.findViewById(R.id.addnewsbtn);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (encoded == null) {
                    Toast.makeText(Tech_News.this, "Please Select Images", Toast.LENGTH_SHORT).show();
                } else {
                    spotsDialog.show();

                    Call<String> call = ApiUtil.getServiceClass().addtechnews(ednews.getText().toString(), encoded);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.body().equals("1")) {
                                spotsDialog.dismiss();
                                dialog.cancel();
                                alert();
                            } else {
                                Toast.makeText(Tech_News.this, response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            try {
                if (data != null) {
                    if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                        profilePicture = BitmapFactory.decodeStream(imageStream);
                        postmeme.setImageBitmap(profilePicture);
                        File actualImage = FileUtil.from(this, data.getData());
                        Bitmap compressedImgFile = SiliCompressor.with(this).getCompressBitmap(actualImage.getAbsolutePath());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    void alert() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Tech_News.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Hey ");
        builder.setMessage("News Updated Success...");
        builder.addButton("Done", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loadData();

                    }
                });
        builder.show();
    }
}