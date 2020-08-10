//package com;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.example.legutkoapplication.R;
//import com.example.legutkoapplication.activity.ProductUpdateActivity;
//import com.utils.MarginDecoration;
//import com.utils.PicHolder;
//import com.utils.imageFolder;
//import com.utils.itemClickListener;
//import com.utils.pictureFacer;
//import com.utils.pictureFolderAdapter;
//
//import java.util.ArrayList;
//
//public class GallegryImgActivity extends AppCompatActivity implements itemClickListener {
//    RecyclerView folderRecycler;
//    TextView empty;
//    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallegry_img);
//
//
//
//        if(ContextCompat.checkSelfPermission(GallegryImgActivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(GallegryImgActivity.this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//        //____________________________________________________________________________________
//
//        empty =findViewById(R.id.empty);
//
//        folderRecycler = findViewById(R.id.folderRecycler);
//        folderRecycler.addItemDecoration(new MarginDecoration(this));
//        folderRecycler.hasFixedSize();
//        ArrayList<imageFolder> folds = getPicturePaths();
//
//
//
//
//
//
//        if(folds.isEmpty()){
//            empty.setVisibility(View.VISIBLE);
//        }else{
//            RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds,GallegryImgActivity.this,this);
//            folderRecycler.setAdapter(folderAdapter);
//
//        }
//
//
//
//
//    }
//    public ArrayList<imageFolder> getPicturePaths(){
//        ArrayList<imageFolder> picFolders = new ArrayList<>();
//        ArrayList<String> picPaths = new ArrayList<>();
//        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
//        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
//        try {
//            if (cursor != null) {
//                cursor.moveToFirst();
//            }
//            do{
//                imageFolder folds = new imageFolder();
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
//                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
//                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
////                String a = "/storage/emulated/0/Pictures/reports_photos/SHEN_00013/";
//                //String folderpaths =  datapath.replace(name,"");
//                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
//                folderpaths = folderpaths+folder+"/";
////                String folderpaths ="/storage/emulated/0/DCIM/Camera";
//
//
//                System.out.println( folderpaths + " folderpa");
//                System.out.println( folder + " folder");
//
//
//                if (!picPaths.contains(folderpaths)) {
//                    picPaths.add(folderpaths);
//
//                    folds.setPath(folderpaths);
//                    folds.setFolderName(folder);
//                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
//                    folds.addpics();
//                    picFolders.add(folds);
//                }else{
//                    for(int i = 0;i<picFolders.size();i++){
//                        if(picFolders.get(i).getPath().equals(folderpaths)){
//                            picFolders.get(i).setFirstPic(datapath);
//                            picFolders.get(i).addpics();
//                        }
//                    }
//                }
//            }while(cursor.moveToNext());
//            cursor.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for(int i = 0;i < picFolders.size();i++){
//            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
//        }
//
//        //reverse order ArrayList
//       /* ArrayList<imageFolder> reverseFolders = new ArrayList<>();
//
//        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
//            reverseFolders.add(picFolders.get(i));
//        }*/
//        System.out.println(picFolders + " to jest folderpaths");
//        return picFolders;
//    }
//
//
//    @Override
//    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {
//
//    }
//
//    /**
//     * Each time an item in the RecyclerView is clicked this method from the implementation of the transitListerner
//     * in this activity is executed, this is possible because this class is passed as a parameter in the creation
//     * of the RecyclerView's Adapter, see the adapter class to understand better what is happening here
//     * @param pictureFolderPath a String corresponding to a folder path on the device external storage
//     */
//    @Override
//    public void onPicClicked(String pictureFolderPath,String folderName) {
//        Intent move = new Intent(GallegryImgActivity.this,ImageDisplay.class);
//
////        System.out.println(a + "test name");
////        pictureFolderPath = "/storage/emulated/0/Pictures/reports_photos/"+a;
//        move.putExtra("folderPath",pictureFolderPath);
//        move.putExtra("folderName",folderName);
//
//        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
//        startActivity(move);
//    }
//
//
//   /* public int getCardsOptimalWidth(int numberOfRows){
//        Configuration configuration = MainActivity.this.getResources().getConfiguration();
//        int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
//        int smallestScreenWidthDp = configuration.smallestScreenWidthDp; //The smallest screen size an application will see in normal operation, corresponding to smallest screen width resource qualifier.
//        int each = screenWidthDp / numberOfRows;
//
//        return each;
//    }*/
//
//   /* private int dpToPx(int dp) {
//        float density = MainActivity.this.getResources()
//                .getDisplayMetrics()
//                .density;
//        return Math.round((float) dp * density);
//    }*/
//
//}
