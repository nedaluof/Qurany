package com.nedaluof.qurany.ui.sura;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.data.model.Suras;
import com.nedaluof.qurany.databinding.ActivityReciterSurasBinding;
import com.nedaluof.qurany.util.SurasUtil;
import com.nedaluof.qurany.util.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReciterSurasActivity extends AppCompatActivity implements SurasView {
    private static final String TAG = "ReciterSurasActivity";
    private ActivityReciterSurasBinding binding;
    private ReciterSuraAdapter adapter;
    private Reciter reciterData;
    private BottomSheetBehavior sheetBehavior;
    //audio player
    private SimpleExoPlayer player;
    private MediaSource mediaSource;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String server;
    private int suraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReciterSurasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sheetBehavior = BottomSheetBehavior.from(binding.playerBottomSheet.bottomSheet);

        /* TODO: 6/26/2020 replace intent by create Room Database
         * and support data in the presenter via @link{#DataManager} class
         * */
        Intent comingData = getIntent();
        reciterData = comingData.getParcelableExtra("reciterData");

        SurasPresenter presenter = new SurasPresenter();
        presenter.attachView(this);


        initRecyclerView();


        assert reciterData != null;
        presenter.loadRecitersSuras(reciterData);


        binding.playerBottomSheet.imgBtnClose.setOnClickListener(v -> {
            player.release();
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            new Handler().postDelayed(() ->
                            binding.playerBottomSheet.bottomSheet.setVisibility(View.GONE)
                    , 750);
        });

        Utility.createMainFolder();
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void initRecyclerView() {
        binding.reciterSurasRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.reciterSurasRecyclerView.setHasFixedSize(true);
        binding.reciterSurasRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ReciterSuraAdapter(R.layout.item_sura, new ArrayList<>(), this);
        adapter.setAdapterAnimation(new ScaleInAnimation());
        binding.reciterSurasRecyclerView.setAdapter(adapter);
        binding.reciterSurasRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (dy < 0) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, getString(R.string.download_manager_completed_message), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void showProgress(boolean show) {
        if (show) {
            binding.proRecitersSura.setVisibility(View.VISIBLE);
        } else {
            binding.proRecitersSura.setVisibility(View.GONE);
        }
    }

    @Override
    public void setReciterName(String reciterName) {
        binding.reciterNameTitleBar.setText(reciterName);
    }

    @Override
    public void showReciterSuras(List<Suras> surasList) {
        adapter.addData(surasList);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error :" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickPlay(int suraId) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Qurany/" + reciterData.getName(), SurasUtil.getSuraName(suraId) + ".mp3");
        if (!player.isPlaying()) {
            if (!Utility.checkIfFileInPathExist(reciterData.getName() + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
                this.suraId = suraId;
                binding.playerBottomSheet.bottomSheet.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.playerBottomSheet.reciterSuraName.setText(SurasUtil.getPlayerTitle(suraId, reciterData.getName()));
                initializePlayer(suraId);
                Log.d(TAG, "onClickPlay: current , listened online");
            } else {
                this.suraId = suraId;
                binding.playerBottomSheet.bottomSheet.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.playerBottomSheet.reciterSuraName.setText(SurasUtil.getPlayerTitle(suraId, reciterData.getName()));
                initializePlayerLocalSura(direct.getPath());
                Log.d(TAG, "onClickPlay: current listened local");
            }
        } else if (this.suraId != suraId) {
            player.release();
            if (!Utility.checkIfFileInPathExist(reciterData.getName() + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
                this.suraId = suraId;
                binding.playerBottomSheet.bottomSheet.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.playerBottomSheet.reciterSuraName.setText(SurasUtil.getPlayerTitle(suraId, reciterData.getName()));
                initializePlayer(suraId);
                Log.d(TAG, "onClickPlay: new sura id , listened online");
            } else {
                this.suraId = suraId;
                binding.playerBottomSheet.bottomSheet.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.playerBottomSheet.reciterSuraName.setText(SurasUtil.getPlayerTitle(suraId, reciterData.getName()));
                initializePlayerLocalSura(direct.getPath());
                Log.d(TAG, "onClickPlay: new sura id , listened local");
            }
        }
    }

    @Override
    public void onDownloadClick(int suraId) {
        new Thread(() -> {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            startDownload(suraId);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                Utility.showSettingsDialog(ReciterSurasActivity.this);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).withErrorListener(e -> Toast.makeText(this, getString(R.string.dexter_permission_error), Toast.LENGTH_SHORT).show())
                    .check();
        }) {
        }.start();
    }

    private void initializePlayer(int suraId) {
        server = reciterData.getServer() + "/" + SurasUtil.getSuraIndex(suraId) + ".mp3";
        mediaSource = buildMediaSource(Uri.parse(server));
        player = ExoPlayerFactory.newSimpleInstance(this);
        binding.playerBottomSheet.playerController.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    new Handler().postDelayed(() -> {
                        binding.playerBottomSheet.bottomSheet.setVisibility(View.GONE);
                        player.release();
                    }, 1000);
                } else if (playWhenReady && playbackState == Player.STATE_READY) {
                    // media actually playing
                } else if (playWhenReady) {
                    // might be idle (plays after prepare()),
                    // buffering (plays when data available)
                    // or ended (plays when seek away from end)
                } else {
                    // player paused in any state
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

        });

    }

    private void initializePlayerLocalSura(String path) {
        mediaSource = buildMediaSource(Uri.parse(path));
        player = ExoPlayerFactory.newSimpleInstance(this);
        binding.playerBottomSheet.playerController.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    new Handler().postDelayed(() -> {
                        binding.playerBottomSheet.bottomSheet.setVisibility(View.GONE);
                        player.release();
                    }, 1000);
                }
            }
        });

    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "Qurany_Player");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }


    private void startDownload(int suraId) {
        if (!Utility.checkIfFileInPathExist(reciterData.getName() + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
            String uRl = reciterData.getServer() + "/" + SurasUtil.getSuraIndex(suraId) + ".mp3";
            DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uRl));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                    DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(getString(R.string.download_manager_title) + SurasUtil.getSuraName(suraId))
                    .setDescription(SurasUtil.getSuraName(suraId) + "| " + reciterData.getName());

            request.setDestinationInExternalPublicDir("/Qurany/" + reciterData.getName(), SurasUtil.getSuraName(suraId) + ".mp3");

            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }
        } else {
            Utility.alertSuraIsReadyToPlay(ReciterSurasActivity.this);
        }
    }


    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer(suraId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer(suraId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

}
