package com.example.onthejourney.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onthejourney.Adapter.PhotographerListViewAdapter;
import com.example.onthejourney.Algorithm.CustomClusterRenderer;
import com.example.onthejourney.Algorithm.NonHierarchicalDistanceBasedAlgorithm;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.ETC.PermissionUtils;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.RequestHttpURLConnection;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
     Buddy buddy = null;

    private TextView like, mung,count,thisArea;
    private Customer me;
    private ClusterManager<Buddy> mClusterManager;
    String str;
    PlaceAutocompleteFragment autocompleteFragment;
    private boolean mPermissionDenied = false;

    ArrayList<Buddy> aryList = new ArrayList<Buddy>();

    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        me = getIntent().getParcelableExtra("Customer");
        Log.d("me",me.toString());
        like = findViewById(R.id.like);
        thisArea = findViewById(R.id.thisArea);
        mung = findViewById(R.id.mung);
        count = findViewById(R.id.count);


        button = (Button) findViewById(R.id.button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                System.out.println("Place: " + place.getName());
                str = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                System.out.println("An error occurred: " + status);
            }
        });
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
        setUpClusterer(googleMap);
        geocoder = new Geocoder(this);

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();


        //클러스터링 이벤트
        mClusterManager.setOnClusterClickListener(
                new ClusterManager.OnClusterClickListener<Buddy>() {
                    @Override
                    public boolean onClusterClick(Cluster<Buddy> cluster) {
                        final ListView listView = (ListView) findViewById(R.id.listView);

                        final Object[] ary = cluster.getItems().toArray();

                        final ArrayList<Buddy> arr = new ArrayList<Buddy>();

                        Task task = new Task("favorite_buddies/customer/", "buddy_id", me, new Task.Listener() {
                            @Override
                            public void onFinished(ArrayList<String> s) {
                                ArrayList<String> likeBuddyList = s;

                                for (int i = 0; i < ary.length; i++) {
                                    Log.d("Buddy",((Buddy)ary[i]).toString());
                                    for (int j = 0; j < likeBuddyList.size(); j++) {
                                        if (likeBuddyList.get(j).equals(((Buddy) ary[i]).getBuddy_id())) {
                                            ((Buddy) ary[i]).setLikeFlag(1);
                                        }
                                    }
                                    arr.add((Buddy) ary[i]);
                                }

                                Log.d("arr", arr.toString());


                                NetworkTask networkTask = new NetworkTask("feed_items", "image_path", arr, new NetworkTask.Listener() {
                                    @Override
                                    public void onFinished(final ArrayList<ArrayList<String>> s) {
                                        Log.d("Image_path_list", s.toString());
                                        LinearLayout linearLayout = findViewById(R.id.linearLayout);
                                        linearLayout.setVisibility(LinearLayout.VISIBLE);
                                        count.setText(String.valueOf(ary.length));

                                        PhotographerListViewAdapter adapter = new PhotographerListViewAdapter(arr, s);
                                        listView.setAdapter(adapter);
                                        listView.setVisibility(ListView.VISIBLE);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                System.out.println(position);
                                                buddy = arr.get(position);
                                                System.out.println(buddy.getTitle());

                                                Intent intent = new Intent(MapsActivity.this, Photographer_info.class);
                                                intent.putExtra("Buddy", buddy);
                                                intent.putExtra("Customer",me);
                                                intent.putStringArrayListExtra("List", s.get(position));
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                });
                                networkTask.execute();
                            }
                        });
                        task.execute();


                        Toast.makeText(MapsActivity.this, "Cluster click", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );
        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<Buddy>() {
                    @Override
                    public boolean onClusterItemClick(final Buddy Buddy) {

                        final ListView listView = (ListView) findViewById(R.id.listView);
                        final ArrayList<Buddy> arr = new ArrayList<Buddy>();

                        Task task = new Task("favorite_buddies/customer/", "buddy_id", me, new Task.Listener() {
                            @Override
                            public void onFinished(ArrayList<String> s) {
                                ArrayList<String> likeBuddyList = s;


                                    for (int j = 0; j < likeBuddyList.size(); j++) {
                                        if (likeBuddyList.get(j).equals(Buddy.getBuddy_id())) {
                                           Buddy.setLikeFlag(1);
                                        }
                                    }
                                    arr.add(Buddy);




                                NetworkTask networkTask = new NetworkTask("feed_items", "image_path", arr, new NetworkTask.Listener() {
                                    @Override
                                    public void onFinished(final ArrayList<ArrayList<String>> s) {
                                        Log.d("Image_path_list", s.toString());
                                        LinearLayout linearLayout = findViewById(R.id.linearLayout);
                                        linearLayout.setVisibility(LinearLayout.VISIBLE);
                                        count.setText("1");
                                        like = findViewById(R.id.like);

                                        PhotographerListViewAdapter adapter = new PhotographerListViewAdapter(arr, s);
                                        listView.setAdapter(adapter);
                                        listView.setVisibility(ListView.VISIBLE);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                System.out.println(position);
                                                buddy = arr.get(position);
                                                System.out.println(buddy.getTitle());

                                                Intent intent = new Intent(MapsActivity.this, Photographer_info.class);
                                                intent.putExtra("Buddy", buddy);
                                                intent.putExtra("Customer",me);
                                                intent.putStringArrayListExtra("List", s.get(position));
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                });
                                networkTask.execute();
                            }
                        });
                        task.execute();






//                        final ListView listView = (ListView) findViewById(R.id.listView);
//
//
//
//                        count.setText("1");
//                        LinearLayout linearLayout = findViewById(R.id.linearLayout);
//                        linearLayout.setVisibility(LinearLayout.VISIBLE);
//                        arr.add(Buddy);
//
//                        NetworkTask networkTask = new NetworkTask("feed_items","image_path", new NetworkTask.Listener() {
//                            @Override
//                            public void onFinished(final ArrayList<ArrayList<String>> s) {
//                                PhotographerListViewAdapter adapter = new PhotographerListViewAdapter(arr,s);
//                                listView.setAdapter(adapter);
//
//                                listView.setVisibility(ListView.VISIBLE);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                                        System.out.println(position);
//                                        buddy = arr.get(position);
//                                        System.out.println(buddy.getTitle());
//
//                                        Intent intent = new Intent(MapsActivity.this, Photographer_info.class);
//                                        intent.putExtra("Buddy", buddy);
//                                        intent.putStringArrayListExtra("List", s.get(position));
//                                        startActivity(intent);
//                                    }
//                                });
//
//                            }
//                        });
//                        networkTask.execute();

                        Toast.makeText(MapsActivity.this, "Cluster item click", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );

        // 버튼 이벤트
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수

                } catch (IOException e) {
                    e.printStackTrace();
                }


                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String[] splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(latitude);
                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
            }
        });

        addItems(aryList);
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private void setUpClusterer(final GoogleMap map) {
        // Position the map.
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<Buddy>(this, map);

        final CustomClusterRenderer render = new CustomClusterRenderer(this, mMap, mClusterManager);
        mClusterManager.setRenderer(render);
        final NonHierarchicalDistanceBasedAlgorithm nonHierarchicalDistanceBasedAlgorithm = new NonHierarchicalDistanceBasedAlgorithm();
        mClusterManager.setAlgorithm(nonHierarchicalDistanceBasedAlgorithm);
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);
    }



    private void addItems(ArrayList<Buddy> aryList) {

//        // Add ten cluster items in close proximity, for purposes of this example.
//        for (int i = 0; i < 10; i++) {
//            double offset = i / 60d;
//            lat = var.latitude + offset;
//            lng = var.longitude + offset;
//            Buddy offsetItem = new Buddy(lat, lng);
//            mClusterManager.addItem(offsetItem);
//        }
        for (int i = 0; i < 100; i++) {
            double lat = 0;
            double lng = 0;
            Random random = new Random();
            LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
            lat = (double) random.nextInt(300) / 5000.0 + DEFAULT_LOCATION.latitude;
            lng = (double) random.nextInt(300) / 5000.0 + DEFAULT_LOCATION.longitude;
            Buddy offsetItem = new Buddy(lat, lng);
            offsetItem.setmTitle(i + "번째 생성");
            if(i%2 == 0) {
                offsetItem.setBuddy_id("hihi");
            }
            else{
                offsetItem.setBuddy_id("hiroo~");
            }
            aryList.add(offsetItem);

            mClusterManager.addItem(offsetItem);
        }
        new HttpAsyncTask("GET", "buddies/" , null, null, new TypeToken<ResultBody<Buddy>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<Buddy> result = (ResultBody<Buddy>) resultBody;

                        for(int i=0;i<result.getDatas().size();i++){
                            Buddy buddy = result.getDatas().get(i);
                            buddy.setmPosition(new LatLng(buddy.getLatitude(),buddy.getLongitude()));
                            mClusterManager.addItem(result.getDatas().get(i));
                        }
                    }
                })
                .execute();
    }


    public static class NetworkTask extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

        private String option1, option2;
        private Listener listener;
        private ArrayList<Buddy> arr;

        public interface Listener{
            void onFinished(ArrayList<ArrayList<String>> s);
        }

        public NetworkTask(String option1, String option2, ArrayList<Buddy> arr, Listener listener){
            this.option1 = option1;
            this.option2 = option2;
            this.listener = listener;
            this.arr = arr;
        }

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... voids) {
            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
            for(int i=0;i<arr.size();i++) {
                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
                ArrayList<String> request = requestHttpURLConnection.getJsonText(option1, option2, arr.get(i).getBuddy_id());
                Log.d("DoinBackGround", "request : " + request);
                result.add(request);
                Log.d("DoinBackground", "result : " + result);

            }
            return result;
        }

        protected void onPostExecute(ArrayList<ArrayList<String>> s) {
                listener.onFinished(s);

        }
    }
    public static class Task extends AsyncTask<Void, Void, ArrayList<String>> {

        private String option1, option2;
        private Customer customer;
        private Listener listener;

        public interface Listener {
            void onFinished(ArrayList<String> s);
        }

        public Task(String option1, String option2, Customer customer, Listener listener) {
            this.option1 = option1;
            this.option2 = option2;
            this.customer = customer;
            this.listener = listener;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> buddy;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            buddy = requestHttpURLConnection.getJsonText(option1, option2, customer.getCustomer_id());
            Log.d("InNetTask", buddy.toString());


            return buddy;
        }

        protected void onPostExecute(ArrayList<String> s) {
            listener.onFinished(s);

        }
    }


}
