package foundobjectapp.jhojan.com.foundobjectapp.activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

import foundobjectapp.jhojan.com.foundobjectapp.R;
import foundobjectapp.jhojan.com.foundobjectapp.models.Objeto;

public class NewItemActivity extends AppCompatActivity {

    ImageButton imageButton;
    ImageView imageView;
    EditText objetoName;
    EditText lugar;
    EditText fecha;
    EditText descObj;
    Button agregar;
    Button cancelar;
    //Referencia para usar Database
    DatabaseReference mRef;

    //Referencia para usar Storage
    StorageReference mStorage;
    Uri uri;
    public static  final String STORAGE_PATH = "image/";
    public static final int PICK_IMAGE_REQUEST = 234;

    // private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        imageButton = (ImageButton) findViewById(R.id.imagenObj);
        imageView = (ImageView) findViewById(R.id.objetoImg);
        objetoName = (EditText) findViewById(R.id.objetoname);
        lugar = (EditText) findViewById(R.id.lugarText);
        fecha = (EditText) findViewById(R.id.fechaText);
        descObj = (EditText) findViewById(R.id.descText);
        agregar = (Button) findViewById(R.id.addNewItem);
        cancelar = (Button) findViewById(R.id.cancelNewItem);

        mStorage = FirebaseStorage.getInstance().getReference();

        mRef = FirebaseDatabase.getInstance().getReference("Objetos");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
            }
        });



        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArrayList();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // alertaConfirm();
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

    }

    //Ingreso de Datos
    private  void addArrayList(){
        final String objetoNombre = objetoName.getText().toString().trim();
        final String lugarTxt = lugar.getText().toString().trim();
        final String fechaTxt = fecha.getText().toString().trim();
        final String descripcion = descObj.getText().toString().trim();

        if (TextUtils.isEmpty(objetoNombre)){
            Toast.makeText(this, "Ingrese el nombre del Objeto", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(lugarTxt)){
            Toast.makeText(this, "Ingrese el lugar de hallazgo", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(fechaTxt)){
            Toast.makeText(this, "Ingrese el fecha de hallazgo", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(descripcion)){
            Toast.makeText(this, "Ingrese la descripcion del objeto", Toast.LENGTH_LONG).show();
        }else{

            if (uri != null){
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Agregando nuevo objeto");
                dialog.show();

            //Upload para la Foto a Storage
                StorageReference filePath = mStorage.child(STORAGE_PATH + System.currentTimeMillis() + "."+getImageExt(uri));
                filePath.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                dialog.dismiss();
                                String id = mRef.push().getKey();
                                Objeto objeto = new Objeto(id);
                                // Uploat de datos a Database
                                mRef.child(id).child("Objeto").setValue(objetoNombre.toString());
                                mRef.child(id).child("Lugar").setValue(lugarTxt.toString());
                                mRef.child(id).child("Fecha").setValue(fechaTxt.toString());
                                mRef.child(id).child("Descripción").setValue(descripcion.toString());
                                mRef.child(id).child("Estado").setValue("Almacenado");
                                mRef.child(id).child("Imagen").setValue(taskSnapshot.getDownloadUrl().toString());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progrss = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                dialog.setMessage("Objeto Agregado" + (int)progrss+"%");
                            }
                        });
            }
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            Toast.makeText(this, "Nuevo objeto agregado", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }

    //Set de los datos
    private void Cleartxt(){
        objetoName.setText("");
        lugar.setText("");
        fecha.setText("");
        descObj.setText("");
    }

    //Push de la imagen al Storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    //confirm Dialog
    private void alertaConfirm(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setCancelable(true);
//        builder.setTitle("Title");
//        builder.setMessage("Message");
//        builder.setPositiveButton("Confirm",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
//                        startActivity(intent);
//                    }
//                });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }
}
